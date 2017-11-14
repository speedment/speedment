package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.trait.HasGeneratorSuppliers;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public interface PersistOperation<ENTITY> extends Operation<ENTITY>, HasGeneratorSuppliers<ENTITY> {
}
