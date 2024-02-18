package log.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import log.model.Player;
import log.model.RaidGroup;
import log.model.Region;

public interface PlayerServiceInterface {
	
	public abstract void createPlayer(String firstname, String surname, String role, String datacenter, String server, String groupname)throws FileNotFoundException, IOException, SQLException, Error;
	public abstract List<RaidGroup> getStatic(String groupname) throws FileNotFoundException, IOException, SQLException;
	public abstract void deletePlayer (String firstname, String surname, String role, String datacenter, String server) throws FileNotFoundException, IOException, SQLException, Error; 
	public abstract void updateRole(String firstname, String surname, String role, String datacenter, String server)throws FileNotFoundException, IOException, SQLException, Error;
	public abstract int countPlayer() throws FileNotFoundException, IOException, SQLException;
	public abstract List<Region> totalRegion()throws FileNotFoundException, IOException, SQLException;
	public abstract void createStatic(String name, String region)throws FileNotFoundException, IOException, SQLException, Error;
	public abstract void exportGroup(String name)throws FileNotFoundException, IOException, SQLException, Error;

}
