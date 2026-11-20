import Waypoints.Waypoints;

import java.io.File;
import java.util.Scanner;

public class Main {

	private final Scanner scanner;
	private Waypoints waypointFile;

	public Main() {
		scanner = new Scanner(System.in);

		System.out.print("Enter waypoint file [Full(default)/100/OSG]: ");
		final String userInput = scanner.nextLine();

		waypointFile = Waypoints.selectWaypointFile(userInput);;
		final File file = new File(waypointFile.getFilePath());

	}

	public static void main(String[] args) {
		new Main();
	}
}