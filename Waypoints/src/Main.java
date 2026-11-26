import WaypointUtil.GPSWaypoint;
import WaypointUtil.Waypoint;
import WaypointUtil.Waypoints;

import java.util.Scanner;

public class Main {

	private final Scanner scanner;
	private final Waypoints waypointFile;

	public Main() {
		scanner = new Scanner(System.in);

		System.out.print("Enter waypoint file [Full(default)/100/OSG]: ");
		final String userInput = scanner.nextLine();

		waypointFile = Waypoints.selectWaypointFile(userInput);;
		final String[] waypointArray = FileUtil.getFileAsArray(waypointFile.getFilePath()); // yay, condition fulfilled

		for(Waypoint waypoint : GPSWaypoint.parseGPSWaypoints(waypointArray)) {
			System.out.println(waypoint);
		}

	}

	public static void main(String[] args) {
		new Main();
	}
}