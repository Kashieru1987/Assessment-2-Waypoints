import Waypoints.Waypoints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

	@Test
	@DisplayName("UK_waypoints lines")
	void countLinesFull() {
		assertEquals(2572, FileUtil.countLines(Waypoints.WAYPOINTS.getFilePath()));
	}

	@Test
	@DisplayName("UK_waypoints100 lines")
	void countLines100() {
		assertEquals(101, FileUtil.countLines(Waypoints.WAYPOINTS100.getFilePath()));
	}

	@Test
	@DisplayName("wainwrights_OSGrid lines")
	void countLinesOSG() {
		assertEquals(215, FileUtil.countLines(Waypoints.WAYPOINTS_OSG.getFilePath()));
	}
}