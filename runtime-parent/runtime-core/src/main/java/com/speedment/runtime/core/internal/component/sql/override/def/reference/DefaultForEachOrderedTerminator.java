package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.ForEachOrderedTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultForEachOrderedTerminator<ENTITY> implements ForEachOrderedTerminator<ENTITY> {

    private DefaultForEachOrderedTerminator() {
    }

    @Override
    public <T> void apply(
        final SqlStreamOptimizerInfo<ENTITY> info,        
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final Consumer<? super T> action
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(action);
        sqlStreamTerminator.optimize(pipeline).getAsReferenceStream().forEachOrdered(action);
    }

    public static final ForEachOrderedTerminator<?> DEFAULT = new DefaultForEachOrderedTerminator<>();

}
