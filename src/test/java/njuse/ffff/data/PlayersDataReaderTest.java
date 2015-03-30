package njuse.ffff.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PlayersDataReaderTest {

	@Test
	public void test() throws ClassNotFoundException, IOException {
		PlayersDataProcessor player = new PlayersDataProcessor();
		player.readAndAnalysisPlayer();
		assertEquals(448,PlayersDataProcessor.players.size());
		player.loadSerial();
		assertEquals(448,PlayersDataProcessor.players.size());
	}

}
