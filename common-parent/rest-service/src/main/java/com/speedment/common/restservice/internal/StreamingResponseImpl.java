package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.StreamingResponse;
import com.speedment.common.restservice.internal.io.Pipe;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class StreamingResponseImpl implements StreamingResponse {

    private final int status;
    private final String contentType;
    private final Pipe responseBody;

    public StreamingResponseImpl(int status, String contentType) throws IOException {
        this.responseBody = new Pipe();
        this.contentType  = requireNonNull(contentType);
        this.status       = status;
    }

    @Override
    public void write(String data) throws IOException {
        responseBody.write(data);
    }

    @Override
    public void write(byte[] data) throws IOException {
        responseBody.write(data);
    }

    @Override
    public void stop() {
        responseBody.stop();
    }

    @Override
    public void setOnStop(Runnable runnable) {
        responseBody.setOnClose(runnable);
    }

    @Override
    public InputStream responseBody() {
        return responseBody.getInputStream();
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
        return -1;
    }
}
