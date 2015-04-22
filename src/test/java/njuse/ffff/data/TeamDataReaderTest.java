package njuse.ffff.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.testng.annotations.Test;

public class TeamDataReaderTest {

	@Test
	public void test() throws IOException {
		DataReadController dr = new DataReadController();
		  try {
			dr.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
