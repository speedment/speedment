package com.speedment.common.restservice;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Functional interface describing a method that writes to a
 * {@link OutputStream}, throwing an exception if something bad happens.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
@FunctionalInterface
public interface ResponseBodyWriter {

    /**
     * Write the response body to the specified output stream.
     *
     * @param out  the stream to write to
     * @throws IOException  for an example if the client closes the connection
     *                      unexpectedly
     */
    void write(OutputStream out) throws IOException;

}
