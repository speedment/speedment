package com.speedment.runtime.core.internal.component.sql.override.def.doubles;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.doubles.DoubleCountTerminator;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class DefaultDoubleCountTerminator<ENTITY> implements DoubleCountTerminator<ENTITY> {

    private DefaultDoubleCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final DoublePipeline pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return sqlStreamTerminator.optimize(pipeline).getAsDoubleStream().count();
    }

    public static final DoubleCountTerminator<?> DEFAULT = new DefaultDoubleCountTerminator<>();

}
