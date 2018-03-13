package com.speedment.runtime.join.internal.component.stream;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.SqlAdapter;

/**
 *
 * @author Per Minborg
 */
@FunctionalInterface
public interface SqlAdapterMapper {

    <ENTITY> SqlAdapter<ENTITY> apply(TableIdentifier<ENTITY> identifier);

}
