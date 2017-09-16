package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Handler extends Thread {
	private String name;

	private Socket socket;

	private BufferedReader in;

	private PrintWriter out;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				out.println("Your name: ");
				name = in.readLine();
				if (name == null) {
					continue;
				}
				synchronized (QuizMaster.players) {
					if (!QuizMaster.players.contains(name)) {
						QuizMaster.players.add(name);
						out.println("Name accepted!");
						QuizMaster.writers.add(out);
						break;
					} else
						out.println("Your name: ");
				}
				continue;
			}

			if (in.readLine().startsWith("[No team]")) {
				synchronized (QuizMaster.playersNoTeam) {
					if (!QuizMaster.playersNoTeam.contains(name)) {
						QuizMaster.playersNoTeam.add(name);
					}
				}
				out.println("[Choose]" + QuizMaster.playersNoTeam);
			}

			while (true) {

				String line = in.readLine();
				if (line.startsWith("[No team]")) {
					out.println("[Choose]" + QuizMaster.playersNoTeam);
				}

				else if (line.startsWith("[select]")) {
					for (PrintWriter out : QuizMaster.writers) {
						out.println(line);
					}

				} else
					out.println("[mistake]");

				if (line.startsWith("[yes]")) {

					for (PrintWriter out : QuizMaster.writers) {
						out.println(line);
					}

					line = line.replace("[yes]", "");
					String[] team = line.split("\\|");
					System.out.println(team);
					if (QuizMaster.teams.isEmpty()) {
						QuizMaster.teams.add(team);
						System.out.println(QuizMaster.teams);
					} else {
						for (String[] teams : QuizMaster.teams) {
							for (int i = 0; i < teams.length; i++) {
								if (teams[i].equals(team[0])) {
									teams[i + 1] = team[1];
								} else if (teams[i].equals(team[1])) {
									teams[i + 1] = team[0];
								} else
									QuizMaster.teams.add(team);
							}
							System.out.println(teams);
						}
					}

				}
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.run();
	}
}
