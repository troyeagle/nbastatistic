package test.ignore.lindsey;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase {

    private int ticker = 0;
    
    @AfterMethod(alwaysRun = true)
    public void after() {
        System.out.println("TestBase.after() from " + this.getClass().getSimpleName());
        
        if(  ticker < 1 ) {
            System.out.println("The 'after()' method is being run even though the 'before()' method was never run from " + this.getClass().getSimpleName());
        }
    }
    
    @BeforeMethod
    public void before() {
        System.out.println("TestBase.before from " + this.getClass().getSimpleName());
        ticker++;
    }
}
