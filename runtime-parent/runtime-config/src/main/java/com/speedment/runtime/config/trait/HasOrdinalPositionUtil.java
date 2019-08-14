package com.speedment.runtime.config.trait;

import java.util.Comparator;

import static com.speedment.common.mapstream.MapStream.comparing;

public final class HasOrdinalPositionUtil {

    private HasOrdinalPositionUtil() {}

    /**
     * The key of the {@code ordinalPosition} property.
     */
    public static final String ORDINAL_POSITION = "ordinalPosition";

    /**
     * The default {@link Comparator} used for documents that implement the
     * {@link HasOrdinalPosition} trait. This will simply order the elements
     * based on the natural ordering of their {@link HasOrdinalPosition#getOrdinalPosition()}
     * result.
     */
    public static final Comparator<HasOrdinalPosition> COMPARATOR = comparing(HasOrdinalPosition::getOrdinalPosition);


}
