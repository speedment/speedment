package com.speedment.runtime.join.trait;

import com.speedment.common.function.QuadFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;

/**
 *
 * @author Per Minborg
 */
public interface HasCreateJoin4 {

    /**
     * Creates and returns a new Join object using the provided {@code pipeline}
     * whereby elements in the returned Join's {@link Join#stream() } method
     * will be constructed using the provided {@code constructor}.
     *
     * @param <T0> entity type of the first table
     * @param <T1> entity type of the second table
     * @param <T2> entity type of the third table
     * @param <T3> entity type of the fourth table
     * @param <T> stream type in returned Join object's stream method
     * @param stages pipeline with information on the joined tables
     * @param constructor to be applied by the returned Join objects stream
     * method
     * @param t0 identifier of the first table
     * @param t1 identifier of the second table
     * @param t2 identifier of the third table
     * @param t3 identifier of the fourth table
     * @return a new Join object
     *
     * @throws NullPointerException if any of the provided arguments are
     * {@code null}
     */
    <T0, T1, T2, T3, T> Join<T> createJoin(
        List<Stage<?>> stages,
        QuadFunction<T0, T1, T2, T3, T> constructor,
        TableIdentifier<T0> t0,
        TableIdentifier<T1> t1,
        TableIdentifier<T2> t2,
        TableIdentifier<T3> t3
    );
}
