package test.ignore.lindsey;

import org.testng.annotations.Test;

public class Bar3 extends Foo {
    
    @Test
    public void exec3() {
        System.out.println("BAR 3 TEST " + this.getClass().getSimpleName());
    }
}
