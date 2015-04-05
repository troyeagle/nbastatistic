package test.ignore.charris;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class ZTestNGSkipBeforeClassBase{
    
    @BeforeSuite(alwaysRun=true)
    public void bs(){
        System.out.println("BeforeSuite in "+this.getClass().getName());
    }
//    @AfterSuite(alwaysRun=true)
//    public void as(){
//        log.info("AfterSuite in "+this.getClass().getName());
//    }
    @BeforeClass(alwaysRun=true)
    public void bc(){
      System.out.println("@BeforeClass in "+this.getClass().getName());
    }
//    @AfterClass(alwaysRun=true)
//    public void ac(){
//        log.info("@AfterClass in "+this.getClass().getName());
//    }
    
    public ZTestNGSkipBeforeClassBase() {
        // configure Logger
//        BasicConfigurator.configure();
//        PropertyConfigurator.configure("environments/log4j.properties");
    }
}
