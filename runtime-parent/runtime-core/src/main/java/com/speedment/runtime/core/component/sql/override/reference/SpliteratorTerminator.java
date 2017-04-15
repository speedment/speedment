package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultSpliteratorTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.Spliterator;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface SpliteratorTerminator<ENTITY> extends ReferenceTerminator {

    <T> Spliterator<T> apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> SpliteratorTerminator<ENTITY> defaultTerminator() {
        return (SpliteratorTerminator<ENTITY>) DEFAULT;
    }

}
