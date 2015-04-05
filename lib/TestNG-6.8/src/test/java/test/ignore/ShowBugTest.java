package test.ignore;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;

public class ShowBugTest {
  @Test
  public void myTestMethod(ITestContext ctx) {
    Map<String, String> allParameters = ctx.getCurrentXmlTest().getLocalParameters();
    Assert.assertFalse(allParameters.isEmpty());
    Iterator<String> iterator = allParameters.keySet().iterator();
    while (iterator.hasNext()) {
      String key = iterator.next();
      String value = allParameters.get(key);
      System.out.println(key + "," + value);
    }
  }
}