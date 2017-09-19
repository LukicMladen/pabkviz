package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Team;
import networking.QuizMaster;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class QuizMasterGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldServer;
	private JButton btnDone;
	private JButton btnSend;
	private JTextArea textAreaQuestion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizMasterGUI frame = new QuizMasterGUI();
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
	public QuizMasterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblServerAddress = new JLabel("Server address");
		lblServerAddress.setBounds(10, 11, 90, 14);
		contentPane.add(lblServerAddress);

		textFieldServer = new JTextField();
		textFieldServer.setEditable(false);
		textFieldServer.setBounds(10, 27, 102, 20);
		contentPane.add(textFieldServer);
		textFieldServer.setColumns(10);
		textFieldServer.setText(QuizMaster.hexAddress);

		btnDone = new JButton("Start Quiz");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(JOptionPane.showConfirmDialog(contentPane,
						"Are you sure you want to start the quiz?") == JOptionPane.YES_OPTION)) {
					return;
				} else {
					textFieldServer.setVisible(false);
					lblServerAddress.setVisible(false);
					btnDone.setVisible(false);
					btnSend.setVisible(true);
					textAreaQuestion.setVisible(true);
				}
			}
		});
		btnDone.setBounds(10, 53, 102, 23);
		contentPane.add(btnDone);

		textAreaQuestion = new JTextArea();
		textAreaQuestion.setBounds(141, 26, 335, 83);
		contentPane.add(textAreaQuestion);
		textAreaQuestion.setVisible(false);

		btnSend = new JButton("Send");
		btnSend.setVisible(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Random r = new Random();
				for (Team t : QuizMaster.teams) {
					int i = r.nextInt(t.getPlayers().size());
					for (PrintWriter out : QuizMaster.writers) {
						out.println("[Question]" + t.getPlayers().get(i) + "|" + textAreaQuestion.getText());
					}
				}
			}
		});
		btnSend.setBounds(264, 126, 89, 23);
		contentPane.add(btnSend);

	}
}
