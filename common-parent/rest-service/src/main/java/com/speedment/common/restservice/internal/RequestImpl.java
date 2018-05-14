package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.Request;
import com.speedment.common.restservice.internal.io.EmptyInputStream;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class RequestImpl implements Request {

    private final InputStream requestBody;
    private final Map<String, Object> arguments;

    public RequestImpl(InputStream requestBody) {
        this.requestBody = requireNonNull(requestBody);
        this.arguments   = emptyMap();
    }

    public RequestImpl(Map<String, Object> arguments) {
        this.requestBody = new EmptyInputStream();
        this.arguments   = unmodifiableMap(new HashMap<>(arguments));
    }

    public RequestImpl(InputStream requestBody, Map<String, Object> arguments) {
        this.requestBody = requireNonNull(requestBody);
        this.arguments   = unmodifiableMap(new HashMap<>(arguments));
    }

    @Override
    public InputStream requestBody() {
        return requestBody;
    }

    @Override
    public Map<String, Object> arguments() {
        return arguments;
    }
}