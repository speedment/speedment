package com.speedment.runtime.join;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.join.internal.component.join.JoinComponentImpl;
import com.speedment.runtime.join.internal.component.stream.SqlJoinStreamSupplierComponent;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class JoinBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            JoinComponentImpl.class,
            SqlJoinStreamSupplierComponent.class
        );
    }

}
