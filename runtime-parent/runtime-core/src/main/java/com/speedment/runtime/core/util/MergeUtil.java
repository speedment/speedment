package com.speedment.runtime.core.util;

import com.speedment.runtime.core.internal.util.InternalMergeUtil;
import com.speedment.runtime.core.manager.Manager;

import java.util.Set;

public final class MergeUtil {

    private MergeUtil() {}

    /**
     * Merges the provided {@code entities} with the underlying database.
     *
     * @param <T> entity type
     * @param manager to use for merging
     * @param entities to merge with the database
     * @return the entities that have been merged
     */
    public static <T> Set<T> merge(Manager<T> manager, Set<T> entities) {
        return InternalMergeUtil.merge(manager, entities);
    }

}
