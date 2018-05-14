package com.speedment.runtime.nanohttpd.internal.util;

import com.speedment.common.json.Json;
import com.speedment.common.restservice.Endpoint;
import com.speedment.common.restservice.Parameter;
import com.speedment.common.restservice.Request;
import com.speedment.common.restservice.RequestMethod;
import com.speedment.common.restservice.Response;
import com.speedment.runtime.nanohttpd.throwable.SpeedmentRestException;
import fi.iki.elonen.NanoHTTPD;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class SessionUtil {

    public static Map<String, Object>
    parseQueryParams(Endpoint endpoint, NanoHTTPD.IHTTPSession session) {
        final Map<String, Object> arguments = new HashMap<>();

        // Parse query parameters
        if (session.getQueryParameterString() != null) {
            for (final String pair : session.getQueryParameterString().split("&")) {
                final int equalPos = pair.indexOf('=');
                final String key, value;
                if (equalPos == -1) {
                    key = pair;
                    value = "";
                } else {
                    try {
                        key   = URLDecoder.decode(pair.substring(0, equalPos),  "UTF-8");
                        value = URLDecoder.decode(pair.substring(equalPos + 1), "UTF-8");
                    } catch (final UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                final Optional<Parameter> param = endpoint.requestParameters().stream()
                    .filter(p -> p.name().equals(key))
                    .findAny();

                if (param.isPresent()) {
                    if (null != arguments.put(key, parseArgument(param.get(), value))) {
                        throw new SpeedmentRestException(format(
                            "Request contained duplicate parameter '%s'.",
                            key
                        ));
                    }
                }

                // Ignore extra query arguments.
            }
        }

        return arguments;
    }

    public static Object parseArgument(Parameter param, String matched) {
        switch (param.type()) {
            case INTEGER: return Integer.parseInt(matched);
            case LONG: return Long.parseLong(matched);
            case FLOAT: return Float.parseFloat(matched);
            case DOUBLE: return Double.parseDouble(matched);
            case BOOLEAN: return Boolean.parseBoolean(matched);
            case STRING: return matched;
            case SET: return Json.fromJson(matched);
            case LIST: return Json.fromJson(matched);
            case MAP: return Json.fromJson(matched);
            default: throw new UnsupportedOperationException(
                format("Unsupported parameter type '%s'.", param.type())
            );
        }
    }

    public static void assertAllSet(Set<String> requiredArgs, Set<String> actualArgs) {
        // Make sure all arguments have been set
        final Set<String> required = new HashSet<>(requiredArgs);
        required.removeAll(actualArgs);
        if (!required.isEmpty()) {
            throw new SpeedmentRestException(format(
                "Missing required arguments '%s'.",
                required.stream().collect(joining(", "))));
        }
    }

    public static NanoHTTPD.Response
    newResponse(Endpoint endpoint, NanoHTTPD.IHTTPSession session, Map<String, Object> arguments) {
        // Prepare the request object
        final Request request;
        if (endpoint.expectsRequestBody()) {
            request = Request.create(session.getInputStream(), arguments);
        } else {
            request = Request.empty(arguments);
        }

        // Serve the request
        final Response response = endpoint.serve(request);
        final NanoHTTPD.Response.IStatus status = NanoHTTPD.Response.Status.lookup(response.status());
        if (response.size() == 0) {
            return NanoHTTPD.newFixedLengthResponse(
                status, "text/plain", null, 0L);
        } else if (response.size() > 0) {
            return NanoHTTPD.newFixedLengthResponse(
                status, response.contentType(), response.responseBody(), response.size());
        } else  {
            return NanoHTTPD.newChunkedResponse(
                status, response.contentType(), response.responseBody());
        }
    }

    public static NanoHTTPD.Method asNanoHTTPDMethod(RequestMethod method) {
        switch (method) {
            case GET: return NanoHTTPD.Method.GET;
            case PUT: return NanoHTTPD.Method.PUT;
            case POST:return NanoHTTPD.Method.POST;
            case DELETE: return NanoHTTPD.Method.DELETE;
            case HEAD: return NanoHTTPD.Method.HEAD;
            case OPTIONS: return NanoHTTPD.Method.OPTIONS;
            case TRACE: return NanoHTTPD.Method.TRACE;
            case PATCH: return NanoHTTPD.Method.PATCH;
            default: throw new SpeedmentRestException(format(
                "Request method '%s' is not supported.",
                method.name()));
        }
    }

    private SessionUtil() {}
}
