package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultCollectSupplierAccumulatorCombinerTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface CollectSupplierAccumulatorCombinerTerminator<ENTITY> extends ReferenceTerminator {

    <T, R> R apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline,
        Supplier<R> supplier,
        BiConsumer<R, ? super T> accumulator,
        BiConsumer<R, R> combiner
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> CollectSupplierAccumulatorCombinerTerminator<ENTITY> defaultTerminator() {
        return (CollectSupplierAccumulatorCombinerTerminator<ENTITY>) DEFAULT;
    }

}
