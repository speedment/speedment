package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.trait.HasConsumers;
import com.speedment.runtime.bulk.trait.HasMappers;
import com.speedment.runtime.bulk.trait.HasPredicates;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public interface UpdateOperation<ENTITY> extends Operation<ENTITY>, HasPredicates<ENTITY>, HasConsumers<ENTITY>, HasMappers<ENTITY> {
}
