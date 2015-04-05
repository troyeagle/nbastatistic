package test.ignore;

import org.testng.annotations.Test;

import junit.framework.TestCase;

public class Test1 extends TestCase
{
  @Test(groups = "group1")
  public void testMethod1()
  {}

  @Test(groups = "group2")
  public void testMethod2()
  {}
}