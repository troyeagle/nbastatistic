package test.ignore;

import org.testng.annotations.Factory;

public class TestFactory {

  @Factory
  public Object[] factory() {
    return new Object[] { new AIgnore(), new B() };
  }
}
