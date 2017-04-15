package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.ToArrayGeneratorTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.IntFunction;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultToArrayGeneratorTerminator<ENTITY> implements ToArrayGeneratorTerminator<ENTITY> {

    private DefaultToArrayGeneratorTerminator() {
    }

    @Override
    public <T, A> A[] apply(
        final SqlStreamOptimizerInfo<ENTITY> info,        
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final IntFunction<A[]> generator
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(generator);
        return sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().toArray(generator);
    }

    public static final ToArrayGeneratorTerminator<?> DEFAULT = new DefaultToArrayGeneratorTerminator<>();

}
