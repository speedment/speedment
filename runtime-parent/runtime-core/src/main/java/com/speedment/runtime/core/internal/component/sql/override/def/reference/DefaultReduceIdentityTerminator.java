package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.BinaryOperator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultReduceIdentityTerminator<ENTITY> implements ReduceIdentityTerminator<ENTITY> {

    private DefaultReduceIdentityTerminator() {
    }

    @Override
    public <T> T apply(
        final SqlStreamOptimizerInfo<ENTITY> info,        
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final T identity,
        final BinaryOperator<T> accumulator
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        // identity nullable
        requireNonNull(accumulator);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().reduce(identity, accumulator);
    }

    public static final ReduceIdentityTerminator<?> DEFAULT = new DefaultReduceIdentityTerminator<>();

}
