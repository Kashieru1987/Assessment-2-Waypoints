package WaypointUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSWaypointTest {


	@Test
	@DisplayName("Standard example (20ft -> 6(.096)m")
	void getHeightInMeters1() { // 20 feet is 6.096 meters
		assertEquals(6, GPSWaypoint.getHeightInMeters("20ft"));
	}

	@Test
	@DisplayName("Always round down (15ft -> 4(.572)m")
	void getHeightInMeters2() { // 15 feet is 4.572 meters (round down)
		assertEquals(4, GPSWaypoint.getHeightInMeters("15ft"));
	}

	@Test
	@DisplayName("larger values")
	void getHeightInMeters3() { // 5000 feet is 1524 meters
		assertEquals(1524, GPSWaypoint.getHeightInMeters("5000ft"));
	}

	@Test
	@DisplayName("Example (20m)")
	void isInMetres1() {
		assertTrue(GPSWaypoint.isInMetres("20m"));
	}

	@Test
	@DisplayName("False example (15ft)")
	void isInMetres2() {
		assertFalse(GPSWaypoint.isInMetres("15ft"));
	}

	@Test
	@DisplayName("Missing units")
	void isInMetres3() {
		assertFalse(GPSWaypoint.isInMetres("20"));
	}

	@Test
	@DisplayName("With decimal place")
	void isInMetres4() {
		assertTrue(GPSWaypoint.isInMetres("1.0m"));
	}

	@Test
	@DisplayName("Zero meters")
	void isInMetres5() {
		assertTrue(GPSWaypoint.isInMetres("0m"));
	}

	@Test
	@DisplayName("Negative meters")
	void isInMetres6() {
		assertTrue(GPSWaypoint.isInMetres("-10m"));
	}

	@Test
	@DisplayName("Arbitrary units")
	void isInMetres7() {
		assertFalse(GPSWaypoint.isInMetres("12aaa"));
	}

	@Test
	@DisplayName("Empty Input String")
	void isInMetres8() {
		assertFalse(GPSWaypoint.isInMetres(""));
	}

}