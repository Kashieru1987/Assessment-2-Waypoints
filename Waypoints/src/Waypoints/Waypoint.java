package Waypoints;

/**
 * Represents a waypoint with common data, supports additional data<br>
 * Subclasses of Waypoints.Waypoint are not designed to be compatible with each other for calculations
 */
public abstract class Waypoint {

	private final String name;
	private final String code;
	private final String posX; // arbitrary
	private final String posY; //arbitrary
	private final double height; // arbitrary

	/**
	 * Waypoints.Waypoints are immutable, all fields are final and must be defined within the constructor
	 * @param name the English name associated with the waypoint
	 * @param code the compact code associated with the waypoint
	 * @param posX arbitrary x position
	 * @param posY arbitrary y position
	 * @param height arbitrary height
	 */
	protected Waypoint(String name, String code, String posX, String posY, double height) {
		this.name = name;
		this.code = code;
		this.posX = posX;
		this.posY = posY;
		this.height = height;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getPosX() {
		return this.posX;
	}

	public String getPosY() {
		return this.posY;
	}

	/**
	 * - required due to "x" definition being abstract<br>
	 * - does not convert "x" into a standard measurement format
	 * @return "x" as a numeric value for calculations
	 */
	public abstract double getX(); // accounts for arbitrary x definition

	/**
	 * - required due to "y" definition being abstract<br>
	 * - does not convert "y" into a standard measurement format
	 * @return "y" as a numeric value for calculations
	 */
	public abstract double getY(); // accounts for arbitrary y definition

	/**
	 * as per specification, height must be obtainable in meters<br>
	 * this is enforced through this function, it is up to subclasses to handle the calculation
	 * @return height in meters
	 */
	public abstract double getHeightInMeters(); // accounts for arbitrary height definition

	/**
	 * support for additional data
	 * @return all additional data regarding the waypoint
	 */
	public abstract String[] getAdditionalData();

	/**
	 * intended for quick and easy output
	 * @return all additional data as a single comma-separated string
	 */
	public String getAdditionalDataString() {
		final String[] data = getAdditionalData();

		if(data == null)
			return "";
		if(data.length == 0)
			return "";

		final StringBuilder output = new StringBuilder(data[0]);
		for(int i = 1; i < data.length; i++)
			output.append(",").append(data[i]);

		return output.toString();
	}

	@Override
	public String toString() {
		return "{"+getName()
				+","+getCode()
				+",pos["+getPosX()+","+getPosY()+"]"
				+",h:"+getHeightInMeters()+"m"
				+", "+getAdditionalDataString()+"}";
	}

}