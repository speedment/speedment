package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultReduceIdentityCombinerTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface ReduceIdentityCombinerTerminator<ENTITY> extends ReferenceTerminator {

    <T, U> U apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        U identity,
        BiFunction<U, ? super T, U> accumulator,
        BinaryOperator<U> combiner
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> ReduceIdentityCombinerTerminator<ENTITY> defaultTerminator() {
        return (ReduceIdentityCombinerTerminator<ENTITY>) DEFAULT;
    }

}
