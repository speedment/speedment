package com.speedment.common.restservice.internal.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static java.util.Objects.requireNonNull;

/**
 * A pipe is an object that holds two piped streams, one input and one output.
 * It is configured so that it closes down properly when the input stream is
 * closed.
 *
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class Pipe implements AutoCloseable {

    private final PipedInputStream in;
    private final PipedOutputStream out;
    private Runnable onClose;

    public Pipe() throws IOException {
        this.in  = new PipedInputStream();
        this.out = new PipedOutputStream(in);
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = requireNonNull(onClose);
    }

    public InputStream getInputStream() {
        return new PipeInputStream(in, out, onClose);
    }

    public void write(String string) throws IOException {
        write(string.getBytes());
    }

    public synchronized void write(byte[] data) throws IOException {
        out.write(data);
        out.flush();
    }

    public void stop() {
        try { out.close(); }
        catch (final IOException ex) {}
    }

    @Override
    public void close() throws IOException {
        onClose.run();
        out.close();
        in.close();
    }

    private final static class PipeInputStream extends InputStream {

        private final PipedInputStream in;
        private final PipedOutputStream out;
        private final Runnable onClose;

        PipeInputStream(PipedInputStream in,
                        PipedOutputStream out,
                        Runnable onClose) {
            this.in      = requireNonNull(in);
            this.out     = requireNonNull(out);
            this.onClose = requireNonNull(onClose, "Close handler has not yet been set.");
        }

        @Override
        public int read() throws IOException {
            return in.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {return in.read(b, off, len);}

        @Override
        public int available() throws IOException {return in.available();}

        @Override
        public int read(byte[] b) throws IOException {return in.read(b);}

        @Override
        public long skip(long n) throws IOException {return in.skip(n);}

        @Override
        public void mark(int readlimit) {in.mark(readlimit);}

        @Override
        public void reset() throws IOException {in.reset();}

        @Override
        public boolean markSupported() {return in.markSupported();}

        @Override
        public void close() throws IOException {
            onClose.run();
            in.close();
            out.close();
        }
    }
}
