package com.speedment.common.injector.test_c;

import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.test_b.A;

/**
 *
 * @author Emil Forslund
 */
public abstract class ParentType {
    
    public @Inject A a;
    
    @Execute
    private void installParent() {}
}
