package playerteam;

import java.util.LinkedList;

public class Team {
	private String name;
	private LinkedList<Player> players;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}
	
	public Team(String name, LinkedList<Player> players) {
		super();
		this.name = name;
		this.players = players;
	}
}
