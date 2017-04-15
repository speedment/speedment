package com.speedment.runtime.core.component.sql.override.doubles;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.doubles.DefaultDoubleCountTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface DoubleCountTerminator<ENTITY> extends DoubleTerminator {

    <T> long apply(
        SqlStreamOptimizerInfo<ENTITY> info,        
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        DoublePipeline pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> DoubleCountTerminator<ENTITY> defaultTerminator() {
        return (DoubleCountTerminator<ENTITY>) DEFAULT;
    }

}
