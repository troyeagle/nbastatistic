package test.ignore;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParallelTestClassB {

  @BeforeClass
  public void setup() throws InterruptedException {
    System.out.println("" + System.currentTimeMillis()
        + " - testB setup starting (thread id = "
        + Thread.currentThread().getId() + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testB setup finished (thread id = "
        + Thread.currentThread().getId() + ")");
  }

  @Test
  public void testB() throws InterruptedException {
    System.out
        .println("" + System.currentTimeMillis()
            + " - testB start (thread id = " + Thread.currentThread().getId()
            + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testB finish (thread id = " + Thread.currentThread().getId()
        + ")");
  }

  @AfterClass
  public void tearDown() throws InterruptedException {
    System.out.println("" + System.currentTimeMillis()
        + " - testB tearDown starting (thread id = "
        + Thread.currentThread().getId() + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testB tearDown finished (thread id = "
        + Thread.currentThread().getId() + ")");
  }
}