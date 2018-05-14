package com.speedment.common.restservice;

import com.speedment.common.restservice.internal.EmptyResponse;
import com.speedment.common.restservice.internal.StreamingResponseImpl;
import com.speedment.common.restservice.internal.StringResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Response for a particular request.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface Response {

    /**
     * Returns a streaming result that can be writted to using
     * {@link StreamingResponse#write(String)}. The status of the response will
     * be {@code 200 OK}.
     *
     * @param contentType   the content type to use
     * @return              the created response
     */
    static StreamingResponse stream(String contentType) throws IOException {
        return new StreamingResponseImpl(200, contentType);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be {@code 200 OK}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response ok(String contentType, String message) {
        return new StringResponse(200, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be {@code 201 CREATED}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response created(String contentType, String message) {
        return new StringResponse(201, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be {@code 202 ACCEPTED}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response accepted(String contentType, String message) {
        return new StringResponse(202, contentType, message);
    }

    /**
     * Returns a fixed-size response with status code {@code 204 NO CONTENT}.
     *
     * @return  the created response
     */
    static Response noContent() {
        return new EmptyResponse(204);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 400 BAD REQUEST}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response badRequest(String contentType, String message) {
        return new StringResponse(400, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 401 UNAUTHORIZED}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response unauthorized(String contentType, String message) {
        return new StringResponse(401, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 403 FORBIDDEN}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response forbidden(String contentType, String message) {
        return new StringResponse(403, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 404 NOT FOUND}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response notFound(String contentType, String message) {
        return new StringResponse(404, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 405 NOT ALLOWED}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response notAllowed(String contentType, String message) {
        return new StringResponse(405, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 406 NOT ACCEPTABLE}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response notAcceptable(String contentType, String message) {
        return new StringResponse(406, contentType, message);
    }

    /**
     * Returns a fixed-size response with the specified {@code contentType}
     * header and body. The status of the response will be
     * {@code 500 INTERNAL SERVER ERROR}.
     *
     * @param contentType  the content type to use
     * @param message      the fixed-size response body
     * @return             the created response
     */
    static Response internalError(String contentType, String message) {
        return new StringResponse(500, contentType, message);
    }

    /**
     * Returns a single-use stream over the result of the operation.
     *
     * @return  stream to read response body from
     */
    InputStream responseBody();

    /**
     * The value of the {@code contentType}-header to set in the result. This
     * may be {@code null} only if {@link #size()} returns 0.
     *
     * @return  the content type header value
     */
    String contentType();

    /**
     * The HTTP status code to return. 2XX-codes are considered successes.
     *
     * @return  the HTTP status code
     */
    int status();

    /**
     * The size of the returned content if known, otherwise {@code -1}.
     *
     * @return  the size if known, otherwise {@code -1}
     */
    int size();

}
