package playerteam;

public class Player {
	private String name;
	private Team team;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Player(String name, Team team) {
		super();
		this.name = name;
		this.team = team;
	}
	
	
	
	
	
}
