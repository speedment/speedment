package com.speedment.runtime.nanohttpd.internal;

import com.speedment.common.restservice.Endpoint;
import com.speedment.common.restservice.Parameter;
import com.speedment.runtime.nanohttpd.throwable.SpeedmentRestException;
import fi.iki.elonen.NanoHTTPD;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.assertAllSet;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.newResponse;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.parseArgument;
import static com.speedment.runtime.nanohttpd.internal.util.SessionUtil.parseQueryParams;
import static com.speedment.runtime.nanohttpd.internal.util.TrimUtil.trimLeadingTrailing;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class EndpointHandler {

    public static EndpointHandler create(Endpoint endpoint) {
        final String uri = trimLeadingTrailing(endpoint.path(), "/");
        final String[] words = uri.split("/");
        final StringBuilder regex = new StringBuilder("^");
        final Map<String, Parameter> params = new LinkedHashMap<>();
        for (final String word : words) {
            if (word.isEmpty()) {
                throw new SpeedmentRestException(format(
                    "Specified endpoint URI '%s' contains empty subpath.",
                    endpoint.path()
                ));
            }

            if (regex.length() > 1) {
                regex.append('/');
            }

            if (word.startsWith("{")) {
                if (word.endsWith("}")) {
                    final String paramName = word.substring(1, word.length() - 1);

                    final Optional<Parameter> param = endpoint.pathVariables().stream()
                        .filter(p -> p.name().equals(paramName))
                        .findAny();

                    if (!param.isPresent()) {
                        throw new SpeedmentRestException(format(
                            "Specified endpoint URI '%s' expects an unknown " +
                            "parameter '%s'.",
                            endpoint.path(),
                            paramName
                        ));
                    }

                    if (null != params.put(paramName, param.get())) {
                        throw new SpeedmentRestException(format(
                            "Specified endpoint URI '%s' contains duplicate " +
                                "parameter '%s'.",
                            endpoint.path(),
                            paramName
                        ));
                    }

                    regex.append("([^/]*)");
                } else {
                    throw new SpeedmentRestException(format(
                        "Specified endpoint URI '%s' contains malformed " +
                        "parameter '%s'.",
                        endpoint.path(), word
                    ));
                }
            } else {
                regex.append(word);
            }
        }

        regex.append('$');
        final Pattern pattern = Pattern.compile(regex.toString());

        final Set<String> requiredArgs = unmodifiableSet(Stream.concat(
            endpoint.pathVariables().stream(),
            endpoint.requestParameters().stream()
        ).filter(p -> !p.isOptional())
            .map(Parameter::name)
            .collect(Collectors.toSet()));

        return new EndpointHandler(pattern, endpoint, params, requiredArgs);
    }

    private final Pattern pattern;
    private final Endpoint endpoint;
    private final Set<String> requiredArgs;
    private final Map<String, Parameter> params;

    private EndpointHandler(Pattern pattern, Endpoint endpoint, Map<String, Parameter> params, Set<String> requiredArgs) {
        this.pattern      = requireNonNull(pattern);
        this.endpoint     = requireNonNull(endpoint);
        this.params       = requireNonNull(params);
        this.requiredArgs = requireNonNull(requiredArgs);
    }

    public Optional<NanoHTTPD.Response> parse(NanoHTTPD.IHTTPSession session) {
        final String uri = trimLeadingTrailing(session.getUri(), "/");
        final Matcher matcher = pattern.matcher(uri);
        if (!matcher.matches()) return Optional.empty();

        final Map<String, Object> arguments = parseQueryParams(endpoint, session);

        // Parse path variables
        int i = 0;
        for (final Map.Entry<String, Parameter> entry : params.entrySet()) {
            final Parameter param = entry.getValue();
            final String matched = matcher.group(i++);
            final Object argument;
            if (matched.isEmpty()) {
                if (param.isOptional()) {
                    argument = param.defaultValue();
                } else {
                    throw new SpeedmentRestException(format(
                        "Endpoint '%s' expects parameter '%s'.",
                        endpoint.path(),
                        param.name()
                    ));
                }
            } else {
                argument = parseArgument(param, matched);
            }

            if (null != arguments.put(param.name(), argument)) {
                throw new SpeedmentRestException(format(
                    "Request contained duplicate parameter '%s'.",
                    param.name()
                ));
            }
        }

        // Make sure all arguments have been set
        assertAllSet(requiredArgs, arguments.keySet());
        return Optional.of(newResponse(endpoint, session, arguments));
    }
}
