package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultReduceTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface ReduceTerminator<ENTITY> extends ReferenceTerminator {

    <T> Optional<T> apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        BinaryOperator<T> accumulator
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> ReduceTerminator<ENTITY> defaultTerminator() {
        return (ReduceTerminator<ENTITY>) DEFAULT;
    }

}
