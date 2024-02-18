package log.model;

public class Europe extends Player{
	String datacenter;
	String server;
	public Europe(String firstname, String surname, String role, String datacenter, String server) {
		super(firstname, surname, role);
		this.datacenter = datacenter;
		this.server = server;
	}
	@Override
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
		return "Europe [Player: " + super.getFirstname() + " "+ super.getSurname() + " Role: "+ super.getRole()  +" datacenter=" + datacenter + ", server=" + server + "]";
	}

	
	
	
	

}
