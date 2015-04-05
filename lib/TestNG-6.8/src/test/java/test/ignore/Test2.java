package test.ignore;

import org.testng.annotations.Test;

public class Test2 extends Test1
{
  @Override
  @Test(groups = "group1")
  public void testMethod1()
  {}

  @Override
  @Test(groups = "group2")
  public void testMethod2()
  {}
}