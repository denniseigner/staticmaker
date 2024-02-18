package log.model;

public class Region {
	private String region;
	private int groupnumbers;
	private int playernumbers;
	public Region(String region, int groupnumbers, int playernumbers) {
		super();
		this.region = region;
		this.groupnumbers = groupnumbers;
		this.playernumbers = playernumbers;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getGroupnumbers() {
		return groupnumbers;
	}
	public void setGroupnumbers(int groupnumbers) {
		this.groupnumbers = groupnumbers;
	}
	public int getPlayernumbers() {
		return playernumbers;
	}
	public void setPlayernumbers(int playernumbers) {
		this.playernumbers = playernumbers;
	}
	@Override
	public String toString() {
		return "Region [region=" + region + ", groupnumbers=" + groupnumbers + ", playernumbers=" + playernumbers + "]";
	}

}

