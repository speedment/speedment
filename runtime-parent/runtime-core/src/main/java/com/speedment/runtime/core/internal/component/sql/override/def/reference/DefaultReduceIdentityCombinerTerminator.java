package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityCombinerTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultReduceIdentityCombinerTerminator<ENTITY> implements ReduceIdentityCombinerTerminator<ENTITY> {

    private DefaultReduceIdentityCombinerTerminator() {
    }

    @Override
    public <T, U> U apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final U identity,
        final BiFunction<U, ? super T, U> accumulator,
        final BinaryOperator<U> combiner
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        // identity nullable
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().reduce(identity, accumulator, combiner);
    }

    public static final ReduceIdentityCombinerTerminator<?> DEFAULT = new DefaultReduceIdentityCombinerTerminator<>();

}
