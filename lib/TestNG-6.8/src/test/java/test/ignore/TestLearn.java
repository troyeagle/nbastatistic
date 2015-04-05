package test.ignore;

import org.testng.annotations.Test;

public class TestLearn {

  @Test
  public void test() {
    System.out.println(Thread.currentThread().getName());
  }

}