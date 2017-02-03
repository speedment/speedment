/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.internal.dependency.DependencyGraph;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import com.speedment.common.injector.internal.dependency.impl.DependencyGraphImpl;
import static com.speedment.common.injector.internal.util.InjectorUtil.findIn;
import static com.speedment.common.injector.internal.util.PrintUtil.horizontalLine;
import static com.speedment.common.injector.internal.util.PrintUtil.limit;
import static com.speedment.common.injector.internal.util.PropertiesUtil.loadProperties;
import static com.speedment.common.injector.internal.util.ReflectionUtil.newInstance;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseAncestors;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link InjectorBuilder}-interface.
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class InjectorBuilderImpl implements InjectorBuilder {
    
    private final static Logger LOGGER = 
        LoggerManager.getLogger(InjectorBuilderImpl.class);

    private final ClassLoader classLoader;
    private final Map<String, List<Class<?>>> injectables;
    private final Map<String, String> overriddenParams;
    private final Map<Class<?>, List<Consumer<?>>> beforeInitialized;
    private final Map<Class<?>, List<Consumer<?>>> beforeResolved;
    private final Map<Class<?>, List<Consumer<?>>> beforeStarted;
    private final Map<Class<?>, List<Consumer<?>>> beforeStopped;
    private Path configFileLocation;

    InjectorBuilderImpl() {
        this(defaultClassLoader(), Collections.emptySet());
    }

    InjectorBuilderImpl(ClassLoader classLoader) {
        this(classLoader, Collections.emptySet());
    }

    InjectorBuilderImpl(Set<Class<?>> injectables) {
        this(defaultClassLoader(), injectables);
    }

    InjectorBuilderImpl(ClassLoader classLoader, Set<Class<?>> injectables) {
        requireNonNull(injectables);

        this.classLoader        = requireNonNull(classLoader);
        this.injectables        = new LinkedHashMap<>();
        this.overriddenParams   = new HashMap<>();
        this.beforeInitialized  = new HashMap<>();
        this.beforeResolved     = new HashMap<>();
        this.beforeStarted      = new HashMap<>();
        this.beforeStopped      = new HashMap<>();
        this.configFileLocation = Paths.get("settings.properties");

        injectables.forEach(this::withComponent);
    }

    @Override
    public InjectorBuilder withComponent(Class<?> injectableType) {
        requireNonNull(injectableType);

        // Store the injectable under every superclass in the map, as well
        // as under every inherited InjectorKey value.
        traverseAncestors(injectableType)

            // only include classes that has an ancestor with the 
            // InjectorKey-annotation, or that are the original class.
            .filter(c -> c == injectableType || traverseAncestors(c)
                .anyMatch(c2 -> c2.isAnnotationPresent(InjectKey.class))
            )

            .forEachOrdered(c -> {
                // Store it under the class name itself
                appendInjectable(c.getName(), injectableType, true);

                // Include InjectorKey value
                if (c.isAnnotationPresent(InjectKey.class)) {
                    final InjectKey key = c.getAnnotation(InjectKey.class);
                    appendInjectable(
                        key.value().getName(), 
                        injectableType, 
                        key.overwrite()
                    );
                }
            });

        return this;
    }

    @Override
    public InjectorBuilder withBundle(Class<? extends InjectBundle> bundleClass) {
        try {
            final InjectBundle bundle = bundleClass.newInstance();
            bundle.injectables().forEach(this::withComponent);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new NoDefaultConstructorException(e);
        }
        return this;
    }

    @Override
    public InjectorBuilder withConfigFileLocation(Path configFile) {
        this.configFileLocation = requireNonNull(configFile);
        return this;
    }

    @Override
    public InjectorBuilder withParam(String name, String value) {
        overriddenParams.put(name, value);
        return this;
    }

    @Override
    public <T> InjectorBuilder beforeInitialized(Class<T> injectableType, Consumer<T> action) {
        beforeInitialized.computeIfAbsent(injectableType, c -> new LinkedList<>()).add(action);
        return this;
    }

    @Override
    public <T> InjectorBuilder beforeResolved(Class<T> injectableType, Consumer<T> action) {
        beforeResolved.computeIfAbsent(injectableType, c -> new LinkedList<>()).add(action);
        return this;
    }

    @Override
    public <T> InjectorBuilder beforeStarted(Class<T> injectableType, Consumer<T> action) {
        beforeStarted.computeIfAbsent(injectableType, c -> new LinkedList<>()).add(action);
        return this;
    }

    @Override
    public <T> InjectorBuilder beforeStopped(Class<T> injectableType, Consumer<T> action) {
        beforeStopped.computeIfAbsent(injectableType, c -> new LinkedList<>()).add(action);
        return this;
    }

    @Override
    public Injector build() 
    throws InstantiationException, NoDefaultConstructorException {

        // Load settings
        final File configFile = configFileLocation.toFile();
        final Properties properties = loadProperties(LOGGER, configFile);
        overriddenParams.forEach(properties::setProperty);

        final Set<Class<?>> injectablesSet = unmodifiableSet(
            injectables.values().stream()
                .flatMap(List::stream)
                .collect(toCollection(() -> new LinkedHashSet<>()))
        );

        final DependencyGraph graph = 
            DependencyGraphImpl.create(injectablesSet);

        final LinkedList<Object> instances = new LinkedList<>();

        LOGGER.debug("Creating " + injectablesSet.size() + 
            " injectable instances.");

        LOGGER.debug(horizontalLine());

        // Create an instance of every injectable type
        for (final Class<?> injectable : injectablesSet) {

            // If we are currently debugging, print out every created
            // instance and which configuration options are available for
            // it.
            if (LOGGER.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                LOGGER.debug("| %-71s CREATED |", 
                    limit(injectable.getSimpleName(), 71)
                );

                traverseFields(injectable)
                    .filter(f -> f.isAnnotationPresent(Config.class))
                    .map(f -> f.getAnnotation(Config.class))
                    .map(a -> String.format(
                        "|     %-48s %26s |", 
                        limit(a.name(), 48),
                        limit(properties.containsKey(a.name())
                            ? properties.get(a.name()).toString()
                            : a.value(), 26
                        )
                    ))
                    .forEachOrdered(LOGGER::debug);

                LOGGER.debug(horizontalLine());
            }

            final Object instance = newInstance(injectable, properties);
            instances.addFirst(instance);
        }

        // Build the Injector
        final Injector injector = new InjectorImpl(
            injectablesSet,
            unmodifiableList(instances),
            unmodifiableMap(beforeStopped),
            properties,
            classLoader,
            this
        );

        // Set the auto-injected fields
        instances.forEach(instance -> traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Inject.class))
            .distinct()
            .forEachOrdered(field -> {
                final Object value;

                if (Inject.class.isAssignableFrom(field.getType())) {
                    value = injector;
                } else {
                    value = findIn(
                        field.getType(),
                        injector,
                        instances, 
                        field.getAnnotation(WithState.class) != null
                    );
                }

                field.setAccessible(true);

                try {
                    field.set(instance, value);
                } catch (final IllegalAccessException ex) {
                    throw new RuntimeException(
                        "Could not access field '" + field.getName()
                            + "' in class '" + value.getClass().getName()
                            + "' of type '" + field.getType()
                            + "'.", ex
                    );
                }
            })
        );

        final AtomicBoolean hasAnythingChanged = new AtomicBoolean();
        final AtomicInteger nextState = new AtomicInteger(0);

        // Loop until all nodes have been started.
        Set<DependencyNode> unfinished;

        // Go through every state up and including STARTED.
        while (nextState.get() <= State.STARTED.ordinal()) {

            // Get a set of the nodes that has not yet reached that state,
            // and operate upon it until it is empty
            while (!(unfinished = graph.nodes()
                .filter(n -> n.getCurrentState().ordinal() < nextState.get())
                .collect(toSet())).isEmpty()) {

                hasAnythingChanged.set(false);

                unfinished.forEach(n -> {
                    // Determine the next state of this node.
                    final State state = State.values()[
                        n.getCurrentState().ordinal() + 1
                    ];

                    final Map<Class<?>, List<Consumer<?>>> actions;
                    switch (state) {
                        case INITIALIZED : actions = beforeInitialized; break;
                        case RESOLVED    : actions = beforeResolved;    break;
                        case STARTED     : actions = beforeStarted;     break;
                        default : throw new IllegalStateException(
                            "Can't be in state '" + state + "'."
                        );
                    }

                    // Check if all its dependencies have been satisfied.
                    if (n.canBe(state)) {

                        LOGGER.debug(horizontalLine());

                        // Retreive the instance for that node
                        final Object instance = findIn(
                            n.getRepresentedType(), 
                            injector, 
                            instances, 
                            true
                        );

                        // Execute all the executions for the next step.
                        n.getExecutions().stream()
                            .filter(e -> e.getState() == state)
                            .map(Execution::getMethod)
                            .forEach(m -> {
                                final Object[] params = 
                                    Stream.of(m.getParameters())
                                    .map(p -> findIn(
                                        p.getType(), 
                                        injector, 
                                        instances, 
                                        p.getAnnotation(WithState.class) 
                                            != null
                                    )).toArray(Object[]::new);

                                m.setAccessible(true);

                                // We might want to log exactly which steps we have
                                // completed.
                                if (LOGGER.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                                    final String shortMethodName
                                        = n.getRepresentedType().getSimpleName() + "#"
                                        + m.getName() + "("
                                        + Stream.of(m.getParameters())
                                        .map(p -> p.getType().getSimpleName().substring(0, 1))
                                        .collect(joining(", ")) + ")";

                                    LOGGER.debug(
                                        "| -> %-76s |", 
                                        limit(shortMethodName, 76)
                                    );
                                }

                                try {
                                    m.invoke(instance, params);
                                } catch (final IllegalAccessException 
                                             | IllegalArgumentException 
                                             | InvocationTargetException ex) {

                                    throw new RuntimeException(ex);
                                }
                            });

                        // Execute all manual configurations for the 
                        // particular step.
                        actions.entrySet().stream()
                            .filter(e -> e.getKey().isAssignableFrom(instance.getClass()))
                            .map(Map.Entry::getValue)
                            .map(list -> {
                                @SuppressWarnings("unchecked")
                                final List<Consumer<Object>> typed =
                                    (List<Consumer<Object>>) (List<?>) list;
                                return typed;
                            })
                            .flatMap(List::stream)
                            .forEachOrdered(action -> {
                                // We might want to log exactly which steps we have
                                // completed.
                                if (LOGGER.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                                    final String shortMethodName
                                        = n.getRepresentedType().getSimpleName() + "#<Consumer>()";

                                    LOGGER.debug(
                                        "| -> %-76s |", 
                                        limit(shortMethodName, 76)
                                    );
                                }

                                action.accept(instance);
                            });

                        // Update its state to the new state.
                        n.setState(state);
                        hasAnythingChanged.set(true);

                        LOGGER.debug(
                            "| %-66s %12s |",
                            limit(n.getRepresentedType().getSimpleName(), 66),
                            limit(state.name(), 12)
                        );
                    }
                });

                // The set was not empty when we entered the 'while' clause, 
                // and yet nothing has changed. This means that we are stuck
                // in an infinite loop.
                if (!hasAnythingChanged.get()) {
                    throw new IllegalStateException(
                        "Injector appears to be stuck in an infinite loop."
                    );
                }
            }

            // Every node has reached the desired state. 
            // Begin working with the next state.
            nextState.incrementAndGet();
        }

        LOGGER.debug(horizontalLine());
        LOGGER.debug(
            "| %-79s |",
            "All " + instances.size() + " components have been configured!"
        );
        LOGGER.debug(horizontalLine());

        return injector;
    }

    private void appendInjectable(String key, Class<?> clazz, boolean overwrite) {
        final List<Class<?>> list = Optional.ofNullable(
            injectables.remove(key)
        ).orElseGet(LinkedList::new);

        if (overwrite) {
            list.clear();
        }

        list.add(clazz);
        injectables.put(key, list);
    }

    private static ClassLoader defaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}