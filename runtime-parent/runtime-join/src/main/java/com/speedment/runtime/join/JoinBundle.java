package com.speedment.runtime.join;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.join.internal.component.join.JoinComponentImpl;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class JoinBundle  implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(JoinComponentImpl.class);
    }
    
}
