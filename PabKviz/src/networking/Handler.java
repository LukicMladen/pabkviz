package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import classes.Team;

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
				for (PrintWriter out : QuizMaster.writers) {
					out.println("[Choose]" + QuizMaster.playersNoTeam);
				}
			}

			while (true) {

				String line = in.readLine();

				if (line.startsWith("[select]")) {
					for (PrintWriter out : QuizMaster.writers) {
						out.println(line);
					}

				}

				if (line.startsWith("[yes]")) {

					for (PrintWriter out : QuizMaster.writers) {
						out.println(line);
					}

					line = line.replace("[yes]", "");
					String[] team = line.split("\\|");
					QuizMaster.playersNoTeam.remove(team[0]);
					QuizMaster.playersNoTeam.remove(team[1]);

					for (PrintWriter out : QuizMaster.writers) {
						out.println("[Choose]" + QuizMaster.playersNoTeam);
					}

					System.out.println(team[0] + " " + team[1]);
					boolean done = false;
					for (Team t : QuizMaster.teams) {

						if (t.getPlayers().contains(team[1])) {
							t.getPlayers().add(team[0]);
							done = true;
						} else if (t.getPlayers().contains(team[0])) {
							t.getPlayers().add(team[1]);
							done = true;
						}
					}
					if (!done) {
						LinkedList<String> players = new LinkedList<>();
						players.add(team[0]);
						players.add(team[1]);
						Team toAdd = new Team(players, 0);
						QuizMaster.teams.add(toAdd);
						System.out.println(toAdd.getPlayers());
					}

				}

				if (line.startsWith("[team]")) {
					String[] teamPlayer;
					line = line.replace("[team]", "");
					teamPlayer = line.split("\\|");
					System.out.println(teamPlayer);
					boolean taken = false;
					for (Team t : QuizMaster.teams) {
						if (t.getName().equals(teamPlayer[0])) {
							out.println("[taken]" + teamPlayer[1]);
							taken = true;
						}
					}
					if (!taken) {
						for (Team t : QuizMaster.teams) {
							for (String p : t.getPlayers()) {
								if (p.equals(teamPlayer[1])) {
									t.setName(teamPlayer[0]);
									out.println("[nottaken]" + teamPlayer[1]);
									for (String p1 : t.getPlayers()) {
										for (PrintWriter out : QuizMaster.writers) {
											out.println("[teamName]" + t.getName() + "|" + p1);
										}

									}
								}
							}
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
