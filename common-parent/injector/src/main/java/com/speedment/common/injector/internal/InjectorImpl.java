/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.injector.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectOrNull;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.Execution.ClassMapper;
import com.speedment.common.injector.internal.util.InjectorUtil;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.injector.internal.util.InjectorUtil.findIn;
import static com.speedment.common.injector.internal.util.PrintUtil.HORIZONTAL_LINE;
import static com.speedment.common.injector.internal.util.PrintUtil.limit;
import static com.speedment.common.injector.internal.util.PropertiesUtil.configureParams;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 * The default implementation of the {@link Injector} interface.
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public final class InjectorImpl implements Injector {

    /**
     * Create a new {@link InjectorBuilder} using the default implementation and
     * default {@code ClassLoader}.
     *
     * @return the injector builder
     */
    public static InjectorBuilder builder() {
        return new InjectorBuilderImpl();
    }

    /**
     * Create a new {@link InjectorBuilder} using the default implementation but
     * with a specific {@code ClassLoader}.
     *
     * @param classLoader the class loader to use
     * @return the injector builder
     */
    public static InjectorBuilder builder(ClassLoader classLoader) {
        return new InjectorBuilderImpl(classLoader);
    }

    public static final Logger LOGGER_INSTANCE = LoggerManager.getLogger(InjectorImpl.class);

    private final Set<Injectable<?>> injectables;
    private final List<Object> instances;
    private final Properties properties;
    private final ClassLoader classLoader;
    private final DependencyGraph graph;
    private final InjectorBuilder builder;

    InjectorImpl(
        final Set<Injectable<?>> injectables,
        final List<Object> instances,
        final Properties properties,
        final ClassLoader classLoader,
        final DependencyGraph graph,
        final InjectorBuilder builder
    ) {
        this.injectables = requireNonNull(injectables);
        this.instances = requireNonNull(instances);
        this.properties = requireNonNull(properties);
        this.classLoader = requireNonNull(classLoader);
        this.graph = requireNonNull(graph);
        this.builder = requireNonNull(builder);
    }

    @Override
    public <T> Stream<T> stream(Class<T> type) {
        return findAll(type);
    }

    @Override
    public <T> T getOrThrow(Class<T> type) {
        return find(type, true);
    }

    @Override
    public <T> T getAfterOrThrow(Class<T> type, T before) {
        return getAfter(type, before).orElseThrow(() ->
            new IllegalArgumentException("A component after " + before + " of type " + type.getName() + " could not be found. Components of type " + type.getSimpleName() + ": " + stream(type).map(Object::getClass).map(Class::getSimpleName).collect(Collectors.joining(", ")))
        );
    }

    @Override
    public <T> Optional<T> get(Class<T> type) {
        return Optional.ofNullable(find(type, false));
    }


    @Override
    public <T> Optional<T> getAfter(Class<T> type, T before) {
        requireNonNull(type);
        requireNonNull(before);

        boolean found = false;
        for (Iterator<T> i = stream(type).iterator(); i.hasNext(); ) {
            final T t = i.next();
            if (found) {
                return Optional.of(t);
            }
            if (t == before) {
                found = true;
            }
        }
        return Optional.empty();
    }

    @Override
    public Stream<Class<?>> injectables() {
        return injectables.stream().map(Injectable::get);
    }

    @Override
    public <T> T inject(T instance) {
        requireNonNull(instance);

        injectFields(instance);
        final InjectorProxy injectorProxy = builder.proxyFor(instance.getClass());
        configureParams(instance, properties, injectorProxy);
        return instance;
    }

    @Override
    public ClassLoader classLoader() {
        return classLoader;
    }

    @Override
    public void stop() {
        final AtomicBoolean hasAnythingChanged = new AtomicBoolean();

        // Create ClassMapper
        final ClassMapper classMapper = this::findRequired;

        // Loop until all nodes have been started.
        Set<DependencyNode> unfinished;
        while (!(unfinished = graph.nodes()
            .filter(n -> n.getCurrentState() != State.STOPPED)
            .collect(toSet())).isEmpty()) {

            hasAnythingChanged.set(false);

            unfinished.forEach(n -> stop(hasAnythingChanged, classMapper, n));

            if (!hasAnythingChanged.get()) {
                throw new IllegalStateException(
                    "Injector appears to be stuck in an infinite loop. The " +
                        "following components have not been stopped: " +
                        unfinished.stream()
                            .map(DependencyNode::getRepresentedType)
                            .map(Class::getSimpleName)
                            .collect(toSet())
                );
            }
        }

        LOGGER_INSTANCE.debug(HORIZONTAL_LINE);
        LOGGER_INSTANCE.debug(
            "| %-79s |",
            "All " + instances.size() + " components have been stopped!"
        );
        LOGGER_INSTANCE.debug(HORIZONTAL_LINE);
    }

    private void stop(AtomicBoolean hasAnythingChanged, ClassMapper classMapper, DependencyNode node) {
        // Check if all its dependencies have been satisfied.
        // when stopping.
        if (node.canBe(State.STOPPED)) {

            LOGGER_INSTANCE.debug(HORIZONTAL_LINE);

            // Retrieve the instance for that node
            final Object inst = find(node.getRepresentedType(), true);

            // Execute all the executions for the next step.
            node.getExecutions().stream()
                .filter(e -> e.getState() == State.STOPPED)
                .map(exec -> {
                    @SuppressWarnings("unchecked")
                    final Execution<Object> casted = (Execution<Object>) exec;
                    return casted;
                })
                .forEach(exec -> stopInstance(classMapper, inst, exec));

            // Update its state to the new state.
            node.setState(State.STOPPED);
            hasAnythingChanged.set(true);

            LOGGER_INSTANCE.debug(
                "| %-66s %12s |",
                node.getRepresentedType().getSimpleName(),
                State.STOPPED.name()
            );
        }
    }

    private void stopInstance(ClassMapper classMapper, Object inst, Execution<Object> exec) {
        // We might want to log exactly which steps we
        // have completed.
        if (LOGGER_INSTANCE.getLevel()
            .isEqualOrLowerThan(Level.DEBUG)) {

            LOGGER_INSTANCE.debug(
                "| -> %-76s |",
                limit(exec.toString(), 76)
            );
        }

        try {
            if (!exec.invoke(inst, classMapper) &&
                LOGGER_INSTANCE.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                LOGGER_INSTANCE.debug(
                    "|      %-74s |",
                    limit("(Ignored due to missing dependencies.)", 74)
                );
            }
        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex) {

            throw new InjectorException(ex);
        }
    }

    @Override
    public InjectorBuilder newBuilder() {
        return new InjectorBuilderImpl(builder);
    }

    private <T> Stream<T> findAll(Class<T> type) {
        return InjectorUtil.findAll(type, this, instances);
    }

    private <T> T findRequired(Class<T> type) {
        return find(type, true);
    }

    private <T> T find(Class<T> type, boolean required) {
        return findIn(type, this, instances, required);
    }

    private <T> void injectFields(T instance) {
        requireNonNull(instance);

        traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Inject.class)
                || f.isAnnotationPresent(InjectOrNull.class))
            .distinct()
            .forEachOrdered(field -> {
                final Object value;

                if (Injector.class.isAssignableFrom(field.getType())) {
                    value = this;
                } else {
                    value = find(
                        field.getType(),
                        field.isAnnotationPresent(Inject.class)
                    );
                }
                // Todo: log a warning that this is deprecated
                try {
                    final InjectorProxy injectorProxy = builder.proxyFor(instance.getClass());
                    injectorProxy.set(field, instance, value);
                } catch (final IllegalAccessException ex) {
                    final String err = String.format(
                        "Could not access field '%s' in class '%s' of " +
                            "type '%s'.",
                        field.getName(),
                        field.getDeclaringClass().getName(),
                        field.getType()
                    );

                    LOGGER_INSTANCE.error(ex, err);
                    throw new InjectorException(err, ex);
                }
            });
    }
}