package com.speedment.runtime.join;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import com.speedment.runtime.join.trait.HasCreateJoin3;
import com.speedment.runtime.join.trait.HasCreateJoin4;
import com.speedment.runtime.join.trait.HasCreateJoin5;
import com.speedment.runtime.join.trait.HasCreateJoin6;

/**
 * JoinStreamSupplierComponent that can be used to create Join objects
 *
 * @author Per Minborg
 */
@InjectKey(JoinStreamSupplierComponent.class)
public interface JoinStreamSupplierComponent
    extends
    HasCreateJoin2,
    HasCreateJoin3,
    HasCreateJoin4,
    HasCreateJoin5,
    HasCreateJoin6 {}
