package test.ignore;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Auto {
  public static void main(final String args[]) {
       TestNG testng = new TestNG();
       String[] users = { "abc", "xyz" };
       XmlSuite suites = new XmlSuite();
       List<XmlTest> tests = new ArrayList<XmlTest>();
       List<XmlClass> XmlClasses = new ArrayList<XmlClass>();
       String[] classes = { TestLearn.class.getName() };
       for (String cla : classes) {
           XmlClasses.add(new XmlClass(cla));
       }
       for (String user : users) {
           XmlTest test = new XmlTest();
           test.setSuite(suites);
           test.setName("Test using " + user);
           test.addParameter("username", user);
           test.setClasses(XmlClasses);
           tests.add(test);
       }
       for (XmlTest test : tests) {
           suites.addTest(test);
       }
       suites.setParallel(suites.PARALLEL_TESTS);
       testng.setParallel("tests");
       testng.setXmlSuites(Arrays.asList(new XmlSuite[] { suites }));
       testng.run();
   }
}

