package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultCollectTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.stream.Collector;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface CollectTerminator<ENTITY> extends ReferenceTerminator {

    <T, R, A> R apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        Collector<? super T, A, R> collector
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> CollectTerminator<ENTITY> defaultTerminator() {
        return (CollectTerminator<ENTITY>) DEFAULT;
    }

}
