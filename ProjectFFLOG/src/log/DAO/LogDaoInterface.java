package log.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import log.model.Player;
import log.model.RaidGroup;
import log.model.Region;


public interface LogDaoInterface {
	
	public abstract void createPlayer(String firstname, String surname, String role, String datacenter, String server,  String groupname, String region)throws FileNotFoundException, IOException, SQLException;
	public abstract List<RaidGroup> getStatic(String name) throws FileNotFoundException, IOException, SQLException;
	public abstract void deletePlayer (String firstname, String surname, String role, String datacenter, String server, String region) throws FileNotFoundException, IOException, SQLException; 
	public abstract void updateRole(String firstname, String surname, String role, String datacenter, String server)throws FileNotFoundException, IOException, SQLException;
	public abstract int countPlayer() throws FileNotFoundException, IOException, SQLException;
	public abstract List<Player> getPlayers()throws FileNotFoundException, IOException, SQLException;
	public abstract List<RaidGroup> allStatic()throws FileNotFoundException, IOException, SQLException;
	public abstract List<Region> totalRegion()throws FileNotFoundException, IOException, SQLException;
	public abstract void createStatic(String name, String region)throws FileNotFoundException, IOException, SQLException;
	
	

}
