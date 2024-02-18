package log.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.model.RaidGroup;
import javafx.scene.control.TableColumn;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import log.DAO.LogDaoImpl;
import log.DAO.LogDaoInterface;
import log.model.*;
import log.service.*;
import log.service.Error;

/**
 * Controlls and functions to operate the GUI
 * 
 * 
 */
public class MainSceneController implements Initializable {

	@FXML
	private TableView tbListAll;
	@FXML
	private TableColumn<Region, String> RegionView;

	@FXML
	private TableColumn<Region, Integer> StaticView;

	@FXML
	private TableColumn<Region, Integer> PlayerView;

	@FXML
	private TableView tbStaticList;
	@FXML
	private TableView tbStaticListaddon;
	@FXML
	private TableColumn<Player, String> FirstNameStatic;

	@FXML
	private TableColumn<Player, String> SurNameStatic;

	@FXML
	private TableColumn<Player, String> RoleStatic;

	@FXML
	private TableColumn<Player, String> DCStatic;

	@FXML
	private TableColumn<Player, String> ServerStatic;

	@FXML
	private TableColumn<RaidGroup, String> StaticStatic;

	@FXML
	private TableColumn<RaidGroup, String> RegionStatic;
	@FXML
	private ChoiceBox DDList;
	@FXML
	private ChoiceBox DDStatic;
	@FXML
	private ChoiceBox DDRole;

	@FXML
	private ChoiceBox DDDataCenter;

	@FXML
	private ChoiceBox DDServerCreate;

	@FXML
	private ChoiceBox DDStaticList;

	@FXML
	private ChoiceBox DDRegion;

	@FXML
	private TextField tf_fname;

	@FXML
	private TextField tf_sname;

	@FXML
	private TextField tfgroupname;

	@FXML
	private Button RefreshListClick;

	@FXML
	private Button StaticTable;

	@FXML
	private Button ExpListClick;

	@FXML
	private Button ChangeListClick;

	@FXML
	private Button OpenList;

	@FXML
	private Button OpenExport;

	@FXML
	private Button addplayer;

	@FXML
	private Button CreateGroupButton;
	@FXML
	private Button DeletePlayer;

	/**Method to delete playsers
	 * 
	 * 
	 * 
	 */
	@FXML
	private void DeletePlayer(ActionEvent event) {
		PlayerServiceInterface service = new PSImplementation();
		String firstname = tf_fname.getText();
		String surname = tf_sname.getText();
		String Role = (String) DDRole.getValue();
		String DC = (String) DDDataCenter.getValue();
		String Server = (String) DDServerCreate.getValue();
		try {
			service.deletePlayer(firstname, surname, Role, DC, Server);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDRole.getItems().clear();
		DDRole.getItems().addAll("Tank", "Healer", "DPS");
		DDDataCenter.getItems().clear();
		DDDataCenter.getItems().addAll("Aether", "Crystal", "Dynamis", "Primal", "Chaos", "Light", "Elemental", "Gaia",
				"Mana", "Meteor");
		DDDataCenter.setOnAction(this::serverchoice);
		
		LogDaoInterface dao = new LogDaoImpl();
		List<String> Groupnames = new ArrayList<>();
		try {
			for (int i = 0; i < dao.allStatic().size(); i++) {
				Groupnames.add(dao.allStatic().get(i).getGroupname());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDStatic.getItems().clear();
		DDStatic.getItems().addAll(Groupnames);
		DDStatic.setOnAction(arg01 -> {
			try {
				fillstaticlist(arg01);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@FXML
	private void ListTable(ActionEvent event) {
		// Add functionality for sorting the table
	}

	@FXML
	private void OpenList(ActionEvent event) {
		// Add functionality for opening list
	}
	
	/**Method to export the Raiding Group via txt file
	 * 
	 * 
	 */
	@FXML
	private void ExpListClick(ActionEvent event) {
		PlayerServiceInterface service = new PSImplementation();
		String choice = (String)DDList.getValue();
		try {
			service.getStatic(choice);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			service.exportGroup(choice);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**button to change the Role of a player
	 * 
	 * 
	 */
	@FXML
	private void ChangeListClick(ActionEvent event) {
		PlayerServiceInterface service = new PSImplementation();		
		JTextField firstname = new JTextField();
		JTextField surname = new JTextField();
		String[] datacenter = { "Aether", "Crystal", "Dynamis", "Primal", "Chaos", "Light", "Elemental", "Gaia", "Mana",
				"Meteor" };
		JComboBox<String> choicebox = new JComboBox<>(datacenter);
		JComboBox<String> choicebox2 = new JComboBox<>(datacenter);
		JTextField server = new JTextField();

		choicebox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String selectedOption = (String) choicebox.getSelectedItem();
				changechoice(choicebox2, selectedOption);

			}
		});
		Object[] fields = { "Firstname", firstname, "Surename", surname, "Data Center", choicebox, "Server",
				choicebox2 };
		int result = JOptionPane.showConfirmDialog(null, fields, "Write Character details",
				JOptionPane.OK_CANCEL_OPTION);
		String enteredFirstname = "";
		String enteredSurname = "";
		String enteredDatacenter = "";
		String enteredServer = "";
		if (result == JOptionPane.OK_OPTION) {
			enteredFirstname = firstname.getText();
			enteredSurname = surname.getText();
			enteredDatacenter = (String) choicebox.getSelectedItem();
			enteredServer = (String) choicebox2.getSelectedItem();
		}
		 String[] buttons = { "Tank", "Healer", "DPS", "Cancel" };

		    int rc = JOptionPane.showOptionDialog(null, "Change into Role?", "Confirmation",
		        JOptionPane.OK_CANCEL_OPTION, 0, null, buttons, buttons[2]);
		    String role = "";
		    switch(rc) {
		    case 0 :
		    	role = "Tank";
		    case 1 :
		    	role = "Healer";
		    case 2:
		    	role = "DPS";
		    }
		    
			try {
				service.updateRole(enteredFirstname, enteredSurname, role, enteredDatacenter, enteredServer);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Error e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

	}

	@FXML
	private void OpenExport(ActionEvent event) {
		// Add functionality for opening export
	}
	/**Method to add a new Player
	 * 
	 * 
	 */
	@FXML
	private void addplayer(ActionEvent event) {
		PlayerServiceInterface service = new PSImplementation();
		String firstname = tf_fname.getText();
		String surname = tf_sname.getText();
		String Role = (String) DDRole.getValue();
		String DC = (String) DDDataCenter.getValue();
		String Server = (String) DDServerCreate.getValue();
		String group = (String) DDStaticList.getValue();
		try {
			service.createPlayer(firstname, surname, Role, DC, Server, group);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDRole.getItems().clear();
		DDRole.getItems().addAll("Tank", "Healer", "DPS");
		DDDataCenter.getItems().clear();
		DDDataCenter.getItems().addAll("Aether", "Crystal", "Dynamis", "Primal", "Chaos", "Light", "Elemental", "Gaia",
				"Mana", "Meteor");
		DDDataCenter.setOnAction(this::serverchoice);
		
		LogDaoInterface dao = new LogDaoImpl();
		List<String> Groupnames = new ArrayList<>();
		try {
			for (int i = 0; i < dao.allStatic().size(); i++) {
				Groupnames.add(dao.allStatic().get(i).getGroupname());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDStatic.getItems().clear();
		DDStatic.getItems().addAll(Groupnames);
		DDStatic.setOnAction(arg01 -> {
			try {
				fillstaticlist(arg01);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	/**Method to create a new Raiding Group
	 * 
	 * 
	 */
	@FXML
	private void CreateGroupButton(ActionEvent event) {
		PlayerServiceInterface service = new PSImplementation();
		String groupname = tfgroupname.getText();
		String continent = (String) DDRegion.getValue();
		tfgroupname.clear();
		DDRegion.getItems().clear();
		DDRegion.getItems().addAll("Europe", "Japan", "America");
		try {
			service.createStatic(groupname, continent);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogDaoInterface dao = new LogDaoImpl();
		List<String> Groupnames = new ArrayList<>();
		try {
			for (int i = 0; i < dao.allStatic().size(); i++) {
				Groupnames.add(dao.allStatic().get(i).getGroupname());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDStatic.getItems().clear();
		DDStatic.getItems().addAll(Groupnames);
		DDStatic.setOnAction(arg01 -> {
			try {
				fillstaticlist(arg01);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		DDList.getItems().clear();
		DDList.getItems().addAll(Groupnames);

	}
	/**Initializer to start the function
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PlayerServiceInterface service = new PSImplementation();
		LogDaoInterface dao = new LogDaoImpl();
		List<Region> region = null;
		try {
			region = service.totalRegion();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBRegionList list = new DBRegionList(region);
		RegionView.setCellValueFactory(new PropertyValueFactory<Region, String>("region"));
		StaticView.setCellValueFactory(new PropertyValueFactory<Region, Integer>("groupnumbers"));
		PlayerView.setCellValueFactory(new PropertyValueFactory<Region, Integer>("playernumbers"));
		tbListAll.setItems(list.getRegion());
		List<String> Groupnames = new ArrayList<>();
		DDRegion.getItems().addAll("Europe", "Japan", "America");
		DDRole.getItems().addAll("Tank", "Healer", "DPS");
		DDDataCenter.getItems().addAll("Aether", "Crystal", "Dynamis", "Primal", "Chaos", "Light", "Elemental", "Gaia",
				"Mana", "Meteor");
		DDDataCenter.setOnAction(this::serverchoice);
		

		try {
			for (int i = 0; i < dao.allStatic().size(); i++) {
				Groupnames.add(dao.allStatic().get(i).getGroupname());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDStatic.getItems().addAll(Groupnames);
		DDStatic.setOnAction(arg01 -> {
			try {
				fillstaticlist(arg01);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		DDList.getItems().addAll(Groupnames);

	}
	/**Filtering out what Static show up for what region at the choicebox
	 * 
	 * 
	 * 
	 */
	public void DropDownStaticfilter(Event event) throws FileNotFoundException, IOException, SQLException {
		LogDaoInterface dao = new LogDaoImpl();
		List<RaidGroup> group = dao.allStatic();
		List<String> list = new ArrayList<>();
		String datacenter = (String) DDDataCenter.getValue();
		String region = "";
		if (datacenter.equalsIgnoreCase("Light") | datacenter.equalsIgnoreCase("Chaos")) {
			region = "Europe";
			for (int i = 0; i < group.size(); i++) {
				if (group.get(i).getContinent().equalsIgnoreCase(region)) {
					list.add(group.get(i).getGroupname());
				}
			}
		} else if (datacenter.equalsIgnoreCase("Aether") | datacenter.equalsIgnoreCase("Crystal")
				| datacenter.equalsIgnoreCase("Dynamis") | datacenter.equalsIgnoreCase("Primal")) {
			region = "America";
			for (int i = 0; i < group.size(); i++) {
				if (group.get(i).getContinent().equalsIgnoreCase(region)) {
					list.add(group.get(i).getGroupname());

				}
			}
		} else if (datacenter.equalsIgnoreCase("Elemental") | datacenter.equalsIgnoreCase("Gaia")
				| datacenter.equalsIgnoreCase("Mana") | datacenter.equalsIgnoreCase("Meteor")) {
			region = "Japan";
			for (int i = 0; i < group.size(); i++) {
				if (group.get(i).getContinent().equalsIgnoreCase(region)) {
					list.add(group.get(i).getGroupname());

				}
			}

		}
		DDStaticList.getItems().clear();
		DDStaticList.getItems().addAll(list);

	}
	/**Refreshing the Overview table
	 * 
	 */
	@FXML
	private void RefreshListClick(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
		PlayerServiceInterface service = new PSImplementation();
		List<Region> region = service.totalRegion();
		DBRegionList list = new DBRegionList(region);
		RegionView.setCellValueFactory(new PropertyValueFactory<Region, String>("region"));
		StaticView.setCellValueFactory(new PropertyValueFactory<Region, Integer>("groupnumbers"));
		PlayerView.setCellValueFactory(new PropertyValueFactory<Region, Integer>("playernumbers"));
		tbListAll.setItems(list.getRegion());

	}
	/**Method to display the Table of the chosen static members
	 * 
	 */
	public void fillstaticlist(Event event) throws FileNotFoundException, IOException, SQLException {

		PlayerServiceInterface service = new PSImplementation();
		List<RaidGroup> group = new ArrayList<>();
		String choice = (String) DDStatic.getValue();
		group.addAll(service.getStatic(choice));
		DBModelStaticList list = new DBModelStaticList(group);
		FirstNameStatic.setCellValueFactory(new PropertyValueFactory<Player, String>("Firstname"));
		SurNameStatic.setCellValueFactory(new PropertyValueFactory<Player, String>("Surname"));
		RoleStatic.setCellValueFactory(new PropertyValueFactory<Player, String>("Role"));
		DCStatic.setCellValueFactory(new PropertyValueFactory<Player, String>("Datacenter"));
		ServerStatic.setCellValueFactory(new PropertyValueFactory<Player, String>("Server"));
		StaticStatic.setCellValueFactory(new PropertyValueFactory<RaidGroup, String>("Groupname"));
		RegionStatic.setCellValueFactory(new PropertyValueFactory<RaidGroup, String>("Continent"));
		tbStaticList.setItems(list.getPlayers());
		tbStaticListaddon.setItems(list.getRaidgroup());

	}
	/**Switch cases to adjust the Server with the given Data Center
	 * 
	 */
	public void serverchoice(Event event) {
		String servervalue = (String) DDDataCenter.getValue();		
		switch (servervalue) {
		case "Aether":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Adamantoise", "Cactur", "Gaerie", "Gilgamesh", "Jenova", "Midgarsormr",
					"Sargatanas", "Siren");
			break;
		case "Crystal":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Balmung", "Brynhildr", "Coeurl", "Diabolos", "Goblin", "Malboro",
					"Mateus", "Zalera");
			break;
		case "Dynamis":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Halicarnassus", "Maduin", "Marilith", "Seraph");
			break;
		case "Primal":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Behemoth", "Excalibur", "Exodus", "Famfrit", "Hyperion", "Lamia",
					"Leviathan", "Ultros");
			break;
		case "Chaos":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Cerberus", "Louisoix", "Moogle", "Omega", "Phantom", "Ragnarok",
					"Sagittarius", "Spriggan");
			break;
		case "Light":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Alpha", "Lich", "Odin", "Phoenix", "Raiden", "Shiva", "Twintania",
					"Zodiark");
			break;
		case "Elemental":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Aegis", "Atomos", "Carbuncle", "Garuda", "Gungnir", "Kujata", "Tonberry",
					"Typhon");
			break;
		case "Gaia":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Alexander", "Bahamut", "Durandal", "Fenrir", "Ifrit", "Ridill", "Tiamat",
					"Ultima");
			break;
		case "Mana":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Anima", "Asura", "Chocobo", "Hades", "Ixion", "Masamune", "Pandaemonium",
					"Titan");
			break;
		case "Meteor":
			
			DDServerCreate.getItems().clear();
			DDServerCreate.getItems().addAll("Belias", "Mandragora", "Ramuh", "Shinryu", "Unicorn", "Valefor",
					"Yojimbo", "Zeromus");
			break;

		}
			DDServerCreate.setOnAction(arg01 -> {
			try {
				DropDownStaticfilter(arg01);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}
	/**Same thing as above but for the changing role function
	 * 
	 */
	public void changechoice(JComboBox<String> choicebox2, String selectedOption) {
		String[] updatedOptions = null;
		switch (selectedOption) {
		case "Aether":

			updatedOptions = new String[] { "Adamantoise", "Cactur", "Gaerie", "Gilgamesh", "Jenova", "Midgarsormr",
					"Sargatanas", "Siren" };
			break;
		case "Crystal":

			updatedOptions = new String[] { "Balmung", "Brynhildr", "Coeurl", "Diabolos", "Goblin", "Malboro", "Mateus",
					"Zalera" };
			break;
		case "Dynamis":

			updatedOptions = new String[] { "Halicarnassus", "Maduin", "Marilith", "Seraph" };
			break;
		case "Primal":

			updatedOptions = new String[] { "Behemoth", "Excalibur", "Exodus", "Famfrit", "Hyperion", "Lamia",
					"Leviathan", "Ultros" };
			break;
		case "Chaos":

			updatedOptions = new String[] { "Cerberus", "Louisoix", "Moogle", "Omega", "Phantom", "Ragnarok",
					"Sagittarius", "Spriggan" };
			break;
		case "Light":

			updatedOptions = new String[] { "Alpha", "Lich", "Odin", "Phoenix", "Raiden", "Shiva", "Twintania",
					"Zodiark" };
			break;
		case "Elemental":

			updatedOptions = new String[] { "Aegis", "Atomos", "Carbuncle", "Garuda", "Gungnir", "Kujata", "Tonberry",
					"Typhon" };
			break;
		case "Gaia":

			updatedOptions = new String[] { "Alexander", "Bahamut", "Durandal", "Fenrir", "Ifrit", "Ridill", "Tiamat",
					"Ultima" };
			break;
		case "Mana":

			updatedOptions = new String[] { "Anima", "Asura", "Chocobo", "Hades", "Ixion", "Masamune", "Pandaemonium",
					"Titan" };
			break;
		case "Meteor":

			updatedOptions = new String[] { "Belias", "Mandragora", "Ramuh", "Shinryu", "Unicorn", "Valefor", "Yojimbo",
					"Zeromus" };
			break;

		}
		choicebox2.setModel(new DefaultComboBoxModel<>(updatedOptions));

	}
	/** Add functionality for sorting the static table
	 * 
	 */
	@FXML
	private void StaticTable(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
		

		LogDaoInterface dao = new LogDaoImpl();
		String tablelist = (String) DDList.getValue();
		dao.getStatic(tablelist);
		ObservableList<Player> list = FXCollections.observableArrayList();

		
	}

}
