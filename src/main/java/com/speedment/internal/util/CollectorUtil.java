package com.speedment.internal.util;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 * @author Emil Forslund
 */
public final class CollectorUtil {
    
    
    
    /**
     * Utility classes should not be instantiated.
     */
    private CollectorUtil() {
        instanceNotAllowed(getClass());
    }
}