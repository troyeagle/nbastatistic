package test.ignore;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParallelTestClassA {

  @BeforeClass
  public void setup() throws InterruptedException {
    System.out.println("" + System.currentTimeMillis()
        + " - testA setup starting (thread id = "
        + Thread.currentThread().getId() + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testA setup finished (thread id = "
        + Thread.currentThread().getId() + ")");
  }

  @Test
  public void testA() throws InterruptedException {
    System.out
        .println("" + System.currentTimeMillis()
            + " - testA start (thread id = " + Thread.currentThread().getId()
            + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testA finish (thread id = " + Thread.currentThread().getId()
        + ")");
  }

  @AfterClass
  public void tearDown() throws InterruptedException {
    System.out.println("" + System.currentTimeMillis()
        + " - testA tearDown starting (thread id = "
        + Thread.currentThread().getId() + ")");
    Thread.sleep(1000);
    System.out.println("" + System.currentTimeMillis()
        + " - testA tearDown finished (thread id = "
        + Thread.currentThread().getId() + ")");
  }
}