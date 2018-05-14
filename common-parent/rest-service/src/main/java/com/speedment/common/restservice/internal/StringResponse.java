package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.Response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class StringResponse implements Response {

    private final int status;
    private final String contentType;
    private final byte[] body;

    public StringResponse(int status, String contentType, String body) {
        this.status      = status;
        this.contentType = requireNonNull(contentType);
        this.body        = body.getBytes(UTF_8);
    }

    @Override
    public InputStream responseBody() {
        return new ByteArrayInputStream(body);
    }

    @Override
    public String contentType() {
        return contentType;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public int size() {
        return body.length;
    }
}
