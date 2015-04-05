package test.ignore;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TemplateTest {
  Long m_number;
  Long m_parentNumber;

  @BeforeClass(alwaysRun = true)
  @Parameters({ "parentNumber" })
  public void setUpSuite(@Optional("20") String parentNumber) throws Exception {
    m_parentNumber = Long.parseLong(parentNumber);
    ;
  }

  @BeforeTest
  @Parameters({ "number" })
  public void setUp(@Optional("1") String number) throws Exception {
    m_number = Long.parseLong(number);
    System.out.println("Number is: " + m_number);
    System.out.println("parentNumber is: " + m_parentNumber);
  }

  @Test
  public void callEvaluate() {
    org.testng.Assert.assertTrue(m_number + m_parentNumber > 5);
  }
}