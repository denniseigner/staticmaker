package log.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.management.DescriptorAccess;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import log.model.*;

/**
 *@Dave 
 *Service transfers the data to this Class so it can add the parameters into the DB
 */
public class LogDaoImpl implements LogDaoInterface {
/**
 * getting the raidgroup of choice
 */
	@Override
	public List<RaidGroup> getStatic(String name) throws FileNotFoundException, IOException, SQLException {
		List<RaidGroup> Grouplist = new ArrayList<>();
		List<Player> players = new ArrayList<>();

		String sql = "select * from players where static_name = '" + name + "'; ";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			if (res.getString("region").equalsIgnoreCase("Europe")) {
				players.add(new Europe(res.getString("firstname"), res.getString("surname"), res.getString("f_role"),
						res.getString("f_datacenter"), res.getString("f_server")));
			} else if (res.getString("region").equalsIgnoreCase("America")) {
				players.add(new America(res.getString("firstname"), res.getString("surname"), res.getString("f_role"),
						res.getString("f_datacenter"), res.getString("f_server")));
			} else if (res.getString("region").equalsIgnoreCase("Japan")) {
				players.add(new Japan(res.getString("firstname"), res.getString("surname"), res.getString("f_role"),
						res.getString("f_datacenter"), res.getString("f_server")));
			}
		}

		ResultSet res2 = stmt.executeQuery("select * from static where static_name = '" + name + "'; ");
		while (res2.next()) {
			Grouplist.add(
					new RaidGroup(res2.getString("static_name"), res2.getInt("players"), res2.getString("region")));
		}
		Grouplist.get(0).setMembers(players);
		con.close();

		return Grouplist;
	}
/**
 * taking out all the player information
 */
	@Override
	public List<Player> getPlayers() throws FileNotFoundException, IOException, SQLException {
		String sql = "select * from players;";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		List<Player> getPlayers = new ArrayList<>();
		while (res.next()) {
			if (res.getString("region").equalsIgnoreCase("Europe")) {
				getPlayers.add(new Europe(res.getString("firstname"), res.getString("surname"), res.getString("f_role"),
						res.getString("f_datacenter"), res.getString("f_server")));
			} else if (res.getString("region").equalsIgnoreCase("America")) {
				getPlayers.add(new America(res.getString("firstname"), res.getString("surname"),
						res.getString("f_role"), res.getString("f_datacenter"), res.getString("f_server")));
			} else if (res.getString("region").equalsIgnoreCase("Japan")) {
				getPlayers.add(new Japan(res.getString("firstname"), res.getString("surname"), res.getString("f_role"),
						res.getString("f_datacenter"), res.getString("f_server")));
			}
		}
		con.close();
		return getPlayers;

	}
/**
 * once checked via Service the player gets added into the database
 */
	@Override
	public void createPlayer(String firstname, String surname, String role, String datacenter, String server,
			String groupname, String region) throws FileNotFoundException, IOException, SQLException {

		String sql = "INSERT INTO players (firstname, surname, f_role, f_datacenter, f_server, static_name, region) VALUES ('"
				+ firstname + "', '" + surname + "', '" + role + "', '" + datacenter + "', '" + server + "', '"
				+ groupname + "', '" + region + "');";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		ResultSet res = stmt.executeQuery("SELECT * FROM static WHERE static_name = '" + groupname + "'");
		int i = 0;
		while (res.next()) {
			i = res.getInt("players");

		}
		i++;
		stmt.executeUpdate("UPDATE static SET players = '" + i + "' where static_name = '" + groupname + "';");
		ResultSet res1 = stmt.executeQuery("SELECT * FROM region WHERE Region = '" + region + "'; ");
		int j = 0;
		while (res1.next()) {
			j = res1.getInt("players");

		}
		j++;
		stmt.executeUpdate("UPDATE region SET players = '" + j + "' where Region = '" + region + "';");
		con.close();
		JOptionPane.showMessageDialog(null, "Character was added", "confirm", JOptionPane.INFORMATION_MESSAGE);

	}
/**
 * deleting the player of choice
 */
	@Override
	public void deletePlayer(String firstname, String surname, String role, String datacenter, String server,
			String region) throws FileNotFoundException, IOException, SQLException {

		String sql = "delete from players where firstname = '" + firstname + "';";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery("select * from players where firstname = '" + firstname + "'; ");
		String groupname = "";
		while (res.next()) {
			groupname = res.getString("static_name");

		}
		stmt.executeUpdate(sql);
		ResultSet res1 = stmt.executeQuery("select * from static where static_name = '" + groupname + "'; ");
		int i = 0;
		while (res1.next()) {
			i = res1.getInt("players");

		}
		i--;
		stmt.executeUpdate("UPDATE static SET players = '" + i + "' where static_name = '" + groupname + "';");
		ResultSet res2 = stmt.executeQuery("select * from region where Region = '" + region + "'; ");
		int j = 0;
		while (res2.next()) {
			j = res2.getInt("players");

		}
		j--;
		stmt.executeUpdate("UPDATE region SET players = '" + j + "' where Region = '" + region + "';");
		con.close();
		JOptionPane.showMessageDialog(null, "Character was deleted", "confirm", JOptionPane.INFORMATION_MESSAGE);

	}
/**
 * getting all the static lists that exist
 */
	@Override
	public List<RaidGroup> allStatic() throws FileNotFoundException, IOException, SQLException {
		String sql = "select * from static;";
		List<RaidGroup> Grouplist = new ArrayList<>();
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			Grouplist.add(new RaidGroup(res.getString("static_name"), res.getInt("players"), res.getString("region")));

		}
		return Grouplist;
	}
/**
 * updating the role of choice
 */
	@Override
	public void updateRole(String firstname, String surname, String role, String datacenter, String server)
			throws FileNotFoundException, IOException, SQLException {
		String sql = "UPDATE players SET role = '" + role + "' where firstname = '" + firstname + "';";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		con.close();
		JOptionPane.showMessageDialog(null, "Character Role was updated", "confirm", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public int countPlayer() throws FileNotFoundException, IOException, SQLException {
		List<Player> amount = getPlayers();
		int numberamount = amount.size();
		return numberamount;
	}
/**
 * gettinng all the Region for the table
 */
	@Override
	public List<Region> totalRegion() throws FileNotFoundException, IOException, SQLException {
		List<Region> region = new ArrayList<>();
		String sql = "select * from region;";
		List<RaidGroup> Grouplist = new ArrayList<>();
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			region.add(new Region(res.getString("Region"), res.getInt("statics"), res.getInt("players")));

		}
		return region;
	}
/**
 * creating a new Static
 */
	@Override
	public void createStatic(String name, String region) throws FileNotFoundException, IOException, SQLException {
		String sql = "insert into static(static_name, players, region) values ('" + name + "', '" + 0 + "', '" + region
				+ "');";
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		ResultSet res = stmt.executeQuery("select * from region where Region = '" + region + "'; ");
		int j = 0;
		while (res.next()) {
			j = res.getInt("statics");

		}
		j++;
		stmt.executeUpdate("UPDATE region SET statics = '" + j + "' where Region = '" + region + "';");
		con.close();
		JOptionPane.showMessageDialog(null, "Static was added", "confirm", JOptionPane.INFORMATION_MESSAGE);

	}

}