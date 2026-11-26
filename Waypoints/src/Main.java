import WaypointUtil.GPSWaypoint;
import WaypointUtil.Waypoint;
import WaypointUtil.Waypoints;

import java.util.Scanner;

public class Main {

	private final Scanner scanner;
	private final Waypoints waypointFile;

	public Main(String[] args) {
		this.scanner = new Scanner(System.in);
		this.waypointFile = selectWaypointFile(args);

		final String[] waypointArray = FileUtil.getFileAsArray(waypointFile.getFilePath()); // yay, condition fulfilled

		for(Waypoint waypoint : GPSWaypoint.parseGPSWaypoints(waypointArray)) {
			System.out.println(waypoint);
		}
	}

	public Waypoints selectWaypointFile(String[] args) {
		if(args.length != 0)
			return Waypoints.selectWaypointFile(args[0]);

		System.out.print("Enter waypoint file [Full(default)/100/OSG]: ");
		final String userInput = scanner.nextLine();

		return Waypoints.selectWaypointFile(userInput);
	}

	public static void main(String[] args) {
		new Main(args);
	}
}