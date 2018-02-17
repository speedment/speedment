package com.speedment.runtime.join;

import com.speedment.common.function.TriFunction;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.pipeline.Pipeline;
import java.util.function.BiFunction;

/**
 * JoinStreamSupplierComponent that can be used to create Join objects
 * <p>
 * All methods that takes objects will throw a {@code NullPointerException } if
 * provided with one or several {@code null } values.
 *
 * @author Per Minborg
 */
@InjectKey(JoinStreamSupplierComponent.class)
public interface JoinStreamSupplierComponent {

    <T1, T2, T> Join<T> create(
        Pipeline p,
        BiFunction<T1, T2, T> constructor,
        TableIdentifier<T1> t1,
        TableIdentifier<T2> t2
    );

    <T1, T2, T3, T> Join<T> create(
        Pipeline p,
        TriFunction<T1, T2, T3, T> constructor,
        TableIdentifier<T1> t1,
        TableIdentifier<T2> t2,
        TableIdentifier<T3> t3
    );
}
