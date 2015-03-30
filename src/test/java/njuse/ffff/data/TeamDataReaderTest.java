package njuse.ffff.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TeamDataReaderTest {

	@Test
	public void test() throws IOException {
		TeamDataProcessor team = new TeamDataProcessor();
		team.readAndAnalysisTeam();
		assertEquals(30,TeamDataProcessor.teams.size());
	}

}
