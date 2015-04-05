package test.ignore.lindsey;

import org.testng.annotations.BeforeMethod;


public abstract class Foo extends TestBase {

    
    @BeforeMethod
    @Override
    public void before(){
        System.out.println("Foo.before() from " + this.getClass().getSimpleName());
        super.before();
        throw new RuntimeException();
    }
}
