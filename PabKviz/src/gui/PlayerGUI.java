package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.DatatypeConverter;
import javax.swing.JButton;
import javax.swing.JTextField;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGUI frame = new PlayerGUI();
					frame.setVisible(true);

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

		textField = new JTextField();
		textField.setBounds(10, 31, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		JLabel lblYourName = new JLabel("Your name:");
		lblYourName.setVisible(false);
		lblYourName.setBounds(10, 11, 86, 20);
		contentPane.add(lblYourName);

		txtYourName = new JTextField();
		txtYourName.setText("");
		txtYourName.setVisible(false);
		txtYourName.setColumns(10);
		txtYourName.setBounds(10, 31, 86, 20);
		contentPane.add(txtYourName);

		JTextArea textArea = new JTextArea();
		textArea.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (line.startsWith("[select]")) {
								line = line.replace("[select]", "");
								System.out.println(line);
								choose = line.split("\\|");
								System.out.println(choose[0]);
								System.out.println(choose[1]);
								System.out.println(myName);
								if (choose[1].equals(myName)) {
									int response = JOptionPane.showConfirmDialog(null,
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
									JOptionPane.showMessageDialog(null, accepting[1] + " accepted your invite!",
											"Accepted", JOptionPane.OK_OPTION);
								}
							}

							if (line.startsWith("[no]")) {
								line = line.replace("[no]", "");
								String[] accepting = line.split("\\|");
								if (accepting[0].equals(myName)) {
									JOptionPane.showMessageDialog(null, accepting[1] + " declined your invite!",
											"Declined", JOptionPane.OK_OPTION);
								}
							}
						}
					}
				};
				Thread t = new Thread(runnable);
				t.start();
			}
		});

		textArea.setEditable(false);
		textArea.setBounds(184, 29, 401, 118);
		contentPane.add(textArea);
		textArea.setVisible(false);

		JRadioButton rdbtnHave = new JRadioButton("I have a team");
		buttonGroup.add(rdbtnHave);
		rdbtnHave.setBounds(10, 30, 135, 23);
		contentPane.add(rdbtnHave);
		rdbtnHave.setVisible(false);

		JRadioButton rdbtnDontHave = new JRadioButton("I don't have a team");
		buttonGroup.add(rdbtnDontHave);
		rdbtnDontHave.setBounds(10, 58, 135, 23);
		contentPane.add(rdbtnDontHave);
		rdbtnDontHave.setVisible(false);

		txtChoosingPlayer = new JTextField();
		txtChoosingPlayer.setBounds(10, 59, 86, 20);
		contentPane.add(txtChoosingPlayer);
		txtChoosingPlayer.setColumns(10);
		txtChoosingPlayer.setVisible(false);

		JLabel lblChoosePlayer = new JLabel("Choose player:");
		lblChoosePlayer.setBounds(10, 42, 122, 20);
		contentPane.add(lblChoosePlayer);
		lblChoosePlayer.setVisible(false);

		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (textArea.getText().contains(txtChoosingPlayer.getText()))
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
		btnChoose.setBounds(10, 88, 86, 23);
		contentPane.add(btnChoose);
		btnChoose.setVisible(false);

		JButton btnRefreshList = new JButton("Refresh");
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LinkedList<String> players = null;

				try {
					players = selectTeam(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String forTextArea = "";
				for (String string : players) {
					if (!string.equalsIgnoreCase(lblYourName.getText())) {
						forTextArea += string + "\n";
					}
				}
				textArea.setText(forTextArea);

			}
		});
		btnRefreshList.setBounds(340, 158, 89, 23);
		contentPane.add(btnRefreshList);
		btnRefreshList.setVisible(false);

		JButton btnSubmitTeam = new JButton("Submit");
		btnSubmitTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					btnRefreshList.setVisible(true);
					btnChoose.setVisible(true);
					txtChoosingPlayer.setVisible(true);
					lblChoosePlayer.setVisible(true);

				}
				if (rdbtnHave.isSelected()) {

				}
			}
		});
		btnSubmitTeam.setBounds(10, 88, 86, 23);
		contentPane.add(btnSubmitTeam);
		btnSubmitTeam.setVisible(false);

		JButton btnSubmitAddress = new JButton("Submit");
		btnSubmitAddress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String name = txtYourName.getText();
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

					JOptionPane.showConfirmDialog(null, "/That name is already taken, try again!", "Warning!",
							JOptionPane.OK_OPTION);

				}

				catch (IOException e) {
					JOptionPane.showConfirmDialog(null, "That name is already taken, try again!", "Warning!",
							JOptionPane.OK_OPTION);
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

		JLabel lblAddress = new JLabel("Address:");
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

	}

	BufferedReader in;
	PrintWriter out;
	public String myName;

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

	public String[] popUpInvite() throws IOException {
		String line = in.readLine();
		String[] choosingPlayers = null;
		if (line.startsWith("[mistake]")) {
			return null;
		}
		if (line.startsWith("[choose]")) {
			line = line.replace("[choose]", "");
			choosingPlayers = line.split("\\|");
			if (myName.equals(choosingPlayers[0])) {
				JOptionPane.showConfirmDialog(null, "Do you want to play with " + choosingPlayers[1] + " on the team?",
						"Invite", JOptionPane.YES_NO_OPTION);
			}
		}
		return null;
	}

	public void Invite(String inviter, String invited) {
		if (invited.equals(myName))
			JOptionPane.showConfirmDialog(null, inviter + " has invited you!");
	}

}
