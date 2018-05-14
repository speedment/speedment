package com.speedment.runtime.nanohttpd.internal;

import com.speedment.common.restservice.Endpoint;
import com.speedment.common.restservice.Parameter;
import com.speedment.runtime.nanohttpd.throwable.SpeedmentRestException;
import fi.iki.elonen.NanoHTTPD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.assertAllSet;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.newResponse;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.parseQueryParams;
import static com.speedment.runtime.nanohttpd.internal.util.TrimUtil.trimLeadingTrailing;
import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class RequestMethodHandler {

    private final Map<String, Endpoint> simpleEndpoints;
    private final List<EndpointHandler> matchers;

    public RequestMethodHandler() {
        this.simpleEndpoints = new HashMap<>();
        this.matchers        = new ArrayList<>();
    }

    public void install(Endpoint ep) {
        final String uri = trimLeadingTrailing(ep.path(), "/");
        if (uri.contains("{")) {
            simpleEndpoints.put(uri, ep);
        } else {
            matchers.add(EndpointHandler.create(ep));
        }
    }

    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        final String uri = trimLeadingTrailing(session.getUri(), "/");
        final Endpoint endpoint = simpleEndpoints.get(uri);
        if (endpoint == null) {
            return matchers.stream()
                .map(ep -> ep.parse(session))
                .filter(Optional::isPresent)
                .findFirst()
                .flatMap(Function.identity())
                .orElseThrow(() -> new SpeedmentRestException(format(
                    "Could not find mapping for %s '%s'.",
                    session.getMethod().name(),
                    uri
                )));
        } else {
            final Map<String, Object> arguments = parseQueryParams(endpoint, session);
            final Set<String> requiredArgs = endpoint.requestParameters().stream()
                .filter(p -> !p.isOptional())
                .map(Parameter::name)
                .collect(toSet());

            assertAllSet(requiredArgs, arguments.keySet());
            return newResponse(endpoint, session, arguments);
        }
    }
}
