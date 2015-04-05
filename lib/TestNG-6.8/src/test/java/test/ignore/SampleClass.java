package test.ignore;

import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class SampleClass {
  
  public static void main(String[] args) {

    // Adding the methods to run to XmlClasses
    XmlClass clazz = new XmlClass(Bar.class);
    List<XmlClass> classes = new ArrayList<XmlClass>();
    classes.add(clazz);

    // Adding the list of classes to a test

    List<XmlSuite> suites = new ArrayList<XmlSuite>();
    XmlSuite suite = new XmlSuite();
    List<XmlTest> tests = new ArrayList<XmlTest>();
    XmlTest test = new XmlTest();

    test.setXmlClasses(classes);
    test.setSuite(suite);
    test.setName("Mahadevan");
    tests.add(test);
    suite.setTests(tests);
    suite.setName("Krishnan");
//    suite.setParallel(XmlSuite.PARALLEL_METHODS);
//    suite.setThreadCount(20);
    suite.setDataProviderThreadCount(20);
    suites.add(suite);
    suite.setTests(tests);
    TestNG testng = new TestNG(false);
    testng.setXmlSuites(suites);
    //testng.setVerbose(10);
    testng.run();
  }

  public static class Bar {

//    @Test(groups = "runMe")
//    public void foo() {
//      System.out.print("\nI am foo() " + Thread.currentThread().getId());
//    }
//
//    @Test
//    public void bartest() {
//      System.out.println("\nI am bar() " + Thread.currentThread().getId());
//    }
    
    @Test(dataProvider="dp")
    public void fooBar(String name){
      System.out.println("\nfoobar() says " + name + " using Thread id : " + Thread.currentThread().getId());
    }
    
    @DataProvider(name="dp", parallel=true)
    public Object[][] fetchData(){
      return new Object[][] {{"Apple"},{"Android"}};
    }
  }
}
