package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultAnyMatchTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface AnyMatchTerminator<ENTITY> extends ReferenceTerminator {

    <T> boolean apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        Predicate<? super T> predicate
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> AnyMatchTerminator<ENTITY> defaultTerminator() {
        return (AnyMatchTerminator<ENTITY>) DEFAULT;
    }

}
