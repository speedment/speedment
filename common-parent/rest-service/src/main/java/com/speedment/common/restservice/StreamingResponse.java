package com.speedment.common.restservice;

import java.io.IOException;

/**
 * Specialized {@link Response} that represents a stream of information that can
 * be added to even after the response has been passed from the endpoint.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface StreamingResponse extends Response {

    /**
     * Write the specified string as UTF-8 encoded bytes to the stream.
     *
     * @param data  the data to write (will be UTF-8 encoded)
     * @throws IOException  if it couldn't be sent
     */
    void write(String data) throws IOException;

    /**
     * Write the specified array of data to the stream.
     *
     * @param data  the data to write
     */
    void write(byte[] data) throws IOException;

    /**
     * Notify the reader that no more content is available.
     */
    void stop();

    /**
     * Set the action to perform when the streaming result is stopped.
     *
     * @param runnable  the action to perform
     */
    void setOnStop(Runnable runnable);

    @Override
    default int size() {
        return -1;
    }
}
