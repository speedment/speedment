package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultToArrayGeneratorTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.function.IntFunction;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface ToArrayGeneratorTerminator<ENTITY> extends ReferenceTerminator {

    <T, A> A[] apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        IntFunction<A[]> generator
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> ToArrayGeneratorTerminator<ENTITY> defaultTerminator() {
        return (ToArrayGeneratorTerminator<ENTITY>) DEFAULT;
    }

}
