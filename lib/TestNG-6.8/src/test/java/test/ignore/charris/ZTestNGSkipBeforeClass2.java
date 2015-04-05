package test.ignore.charris;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups={"ZTestNGSkip"})
public class ZTestNGSkipBeforeClass2 extends ZTestNGSkipBeforeClassBase{
//public class ZTestNGSkipBeforeClass2{
    
    @BeforeClass(alwaysRun=true)
    public void checkIfRun(){
//        throw new SkipException("Skipping in "+this.getClass().getName());
    }
    
    @Test(dataProvider="fake",
            description = "Fails half the TCs, based on data from provider",
            enabled=true)
    public void testFailDisplayFromDataProvider(Integer cnt){
        Integer failVal = 9999;
        System.out.println(Thread.currentThread().getName() + " "+ cnt);
        if (cnt % 2 == 0){
            Assert.assertEquals(cnt, failVal, "Failing even numbers");
            Assert.fail("Failing even numbers");
        }
    }
    
    @DataProvider(name="fake", parallel = true)
    public static Object[][] dataProvider(){
        Integer[] arg = {1,2,3,4,5,6,7,8,9,10};
        Object[][] qss = new Object[arg.length][1];
        int cnt = 0;
        for(int i = 0; i < arg.length; i++){
            Object[] args = new Object[1];
            args[0] = arg[i];
            qss[cnt] = args;
            cnt++;
        }
        return qss;
    }
    
    @AfterClass (alwaysRun = true)
    public void runAfterClass(){
        System.out.println("Afterclass");
    }
}
