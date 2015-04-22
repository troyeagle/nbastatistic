package njuse.ffff.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.testng.annotations.Test;

public class PlayersDataReaderTest {

	@Test
	public void test() throws ClassNotFoundException, IOException {
		  DataReadController dr = new DataReadController();
		  try {
			dr.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  assertNotEquals(null,PlayersDataProcessor.players);
	}

}
