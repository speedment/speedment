package com.speedment.runtime.nanohttpd.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.restservice.Endpoint;
import com.speedment.common.restservice.RestComponent;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static com.speedment.common.injector.State.STARTED;
import static com.speedment.common.injector.State.STOPPED;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.asNanoHTTPDMethod;
import static java.lang.String.format;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class NanoHttpdRestComponent implements RestComponent {

    private final Map<NanoHTTPD.Method, RequestMethodHandler> handlers;
    private @Config(name="service.port", value="9999") int port;
    private @Inject Injector injector;
    private NanoHTTPD httpd;

    public NanoHttpdRestComponent() {
        handlers = new EnumMap<>(NanoHTTPD.Method.class);
        for (final NanoHTTPD.Method method : NanoHTTPD.Method.values()) {
            handlers.put(method, new RequestMethodHandler());
        }
    }

    @Override
    public void install(Endpoint endpoint) {
        handlers.get(asNanoHTTPDMethod(endpoint.method()))
            .install(injector.inject(endpoint));
    }

    @ExecuteBefore(STARTED)
    void setUp() {
        httpd = new NanoHTTPD(port) {
            @Override
            public Response serve(IHTTPSession session) {
                return handlers.get(session.getMethod()).serve(session);
            }
        };

        try {
            httpd.start();
        } catch (final IOException ex) {
            throw new RuntimeException(format(
                "Error binding service on port %d. Is something else running?",
                port), ex);
        }
    }

    @ExecuteBefore(STOPPED)
    void tearDown() {
        httpd.stop();
    }
}
