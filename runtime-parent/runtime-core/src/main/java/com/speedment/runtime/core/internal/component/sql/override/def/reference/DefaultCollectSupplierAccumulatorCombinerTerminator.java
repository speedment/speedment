package com.speedment.runtime.core.internal.component.sql.override.def.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.reference.CollectSupplierAccumulatorCombinerTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultCollectSupplierAccumulatorCombinerTerminator<ENTITY> implements CollectSupplierAccumulatorCombinerTerminator<ENTITY> {

    private DefaultCollectSupplierAccumulatorCombinerTerminator() {
    }

    @Override
    public <T, R> R apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final ReferencePipeline<T> pipeline,
        final Supplier<R> supplier,
        final BiConsumer<R, ? super T> accumulator,
        final BiConsumer<R, R> combiner
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return sqlStreamTerminator
            .optimize(pipeline)
            .getAsReferenceStream().collect(supplier, accumulator, combiner);
    }

    public static final CollectSupplierAccumulatorCombinerTerminator<?> DEFAULT = new DefaultCollectSupplierAccumulatorCombinerTerminator<>();

}
