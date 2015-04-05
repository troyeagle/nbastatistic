package test.ignore.lindsey;

import org.testng.annotations.Test;

public class Bar1 extends Foo {
    
    @Test
    public void exec1() {
        System.out.println(this.getClass().getSimpleName());
    }
}
