package njuse.ffff.data;

import java.io.IOException;

import org.testng.annotations.Test;

import static org.junit.Assert.*;
public class MatchDataReaderTest {
  @Test
  public void test() {
	  DataReadController dr = new DataReadController();
	  try {
		dr.initialize();
	} catch (IOException e) {
		e.printStackTrace();
	}
	  assertNotEquals(null,MatchDataProcessor.matches);
  }
}
