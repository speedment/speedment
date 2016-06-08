package com.speedment.common.injector.test_c;

import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.test_b.B;

/**
 *
 * @author Emil Forslund
 */
public class ChildType extends ParentType {
    
    public @Inject B b;
    
    @Execute
    private void installChild() {}
}