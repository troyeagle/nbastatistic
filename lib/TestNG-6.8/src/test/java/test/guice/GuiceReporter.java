package test.guice;

import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class GuiceReporter implements IReporter {

  public GuiceReporter() {
    System.out.println("Constructor");
  }

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
      String outputDirectory) {
    System.out.println("Generating report");
  }

}
