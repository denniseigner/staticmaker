package log.model;

import java.util.Arrays;
import java.util.List;

public class RaidGroup {	
	private String groupname;
	private int amount;
	private String continent;
	private List<Player> members;
	public RaidGroup() {
		
	}
	
	public RaidGroup( String groupname,int amount, String region ) {
		
		
		this.groupname = groupname;
		this.amount = amount;
		this.continent = region;
		
	}
	public RaidGroup(int amount, String groupname, String region, List<Player> members) {
		
		
		this.groupname = groupname;
		this.amount = amount;
		this.continent = region;
		this.members = members;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getServerchoice() {
		return continent;
	}
	public void setServerchoice(String serverchoice) {
		this.continent = serverchoice;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public List<Player> getMembers() {
		return members;
	}
	public void setMembers(List<Player> members) {
		this.members = members;
	}
	
	@Override
	public String toString() {
		return "RaidGroup [ groupname=" + groupname + "amount=" + amount + ", continent=" + continent + ", members="
				+ members.toString()+ "]";
	}
	
		
	

}
