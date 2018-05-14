package com.speedment.common.restservice;

import com.speedment.common.restservice.internal.RequestImpl;

import java.io.InputStream;
import java.util.Map;

/**
 * A request sent to an {@link Endpoint}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface Request {

    /**
     * Creates and returns a new instance of this interface using the default
     * implementation.
     *
     * @param requestBody  input stream over the request body (single use)
     * @param arguments    unmodifiable map of arguments
     * @return  the created request
     */
    static Request create(InputStream requestBody, Map<String, Object> arguments) {
        return new RequestImpl(requestBody, arguments);
    }

    /**
     * Creates and returns a new instance of this interface using the default
     * implementation.
     *
     * @param arguments    unmodifiable map of arguments
     * @return  the created request
     */
    static Request empty(Map<String, Object> arguments) {
        return new RequestImpl(arguments);
    }

    /**
     * Single-use input stream over the requestBody.
     *
     * @return  the request body stream
     */
    InputStream requestBody();

    /**
     * Parameters given in the request. The value will be the value expected in
     * the {@link Parameter} object. If a particular parameter was
     * {@link Parameter#isOptional() optional}, it might not be present in
     * the map.
     *
     * @return  map of parameter values
     */
    Map<String, Object> arguments();

}