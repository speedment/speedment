package com.speedment.common.restservice.internal.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class EmptyInputStream extends InputStream  {

    @Override
    public int read() throws IOException {
        return -1;
    }

    @Override
    public int read(byte[] b) {
        return 0;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        return 0;
    }

    @Override
    public long skip(long n) {
        return 0;
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public void close() {}
}
