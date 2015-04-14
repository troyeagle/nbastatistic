package njuse.ffff.data;

import org.testng.annotations.Test;
import static org.junit.Assert.*;
public class MatchDataReaderTest {
  @Test
  public void test() {
	  MatchDataProcessor md = new MatchDataProcessor();
	  md.readAndAnalysisMatch();
	  assertNotEquals(null,MatchDataProcessor.matches);
  }
}
