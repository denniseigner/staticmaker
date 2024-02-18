package log.model;

public abstract class Player {
	
	private String firstname;
	private String surname;
	private String role;
	public Player() {
		
	}
	
	public Player(String firstname, String surname, String role) {
		
		this.firstname = firstname;
		this.surname = surname;
		this.role = role;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public abstract String getDatacenter(); 
	public abstract String getServer(); 
	@Override
	public String toString() {
		return "Player [firstname=" + firstname + ", surname=" + surname + ", role=" + role + "]";
	}

	

	

	
	
	
	

	
	
	
	

}
