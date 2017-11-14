package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.trait.HasPredicates;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public interface RemoveOperation<ENTITY> extends Operation<ENTITY>, HasPredicates<ENTITY> {
}
