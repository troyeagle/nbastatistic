package test.ignore;

import com.google.inject.Inject;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice
public class AIgnore {

  @Inject
  private B b;

  @Test
  public void f() {
    System.out.println("A.f()");
    Assert.assertEquals("Daniel", "Dariel");
  }
}
