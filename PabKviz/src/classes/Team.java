package classes;

import java.util.LinkedList;

public class Team {
	String name = "";
	LinkedList<String> players;
	int bodovi;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<String> getPlayers() {
		return players;
	}
	public void setPlayers(LinkedList<String> players) {
		this.players = players;
	}
	public int getBodovi() {
		return bodovi;
	}
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	public Team(LinkedList<String> players, int bodovi) {
		super();
		
		this.players = players;
		this.bodovi = bodovi;
	}
	
	
}
