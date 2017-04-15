package com.speedment.runtime.core.component.sql.override;

import com.speedment.runtime.core.component.sql.override.doubles.DoubleSqlStreamTerminatorOverride;
import com.speedment.runtime.core.component.sql.override.ints.IntSqlStreamTerminatorOverride;
import com.speedment.runtime.core.component.sql.override.longs.LongSqlStreamTerminatorOverride;
import com.speedment.runtime.core.component.sql.override.reference.ReferenceSqlStreamTerminatorOverride;

/**
 *
 * @author Per Minborg
 */
public interface SqlStreamTerminatorComponent extends
    DoubleSqlStreamTerminatorOverride,
    IntSqlStreamTerminatorOverride,
    LongSqlStreamTerminatorOverride,
    ReferenceSqlStreamTerminatorOverride {
}
