package log.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import log.model.*;
import log.DAO.*;

/**
 * @Dave service for checking the given information
 * 
 * 
 * 
 * 
 */
public class PSImplementation implements PlayerServiceInterface {

	/**
	 * creating Player while also checking the legitimacy if it actually exist via
	 * the website FFLOG
	 */
	@Override
	public void createPlayer(String firstname, String surname, String role, String datacenter, String server,
			String groupname) throws FileNotFoundException, IOException, SQLException, Error {
		LogDaoInterface db = new LogDaoImpl();
		String region = "";
		if (role == null | datacenter == null | server == null | groupname == null) {
			throw new Error();
		}
		if (datacenter.equalsIgnoreCase("Chaos") | datacenter.equalsIgnoreCase("Light")) {
			region = "Europe";
		} else if (datacenter.equalsIgnoreCase("Aether") | datacenter.equalsIgnoreCase("Crystal")
				| datacenter.equalsIgnoreCase("Dynamis") | datacenter.equalsIgnoreCase("Primal")) {
			region = "America";
		} else if (datacenter.equalsIgnoreCase("Elemental") | datacenter.equalsIgnoreCase("Gaia")
				| datacenter.equalsIgnoreCase("Mana") | datacenter.equalsIgnoreCase("Meteor")) {
			region = "Japan";
		}
		boolean checker = characterCheck(firstname, surname, server, region);
		if (checker == true) {
			JOptionPane.showMessageDialog(new JFrame(), "Character doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
		}

		List<RaidGroup> grouplist = db.getStatic(groupname);
		List<Player> liste = new ArrayList<>();
		for (int i = 0; i < grouplist.get(0).getMembers().size(); i++) {
			liste.add(grouplist.get(0).getMembers().get(i));

		}

		if (firstname.length() > 15 | firstname.contains(" ")
				| firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;") | firstname.matches(".*\\d.'")) {

			throw new Error();

		}
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).getFirstname().equals(firstname)) {
				throw new Error();
			}
		}

		if (surname.length() > 15 | surname.contains(" ") | firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;")
				| surname.matches(".*\\d.'")) {

			throw new Error();

		}
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).getSurname().equals(surname)) {
				throw new Error();
			}
		}

		db.createPlayer(firstname, surname, role, datacenter, server, groupname, region);

	}

	/**
	 * calling out a specific static of choice
	 */
	@Override
	public List<RaidGroup> getStatic(String groupname) throws FileNotFoundException, IOException, SQLException {
		LogDaoInterface dao = new LogDaoImpl();
		List<RaidGroup> group = dao.getStatic(groupname);
		return group;
	}

	/**
	 * 
	 * deleting the selected player
	 */
	@Override
	public void deletePlayer(String firstname, String surname, String role, String datacenter, String server)
			throws FileNotFoundException, IOException, SQLException, Error {
		LogDaoInterface db = new LogDaoImpl();
		String region = "";
		if (datacenter.equalsIgnoreCase("Chaos") | datacenter.equalsIgnoreCase("Light")) {
			region = "Europe";
		} else if (datacenter.equalsIgnoreCase("Aether") | datacenter.equalsIgnoreCase("Crystal")
				| datacenter.equalsIgnoreCase("Dynamis") | datacenter.equalsIgnoreCase("Primal")) {
			region = "America";
		} else if (datacenter.equalsIgnoreCase("Elemental") | datacenter.equalsIgnoreCase("Gaia")
				| datacenter.equalsIgnoreCase("Mana") | datacenter.equalsIgnoreCase("Meteor")) {
			region = "Japan";

		}

		if (firstname.length() > 15 | firstname.contains(" ")
				| firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;") | firstname.matches(".*\\d.'")) {

			throw new Error();

		}

		if (surname.length() > 15 | surname.contains(" ") | firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;")
				| surname.matches(".*\\d.'")) {

			throw new Error();

		}

		db.deletePlayer(firstname, surname, role, datacenter, server, region);

	}

	/**
	 * updating the Role of the player
	 */
	@Override
	public void updateRole(String firstname, String surname, String role, String datacenter, String server)
			throws FileNotFoundException, IOException, SQLException, Error {
		LogDaoInterface db = new LogDaoImpl();
		List<Player> liste = db.getPlayers();
		if (firstname.length() > 15 | firstname.contains(" ")
				| firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;") | firstname.matches(".*\\d.'")) {

			throw new Error();

		}
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).equals(firstname)) {
				throw new Error();
			}
		}

		if (surname.length() > 15 | surname.contains(" ") | firstname.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;")
				| surname.matches(".*\\d.'")) {

			throw new Error();

		}

		db.updateRole(firstname, surname, role, datacenter, server);

	}

	/**
	 * counting the amount of player
	 */
	@Override
	public int countPlayer() throws FileNotFoundException, IOException, SQLException {
		LogDaoInterface count = new LogDaoImpl();
		int result = count.countPlayer();
		return result;
	}

	/**
	 * returning the region table
	 */
	@Override
	public List<Region> totalRegion() throws FileNotFoundException, IOException, SQLException {
		LogDaoInterface dao = new LogDaoImpl();
		List<Region> region = dao.totalRegion();

		return region;
	}

	/**
	 * creating the static method
	 */
	@Override
	public void createStatic(String name, String region)
			throws FileNotFoundException, IOException, SQLException, Error {
		LogDaoInterface dao = new LogDaoImpl();
		List<RaidGroup> group = dao.allStatic();
		if (region == null) {
			throw new Error();
		}
		if (name.length() > 15 | name.contains(" ") | name.contains("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].,:;")
				| name.matches(".*\\d.'")) {

			throw new Error();

		}
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).getGroupname().equalsIgnoreCase(name)) {
				throw new Error();
			}
		}
		dao.createStatic(name, region);

	}
/**
 * exporting a specific group choice
 */
	@Override
	public void exportGroup(String name) throws FileNotFoundException, IOException, SQLException, Error {
		LogDaoInterface dao = new LogDaoImpl();
		List<RaidGroup> group = dao.getStatic(name);
		List<String> text = new ArrayList<>();
		text.add(group.get(0).getGroupname() + " " + group.get(0).getContinent() + " Players: "
				+ group.get(0).getAmount());
		for (int i = 0; i < group.get(0).getMembers().size(); i++) {
			text.add(group.get(0).getMembers().toString());

		}
		String filename = JOptionPane.showInputDialog("How shall the file be called") + ".txt";
		File file = new File("." + File.separator + filename);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (String line : text)
				writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "List got exported", "confirm", JOptionPane.INFORMATION_MESSAGE);

	}
/**
 * checking via FFLOG if the player exist or not, while its not the most elegant method resolved as API Request and Response
 * would have probably be more efficient, its the fastest and easiest way at least
 * 
 * 
 */
	public boolean characterCheck(String firstname, String surname, String server, String region) {

		switch (region) {
		case "Europe":
			region = "eu";
			break;
		case "America":
			region = "na";
			break;
		case "Japan":
			region = "jp";
			break;

		}
		String urlString = "https://www.fflogs.com/character/" + region + "/" + server + "/" + firstname + "%20"
				+ surname;
		boolean checker = false;
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();
			conn.disconnect();

			System.out.println("Response from URL:");
			System.out.println(response.toString());
			if (response.toString().contains("No character could be found with that name")) {
				checker = true;
			} else {
				checker = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return checker;
	}

}
