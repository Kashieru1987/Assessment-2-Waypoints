import WaypointUtil.Waypoints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaypointsTest {

	@Test
	@DisplayName("Select Default File (empty input)")
	void selectWaypointFileEmpty() {
		assertEquals(Waypoints.WAYPOINTS, Waypoints.selectWaypointFile(""));
	}

	@Test
	@DisplayName("Select Default File (random input)")
	void selectWaypointFileRandom() {
		assertEquals(Waypoints.WAYPOINTS, Waypoints.selectWaypointFile("AAABBBCCC"));
	}

	@Test
	@DisplayName("Select Default File (100 input)")
	void selectWaypointFile100() {
		assertEquals(Waypoints.WAYPOINTS100, Waypoints.selectWaypointFile("100"));
	}

	@Test
	@DisplayName("Select Default File (OSG input)")
	void selectWaypointFileOSG() {
		assertEquals(Waypoints.WAYPOINTS_OSG, Waypoints.selectWaypointFile("OSG"));
	}

}