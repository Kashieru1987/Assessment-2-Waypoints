package WaypointUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPSWaypoint extends Waypoint {

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

	private static Waypoint parseLine(String line, Map<Integer, String> headerMap) {
		return parseLine(line.split(","), headerMap);
	}

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

	private static Map<Integer, String> mapHeader(String header) {
		return mapHeader(header.split(","));
	}

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

	public double getHeightInMeters(String height) {
		final boolean isMetre = isInMetres(height);
		final int unitLength = isMetre ? 1 : 2;
		final String heightString = height.substring(0, height.length() - unitLength);

		final double heightDouble = Double.parseDouble(heightString);

		//constant for ft to m, taken from SampleCode/HelperLibrary.java
		return isMetre ? heightDouble : (int) (heightDouble * 0.3048);
	}

	public static boolean isInMetres(String height) {
		return height.substring(height.length() - 1).equalsIgnoreCase("m");
	}

	@Override
	public String[] getAdditionalData() {
		return data.toArray(new String[0]);
	}
}
