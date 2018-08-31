package com.speedment.runtime.config.resolver;

import com.speedment.runtime.config.internal.resolver.InternalResolverUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class used to work with documents.
 *
 * @author Emil Forslund
 * @since  3.1.7
 */
public final class ResolverUtil {

    /**
     * Returns a deep-copy of the specified object with the order retained.
     * Objects that are immutable may be returned instead of a copy. This
     * method supports the following types:
     * <ul>
     *     <li>{@link Integer}
     *     <li>{@link Long}
     *     <li>{@link Float}
     *     <li>{@link Double}
     *     <li>{@link Boolean}
     *     <li>{@link String}
     *     <li>{@link UUID}
     *     <li>{@link Map}
     *     <li>{@link List}
     *     <li>{@link LocalTime}
     *     <li>{@link LocalDate}
     *     <li>{@link LocalDateTime}
     * </ul>
     *
     * @param object  the object to copy
     * @param <T>     the type of the copied object
     * @return        the copy
     */
    public static <T> T deepCopy(T object) {
        return InternalResolverUtil.deepCopy(object);
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ResolverUtil() {}
}
