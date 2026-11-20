package Waypoints;

public enum Waypoints {
	WAYPOINTS("UK_waypoints.csv"),
	WAYPOINTS100("UK_waypoints100.csv"),
	WAYPOINTS_OSG("wainwrights_OSGrid.csv");

	private final String folderPath = "data/";
	private final String fileName;

	private Waypoints(String file) {
		this.fileName = file;
	}

	public String getFilePath() {
		return folderPath + fileName;
	}

	public static Waypoints selectWaypointFile(String input) {
		return switch(input) {
			case "100" -> Waypoints.WAYPOINTS100;
			case "OSG" -> Waypoints.WAYPOINTS_OSG;
			default -> Waypoints.WAYPOINTS;
		};
	}

}
