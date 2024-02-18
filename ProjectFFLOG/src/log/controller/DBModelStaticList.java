package log.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import log.model.America;
import log.model.Europe;
import log.model.Japan;
import log.model.Player;
import log.model.RaidGroup;
import log.model.Region;
import log.service.PSImplementation;
import log.service.PlayerServiceInterface;

/**
 * @Dave
 * List the static members
 * get them out of DB 
 * 
 */
public class DBModelStaticList {

	
	private ObservableList<Player> players;
	private ObservableList<RaidGroup> raidgroup;

	public ObservableList<RaidGroup> getRaidgroup() {
		return raidgroup;
	}

	public void setRaidgroup(ObservableList<RaidGroup> raidgroup) {
		this.raidgroup = raidgroup;
	}
	/**Method to call it into the Table
	 * 
	 * 
	 */
	public DBModelStaticList(List<RaidGroup> group) {

		players = FXCollections.observableArrayList();
		raidgroup = FXCollections.observableArrayList();

		if (group.get(0).getContinent().equalsIgnoreCase("Europe")) {

			for (int i = 0; i < group.get(0).getMembers().size(); i++) {
				players.add(new Europe(group.get(0).getMembers().get(i).getFirstname(),
						group.get(0).getMembers().get(i).getSurname(), group.get(0).getMembers().get(i).getRole(),
						group.get(0).getMembers().get(i).getDatacenter(),
						group.get(0).getMembers().get(i).getServer()));
				raidgroup.add(new RaidGroup(group.get(0).getGroupname(), group.get(0).getAmount(),
						group.get(0).getContinent()));

			}
		} else if (group.get(0).getContinent().equalsIgnoreCase("America")) {

			for (int i = 0; i < group.get(0).getMembers().size(); i++) {
				players.add(new America(group.get(0).getMembers().get(i).getFirstname(),
						group.get(0).getMembers().get(i).getSurname(), group.get(0).getMembers().get(i).getRole(),
						group.get(0).getMembers().get(i).getDatacenter(),
						group.get(0).getMembers().get(i).getServer()));
				raidgroup.add(new RaidGroup(group.get(0).getGroupname(), group.get(0).getAmount(),
						group.get(0).getContinent()));

			}
		} else if (group.get(0).getContinent().equalsIgnoreCase("Japan")) {

			for (int i = 0; i < group.get(0).getMembers().size(); i++) {
				players.add(new Japan(group.get(0).getMembers().get(i).getFirstname(),
						group.get(0).getMembers().get(i).getSurname(), group.get(0).getMembers().get(i).getRole(),
						group.get(0).getMembers().get(i).getDatacenter(),
						group.get(0).getMembers().get(i).getServer()));
				raidgroup.add(new RaidGroup(group.get(0).getGroupname(), group.get(0).getAmount(),
						group.get(0).getContinent()));

			}
		}

	}

	public ObservableList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ObservableList<Player> players) {
		this.players = players;
	}

}
