package WaypointUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPSWaypoint extends Waypoint {

	/**
	 * holds the variable extra associated data for the waypoint
	 */
	private final List<String> data;

	/**
	 * Waypoints are immutable, all fields are final and must be defined within the constructor<br>
	 *
	 *
	 * @param name      the English name associated with the waypoint
	 * @param code      the compact code associated with the waypoint
	 * @param longitude x position as longitude
	 * @param latitude  y position as latitude
	 * @param elevation arbitrary height
	 * @param data      additional data
	 */
	private GPSWaypoint(String name, String code, String longitude, String latitude, String elevation, List<String> data) {
		super(name, code, longitude, latitude, elevation);
		this.data = data;
	}

	/**
	 * Parses an entire file of waypoints using the header file<br>
	 * All extra/misc data is thrown into the "description" of the waypoint
	 *
	 * @param file the file containing waypoints and a header
	 * @return a list of GPSWaypoints parsed from the file
	 */
	public static List<Waypoint> parseGPSWaypoints(String[] file) {
		final List<Waypoint> output = new ArrayList<>();

		final Map<Integer, String> headerMap = mapHeader(file[0]);

		for(int i = 1; i < file.length; i++) {
			final Waypoint waypoint = parseLine(file[i], headerMap);

			if(waypoint == null)
				continue;

			output.add(waypoint);
		}

		return output;
	}

	/**
	 * This function is overloaded here to split the line by its commas
	 *
	 * @param line the line containing the waypoint data
	 * @param headerMap the header map that matches the data points to the GPSWaypoint fields
	 * @return a GPSWaypoint parsed from the line
	 */
	private static Waypoint parseLine(String line, Map<Integer, String> headerMap) {
		return parseLine(line.split(","), headerMap);
	}

	/**
	 * Matches the data on the line to waypoint fields using the header map<br>
	 * Throws any misc data into the "description" list field
	 *
	 * @param line the line containing the waypoint data
	 * @param headerMap the header map that matches the data points to the GPSWaypoint fields
	 * @return a GPSWaypoint parsed from the line
	 */
	private static Waypoint parseLine(String[] line, Map<Integer, String> headerMap) {
		String name = "";
		String code = "";
		String latitude = "";
		String longitude = "";
		String elevation = "";
		final List<String> data = new ArrayList<>();

		for(int i = 0; i < line.length; i++) {

			if(!headerMap.containsKey(i)) {
				data.add(line[i]);
				continue;
			}
			switch(headerMap.get(i)) {
				case "name" -> name = line[i];
				case "code" -> code = line[i];
				case "latitude" -> latitude = line[i];
				case "longitude" -> longitude = line[i];
				case "elevation" -> elevation = line[i];
				default -> data.add(line[i]);
			}
		}

		return new GPSWaypoint(name, code, longitude, latitude, elevation, data);
	}

	/**
	 * This function is overloaded to split the header line into a String[]
	 *
	 * @param header the header line of the waypoint file
	 * @return a map associating the data points with respective waypoint fields
	 */
	private static Map<Integer, String> mapHeader(String header) {
		return mapHeader(header.split(","));
	}

	/**
	 * Checks the first three letters of each item in the header<br>
	 * Matches required fields for a waypoint<br>
	 * Throws misc/unknown values into the description
	 *
	 * @param header the header line of the waypoint file
	 * @return a map associating the data points with respective waypoint fields
	 */
	private static Map<Integer, String> mapHeader(String[] header) {
		final Map<Integer, String> headerMap = new HashMap<>();

		for(int i = 0; i < header.length; i++) {
			switch(header[i].substring(0, 3)){
				case "nam" -> headerMap.put(i, "name");
				case "cod" -> headerMap.put(i, "code");
				case "lon" -> headerMap.put(i, "longitude");
				case "lat" -> headerMap.put(i, "latitude");
				case "ele" -> headerMap.put(i, "elevation");
				case "des" -> headerMap.put(i, "description");
			}
		}

		return headerMap;
	}

	@Override
	public double getX() {
		return -1;
	}

	@Override
	public double getY() {
		return -1;
	}

	@Override
	public double getHeightInMeters() {
		return getHeightInMeters(this.height);
	}

	/**
	 * Converts the height field from a waypoint file into a value for mathematics or display
	 *
	 * @param height the height field from the waypoint file
	 * @return the height in meters
	 */
	public static double getHeightInMeters(String height) {
		final boolean isMetre = isInMetres(height);
		final int unitLength = isMetre ? 1 : 2;
		final String heightString = height.substring(0, height.length() - unitLength);

		final double heightDouble = Double.parseDouble(heightString);

		//constant for ft to m, taken from SampleCode/HelperLibrary.java
		return isMetre ? heightDouble : (int) (heightDouble * 0.3048);
	}

	/**
	 * Checks if a height is given in meters or feet
	 *
	 * @param height the height field from the waypoint file
	 * @return true if the height passed in is in meters
	 */
	public static boolean isInMetres(String height) {
		if(height.isEmpty())
			return false;
		return height.substring(height.length() - 1).equalsIgnoreCase("m");
	}

	@Override
	public String[] getAdditionalData() {
		return data.toArray(new String[0]);
	}
}
