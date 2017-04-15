package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultToArrayTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface ToArrayTerminator<ENTITY> extends ReferenceTerminator {

    <T> Object[] apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> ToArrayTerminator<ENTITY> defaultTerminator() {
        return (ToArrayTerminator<ENTITY>) DEFAULT;
    }

}
