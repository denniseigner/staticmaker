package log.model;

public class Japan extends Player{
	String datacenter;
	String server;
	public Japan(String firstname, String surname, String role, String datacenter, String server) {
		super(firstname, surname, role);
		this.datacenter = datacenter;
		this.server = server;
	}
	public String getDatacenter() {
		return datacenter;
	}
	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	@Override
	public String toString() {
		return "Japan [Player: " + super.getFirstname() + " "+ super.getSurname() + " Role: "+ super.getRole()  +" datacenter=" + datacenter + ", server=" + server + "]";
	}
	
	

}
