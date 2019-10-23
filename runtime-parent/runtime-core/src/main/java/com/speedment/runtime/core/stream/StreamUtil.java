package com.speedment.runtime.core.stream;

import com.speedment.runtime.core.internal.stream.InternalStreamUtil;

import java.util.Iterator;
import java.util.stream.Stream;

public final class StreamUtil {
    private StreamUtil() {}

    public static <T> Stream<T> asStream(Iterator<T> iterator) {
        return InternalStreamUtil.asStream(iterator);
    }

}
