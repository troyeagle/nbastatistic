package test.ignore;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.asserts.LoggingAssert;

public class TestNgTest {

  private String instanceName;

  public TestNgTest(String instanceName) {
    this.instanceName = instanceName;
  }

  @Test
  public void test1() {
    System.out.println("test1 says " + this.instanceName);
    this.instanceName = this.instanceName + "test1";
    LoggingAssert f = new LoggingAssert();

    f.assertEquals(true, true);

    LoggingAssert s = new LoggingAssert();
    s.assertEquals(true, true);

  }

  @Test(dependsOnMethods = { "test1" })
  public void test2() {
    System.out.println("test2 says " + this.instanceName);
    this.instanceName = this.instanceName + "test2";
  }

  @Test(dependsOnMethods = { "test2" })
  public void test3() {
    System.out.println("test3 says " + this.instanceName);
  }

  // The factory will get the values using which a test class has to be
  // initialized and then
  // provides testng with instances of them, so that testng can invoke them.
  @Factory(dataProvider = "dataFeeder")
  public static Object[] getInstance(String instanceName) {
    return new Object[] { new TestNgTest(instanceName) };
  }

  // This will read values from an external data source and pass it to the
  // factory.
  @DataProvider(name = "dataFeeder")
  public Object[][] dataFeeder() {
    return new Object[][] { { "Krishnan" }, { "Mahadevan" } };

  }

}