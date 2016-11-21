package com.speedment.common.mutablestream.source;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.internal.source.WrappedStreamImpl;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>  the streamed type (or wrapper)
 * @param <TS> the stream type
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface WrappedStream<T, TS extends BaseStream<T, TS>> 
extends HasNext<T, TS> {

    static <T, TS extends BaseStream<T, TS>> 
    WrappedStream<T, TS> create(TS wrapped) {
        final WrappedStream<T, TS> result = new WrappedStreamImpl<>(wrapped);
        return result;
    }
    
}