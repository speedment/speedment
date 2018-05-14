package com.speedment.common.restservice;

import java.util.Collection;
import java.util.Collections;

/**
 * A Single REST Endpoint that should be mapped in the HTTP Server. Incoming
 * requests that match the specified {@link #path()} and {@link #method()}
 * should be routed to the {@link #serve(Request)} method, producing a
 * {@link Response} that can be sent back.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface Endpoint {

    /**
     * The path of the endpoint. Parameters are specified in brackets.
     * <p>
     * <em>Examples:</em>
     * <pre>{@code
     * /foo
     * /foo/bar
     * /foo/{id}
     * /{name}/{id}
     * }</pre>
     *
     * @return  the path of the endpoint
     */
    String path();

    /**
     * The request method that this endpoint is expecting.
     *
     * @return  the request method
     */
    default RequestMethod method() {
        return RequestMethod.GET;
    }

    /**
     * Returns {@code true} if this method expects a request body, otherwise
     * {@code false}.
     *
     * @return  {@code true} if a request body stream is expected, otherwise
     *          {@code false}
     */
    default boolean expectsRequestBody() {
        switch (method()) {
            case POST: case PUT: case PATCH: return true;
            default: return false;
        }
    }

    /**
     * Collection of parameters that need to be supplied with a request to this
     * endpoint. The parameter names and collection order must correspond to the
     * parameters specified in the {@link #path()}.
     *
     * @return  the expected request parameters
     */
    default Collection<Parameter> pathVariables() {
        return Collections.emptyList();
    }

    /**
     * Collection of parameters that need to be supplied with a request to this
     * endpoint. These parameters are expected to be supplied as query
     * parameters in the URL.
     *
     * @return  the expected request parameters
     */
    default Collection<Parameter> requestParameters() {
        return Collections.emptyList();
    }

    /**
     * Serves the specified request. It is safe to assume that all the
     * {@link #pathVariables()} and {@link #requestParameters()} that this
     * object specified are present.
     *
     * @param request  the request data
     * @return         the response data
     */
    Response serve(Request request);
}
