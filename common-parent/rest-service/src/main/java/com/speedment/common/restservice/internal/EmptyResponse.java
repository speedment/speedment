package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.Response;
import com.speedment.common.restservice.internal.io.EmptyInputStream;

import java.io.InputStream;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class EmptyResponse implements Response {

    private final int status;

    public EmptyResponse(int status) {
        this.status = status;
    }

    @Override
    public InputStream responseBody() {
        return new EmptyInputStream();
    }

    @Override
    public String contentType() {
        return null;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public int size() {
        return 0;
    }
}
