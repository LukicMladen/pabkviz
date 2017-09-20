package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.DatatypeConverter;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;

public class PlayerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtYourName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtChoosingPlayer;
	private JTextArea textArea;
	private JButton btnSubmitAnswer;
	private JTextArea textAreaQuestion;
	private JLabel lblAnswer;
	private JLabel lblQuestion;
	private JLabel lblYourName;
	private JLabel lblChoosePlayer;
	private JButton btnChoose;
	private JButton btnDone;
	private JRadioButton rdbtnHave;
	private JRadioButton rdbtnDontHave;
	private JButton btnSubmitTeam;
	private JButton btnSubmitAddress;
	private JLabel lblAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGUI frame = new PlayerGUI();
					frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlayerGUI() {
		setTitle("PabKviz");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 794, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtChoosingPlayer = new JTextField();
		txtChoosingPlayer.setBounds(10, 59, 86, 20);
		contentPane.add(txtChoosingPlayer);
		txtChoosingPlayer.setColumns(10);
		txtChoosingPlayer.setVisible(false);

		lblChoosePlayer = new JLabel("Choose player:");
		lblChoosePlayer.setBounds(10, 42, 122, 20);
		contentPane.add(lblChoosePlayer);
		lblChoosePlayer.setVisible(false);

		btnChoose = new JButton("Choose");
		btnChoose.setBounds(10, 88, 86, 23);
		contentPane.add(btnChoose);
		btnChoose.setVisible(false);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (textArea.getText().contains(txtChoosingPlayer.getText()) && !txtChoosingPlayer.getText().isEmpty())
						selectPlayer(txtChoosingPlayer.getText());
					else
						JOptionPane.showConfirmDialog(null, "That person doesn't exist, try again!", "Warning",
								JOptionPane.OK_OPTION);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		textField = new JTextField();
		textField.setBounds(10, 31, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		lblYourName = new JLabel("Your name:");
		lblYourName.setVisible(false);
		lblYourName.setBounds(10, 11, 86, 20);
		contentPane.add(lblYourName);

		txtYourName = new JTextField();
		txtYourName.setText("");
		txtYourName.setVisible(false);
		txtYourName.setColumns(10);
		txtYourName.setBounds(10, 31, 86, 20);
		contentPane.add(txtYourName);

		btnDone = new JButton("Done");
		btnDone.setBounds(340, 158, 89, 23);
		contentPane.add(btnDone);
		btnDone.setVisible(false);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String team = JOptionPane.showInputDialog(textArea, "Name of your team:", "Team",
						JOptionPane.QUESTION_MESSAGE);
				if (!team.matches("^[A-Z][a-z]+$")) {
					JOptionPane.showMessageDialog(textArea,
							"Name of your team must start with a capital letter followed by non-capital letters",
							"Team", JOptionPane.OK_OPTION, null);
					return;
				} else {
					out.println("[team]" + team + "|" + myName);
				}

			}
		});

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(184, 29, 401, 118);
		contentPane.add(textArea);
		textArea.setVisible(false);

		rdbtnHave = new JRadioButton("I have a team");
		buttonGroup.add(rdbtnHave);
		rdbtnHave.setBounds(10, 30, 135, 23);
		contentPane.add(rdbtnHave);
		rdbtnHave.setVisible(false);

		rdbtnDontHave = new JRadioButton("I don't have a team");
		buttonGroup.add(rdbtnDontHave);
		rdbtnDontHave.setBounds(10, 58, 135, 23);
		contentPane.add(rdbtnDontHave);
		rdbtnDontHave.setVisible(false);

		btnSubmitTeam = new JButton("Submit");
		btnSubmitTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable runnable = new Runnable() {
					public void run() {
						while (true) {
							String line = null;
							System.out.println("Thread started!");
							String[] choose = null;
							try {
								line = in.readLine();
								System.out.println(line);
							} catch (IOException e) {
								e.printStackTrace();
							}
							if (line.startsWith("[Choose]")) {
								String[] playersToChoose;
								line = line.replace("[Choose]", "");
								line = line.replace("[", "");
								line = line.replace("]", "");
								playersToChoose = line.split(", ");
								String forTextArea = "";
								for (int i = 0; i < playersToChoose.length; i++) {
									if (!playersToChoose[i].equals(myName)) {
										forTextArea += playersToChoose[i] + "\n";
									}
								}
								textArea.setText(forTextArea);
							}
							if (line.startsWith("[select]")) {
								line = line.replace("[select]", "");
								choose = line.split("\\|");
								if (choose[1].equals(myName)) {
									int response = JOptionPane.showConfirmDialog(textArea,
											"Do you want to play with " + choose[0], "Invitation",
											JOptionPane.YES_NO_OPTION);
									if (response == JOptionPane.YES_OPTION) {
										out.println("[yes]" + choose[0] + "|" + choose[1]);
									} else {
										out.println("[no]" + choose[0] + "|" + choose[1]);
									}
								}

							}

							if (line.startsWith("[yes]")) {
								line = line.replace("[yes]", "");
								String[] accepting = line.split("\\|");
								if (accepting[0].equals(myName)) {
									JOptionPane.showMessageDialog(textArea, accepting[1] + " accepted your invite!",
											"Accepted", JOptionPane.OK_OPTION);
								}
							}

							if (line.startsWith("[no]")) {
								line = line.replace("[no]", "");
								String[] accepting = line.split("\\|");
								if (accepting[0].equals(myName)) {
									JOptionPane.showMessageDialog(textArea, accepting[1] + " declined your invite!",
											"Declined", JOptionPane.OK_OPTION);
								}
							}

							if (line.startsWith("[taken]")) {
								line = line.replace("[taken]", "");
								if (line.equals(myName)) {
									JOptionPane.showMessageDialog(textArea, "That name is taken!", "Team",
											JOptionPane.OK_OPTION, null);
								}

							}
							if (line.startsWith("[nottaken]")) {
								line = line.replace("[nottaken]", "");
								if (line.equals(myName)) {
									JOptionPane.showMessageDialog(textArea, "The name of your team is accepted!",
											"Team", JOptionPane.OK_OPTION, null);
								}
							}
							if (line.startsWith("[teamName]")) {
								line = line.replace("[teamName]", "");
								String[] teamPlayer = line.split("\\|");
								System.out.println(teamPlayer[1]);
								System.out.println(myName);
								if (teamPlayer[1].equals(myName)) {
									myTeam = teamPlayer[0];
									JOptionPane.showMessageDialog(textArea, "The name of your team is " + teamPlayer[0],
											"Team", JOptionPane.OK_OPTION, null);
									btnChoose.setVisible(false);
									txtChoosingPlayer.setVisible(false);
									lblChoosePlayer.setVisible(false);
									btnDone.setVisible(false);
									initializeQuiz();
								}

							}
							if (line.startsWith("[Question]")) {
								line = line.replace("[Question]", "");
								String[] playerQuestion = line.split("\\|");
								if (playerQuestion[0].equals(myName)) {
									textAreaQuestion.setText(playerQuestion[1]);
								}
							}

						}
					}
				};
				Thread t = new Thread(runnable);
				t.start();
				if (rdbtnDontHave.isSelected()) {
					LinkedList<String> players = null;
					try {
						players = selectTeam(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textArea.setVisible(true);
					String forTextArea = "";
					for (String string : players) {
						if (!string.equalsIgnoreCase(lblYourName.getText())) {
							forTextArea += string + "\n";
						}
					}
					textArea.setText(forTextArea);
					btnSubmitTeam.setVisible(false);
					rdbtnDontHave.setVisible(false);
					rdbtnHave.setVisible(false);
					btnChoose.setVisible(true);
					txtChoosingPlayer.setVisible(true);
					lblChoosePlayer.setVisible(true);
					btnDone.setVisible(true);

				}
				if (rdbtnHave.isSelected()) {
					btnSubmitTeam.setVisible(false);
					rdbtnDontHave.setVisible(false);
					rdbtnHave.setVisible(false);
					lblTeamName.setVisible(true);
					btnSubmitHaveTeam.setVisible(true);
					textFieldTeam.setVisible(true);
				}
			}
		});
		btnSubmitTeam.setBounds(10, 88, 86, 23);
		contentPane.add(btnSubmitTeam);
		btnSubmitTeam.setVisible(false);

		btnSubmitAddress = new JButton("Submit");
		btnSubmitAddress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String name = txtYourName.getText();
				if (!name.matches("^[A-Z][a-z]+$")) {
					JOptionPane.showConfirmDialog(contentPane,
							"Your name must start with a capital letter \n followed by non-capital letters", "Error!", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
				} else {
					try {

						if (setName1(name)) {
							lblYourName.setText(name);
							btnSubmitAddress.setVisible(false);
							txtYourName.setVisible(false);
							rdbtnDontHave.setVisible(true);
							rdbtnHave.setVisible(true);
							rdbtnHave.setSelected(true);
							btnSubmitTeam.setVisible(true);
							return;
						}

						JOptionPane.showConfirmDialog(null, "That name is already taken, try again!", "Warning!",
								JOptionPane.OK_OPTION);

					}

					catch (IOException e) {
						JOptionPane.showConfirmDialog(null, "That name is already taken, try again!", "Warning!",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnSubmitAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnSubmitAddress.setVisible(false);
		btnSubmitAddress.setBounds(10, 58, 86, 23);
		contentPane.add(btnSubmitAddress);

		lblAddress = new JLabel("Address:");
		lblAddress.setBounds(10, 11, 86, 20);
		contentPane.add(lblAddress);
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ipAddress = textField.getText();
				try {
					connectToServer(ipAddress);
					lblAddress.setText("");
					btnConnect.setVisible(false);
					lblAddress.setVisible(false);
					textField.setVisible(false);
					btnSubmitAddress.setVisible(true);
					lblYourName.setVisible(true);
					txtYourName.setVisible(true);

				} catch (Exception e) {
					JOptionPane.showConfirmDialog(null, "Wrong address, try again!", "Warning!", JOptionPane.OK_OPTION);
				}

			}
		});
		btnConnect.setBounds(10, 60, 86, 23);
		contentPane.add(btnConnect);

		textAreaQuestion = new JTextArea();
		textAreaQuestion.setEditable(false);
		textAreaQuestion.setBounds(184, 31, 401, 116);
		contentPane.add(textAreaQuestion);
		textAreaQuestion.setVisible(false);

		btnSubmitAnswer = new JButton("Submit");
		btnSubmitAnswer.setVisible(false);
		btnSubmitAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSubmitAnswer.setBounds(340, 192, 89, 23);
		contentPane.add(btnSubmitAnswer);

		textFieldAnswer = new JTextField();
		textFieldAnswer.setBounds(291, 159, 187, 20);
		contentPane.add(textFieldAnswer);
		textFieldAnswer.setColumns(10);
		textFieldAnswer.setVisible(false);

		lblQuestion = new JLabel("Question");
		lblQuestion.setBounds(184, 11, 86, 14);
		contentPane.add(lblQuestion);
		lblQuestion.setVisible(false);

		lblAnswer = new JLabel("Answer");
		lblAnswer.setBounds(236, 162, 46, 14);
		contentPane.add(lblAnswer);

		textFieldTeam = new JTextField();
		textFieldTeam.setBounds(10, 59, 86, 20);
		contentPane.add(textFieldTeam);
		textFieldTeam.setColumns(10);
		textFieldTeam.setVisible(false);

		btnSubmitHaveTeam = new JButton("Submit");
		btnSubmitHaveTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String team = textFieldTeam.getText();
				if (!team.matches("^[A-Z][a-z]+$")) {
					JOptionPane.showMessageDialog(textArea,
							"Name of your team must start with a capital letter followed by non-capital letters",
							"Team", JOptionPane.OK_OPTION, null);
					return;
				} else {
					out.println("[HaveTeam]" + team + "|" + myName);
					myTeam = team;
					lblTeamName.setVisible(false);
					btnSubmitHaveTeam.setVisible(false);
					textFieldTeam.setVisible(false);
					initializeQuiz();

				}
			}
		});
		btnSubmitHaveTeam.setBounds(10, 88, 86, 23);
		contentPane.add(btnSubmitHaveTeam);
		btnSubmitHaveTeam.setVisible(false);

		lblTeamName = new JLabel("Team name:");
		lblTeamName.setBounds(10, 45, 86, 14);
		contentPane.add(lblTeamName);
		lblTeamName.setVisible(false);
		lblAnswer.setVisible(false);

	}

	BufferedReader in;
	PrintWriter out;
	public String myName;
	private JTextField textFieldAnswer;

	private String myTeam;
	private JTextField textFieldTeam;
	private JButton btnSubmitHaveTeam;
	private JLabel lblTeamName;

	public void connectToServer(String hexIpAddress) throws Exception {
		InetAddress iAddress = InetAddress.getByAddress(DatatypeConverter.parseHexBinary(hexIpAddress));
		String serverAddress = iAddress.toString().replaceFirst("/", "");

		Socket socket = new Socket(serverAddress, 11000);
		System.out.println("Connected to server");

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	public boolean setName1(String name) throws IOException {
		while (true) {
			String line = in.readLine();
			if (line.startsWith("Your name:")) {
				out.println(name);
				myName = name;
				line = in.readLine();
				if (line.startsWith("Name")) {
					return true;
				}
			}
			return false;

		}
	}

	public LinkedList<String> selectTeam(boolean hasTeam) throws Exception {
		LinkedList<String> playersToChoose = new LinkedList<String>();
		String choose;
		String[] players = null;
		while (true) {
			if (!hasTeam) {
				out.println("[No team]");
			}
			choose = in.readLine();
			if (choose.startsWith("[Choose]")) {
				choose = choose.replace("[Choose]", "");
				choose = choose.replace("[", "");
				choose = choose.replace("]", "");
				players = choose.split(", ");
				break;
			}
		}
		for (int i = 0; i < players.length; i++) {
			if (playersToChoose == null) {
				return null;
			}
			playersToChoose.add(players[i]);
		}
		return playersToChoose;
	}

	public void selectPlayer(String name) throws IOException {
		out.println("[select]" + myName + "|" + name);
	}

	public void initializeQuiz() {
		lblYourName.setText("[" + myTeam + "]" + myName);
		textAreaQuestion.setVisible(true);
		btnSubmitAnswer.setVisible(true);
		textFieldAnswer.setVisible(true);
		lblAnswer.setVisible(true);
		lblQuestion.setVisible(true);

	}
}
