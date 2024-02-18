package log.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import log.model.Region;

/**
 * @Dave
 * Overview Table showing the amount of Players and Statics
 * 
 * 
 */
public class DBRegionList {
	private ObservableList<Region> region;	

	public DBRegionList(List<Region> group) {
		region = FXCollections.observableArrayList();
		for (int i = 0; i < group.size(); i++) {
			region.add(new Region(group.get(i).getRegion(), group.get(i).getGroupnumbers(),
					group.get(i).getPlayernumbers()));

		}

	}
	public ObservableList<Region> getRegion() {
		return region;
	}


	public void setRegion(ObservableList<Region> region) {
		this.region = region;
	}

}
