package test.ignore.lindsey;

import org.testng.annotations.Test;

public class Bar2 extends Foo {
    
    @Test
    public void exec2() {
        System.out.println(this.getClass().getSimpleName());
    }
}
