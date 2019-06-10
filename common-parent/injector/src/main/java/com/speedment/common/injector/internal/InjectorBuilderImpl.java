/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.ConstructorResolutionException;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.exception.NotInjectableException;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.Execution.ClassMapper;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.internal.dependency.DependencyGraphImpl;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static com.speedment.common.injector.internal.util.InjectorUtil.findIn;
import static com.speedment.common.injector.internal.util.PrintUtil.horizontalLine;
import static com.speedment.common.injector.internal.util.PrintUtil.limit;
import static com.speedment.common.injector.internal.util.PropertiesUtil.loadProperties;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseAncestors;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import static com.speedment.common.injector.internal.util.ReflectionUtil.tryToCreate;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

/**
 * Default implementation of the {@link InjectorBuilder}-interface.
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class InjectorBuilderImpl implements InjectorBuilder {

    public final static Logger LOGGER_INSTANCE = LoggerManager.getLogger(InjectorBuilderImpl.class);

    private final ClassLoader classLoader;
    private final Map<String, List<Injectable<?>>> injectables;
    private final List<ExecutionBuilder<?>> executions;
    private final Map<String, String> overriddenParams;
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
        this.executions         = new LinkedList<>();
        this.overriddenParams   = new HashMap<>();
        this.configFileLocation = Paths.get("settings.properties");

        injectables.forEach(this::withComponent);
    }

    @Override
    public InjectorBuilder withComponent(Class<?> injectableType) {
        requireNonNull(injectableType);
        return withComponentAndSupplier(injectableType, null);
    }

    @Override
    public <T> InjectorBuilder withComponent(Class<T> injectableType, Supplier<T> instanceSupplier) {
        requireNonNull(injectableType);
        requireNonNull(instanceSupplier);
        return withComponentAndSupplier(injectableType, instanceSupplier);
    }

    private <T> InjectorBuilder withComponentAndSupplier(Class<T> injectableType, Supplier<T> instanceSupplier) {
        requireNonNull(injectableType);
        final Injectable<T> injectable = new Injectable<>(injectableType, instanceSupplier);

        // Append the class itself under its own name
        appendInjectable(injectableType.getName(), injectable, true);

        // Append the class under its InjectKey(s) (if any)
        traverseAncestors(injectableType)
            .filter(c -> c.isAnnotationPresent(InjectKey.class))
            .map(c -> c.getAnnotation(InjectKey.class))
            .forEachOrdered(key -> appendInjectable(key.value().getName(), injectable, key.overwrite()));

        return this;
    }

    @Override
    public InjectorBuilder withBundle(Class<? extends InjectBundle> bundleClass) {
        try {
            final InjectBundle bundle = bundleClass.newInstance();
            bundle.injectables().forEachOrdered(this::withComponent);
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
    public <T> InjectorBuilder before(ExecutionBuilder<T> executionBuilder) {
        executions.add(requireNonNull(executionBuilder));
        return this;
    }

    @Override
    public Injector build() throws InstantiationException, NoDefaultConstructorException {

        // Load settings
        final File configFile = configFileLocation.toFile();
        final Properties properties = loadProperties(LOGGER_INSTANCE, configFile);
        overriddenParams.forEach(properties::setProperty);

        final Set<Injectable<?>> injectablesSet = unmodifiableSet(
            injectables.values().stream()
                .flatMap(List::stream)
                .collect(toCollection(() -> new LinkedHashSet<>()))
        );

        final DependencyGraph graph = 
            DependencyGraphImpl.create(injectablesSet.stream().map(Injectable::get));

        final LinkedList<Object> instances = new LinkedList<>();

        LOGGER_INSTANCE.debug("Creating " + injectablesSet.size() +
            " injectable instances.");

        LOGGER_INSTANCE.debug(horizontalLine());

        // Create an instance of every injectable type

        final Set<Injectable<?>> injectablesLeft = new LinkedHashSet<>(injectablesSet);
        int injectablesLeftSize = injectablesLeft.size();
        while (!injectablesLeft.isEmpty()) {
            final Iterator<Injectable<?>> it = injectablesLeft.iterator();

            while (it.hasNext()) {
                final Injectable<?> injectable = it.next();

                // If we are currently debugging, print out every created
                // instance and which configuration options are available for
                // it.
                if (LOGGER_INSTANCE.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                    LOGGER_INSTANCE.debug("| %-71s CREATED |",
                        limit(injectable.get().getSimpleName(), 71)
                    );

                    traverseFields(injectable.get())
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
                        .forEachOrdered(LOGGER_INSTANCE::debug);

                    LOGGER_INSTANCE.debug(horizontalLine());
                }

                boolean created = false;
                if (injectable.hasSupplier()) {
                    final Object instance = injectable.supplier().get();
                    instances.addFirst(instance);
                    created = true;
                } else {
                    final Optional<?> instance = tryToCreate(injectable.get(), properties, instances);
                    if (instance.isPresent()) {
                        instances.addFirst(instance.get());
                        created = true;
                    }
                }

                if (created) {
                    it.remove();
                }
            }

            // Check if no injectables was instantiated this pass
            if (injectablesLeftSize == injectablesLeft.size()) {
                final StringBuilder msg = new StringBuilder();
                msg.append(injectablesLeft.size());
                msg.append("  injectables could not be instantiated. These where: [\n");
                injectablesLeft.stream()
                    .map(Injectable::get)
                    .map(Class::getName)
                    .forEachOrdered(s -> msg.append("  ").append(s).append('\n'));
                msg.append("]");
                throw new ConstructorResolutionException(msg.toString());
            }

            injectablesLeftSize = injectablesLeft.size();
        }


        // Build the Injector
        final Injector injector = new InjectorImpl(
            injectablesSet,
            unmodifiableList(instances),
            properties,
            classLoader,
            graph,
            this
        );
        
        // Create ClassMapper
        final ClassMapper classMapper = new ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) 
                throws NotInjectableException {
                return findIn(
                    type, 
                    injector, 
                    instances,
                    true // Required = true
                );
            }

            @Override
            public <T> T applyOrNull(Class<T> type) {
                return findIn(
                    type,
                    injector,
                    instances,
                    false
                );
            }
        };

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
        
        // Build explicit executions and add them to the graph
        executions.stream()
            .map(builder -> builder.build(graph))
            .forEachOrdered(execution -> {
                final DependencyNode node = graph.get(execution.getType());
                node.getExecutions().add(execution);
            });

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
                    final State state = n.getCurrentState().next();

                    // Check if all its dependencies have been satisfied.
                    if (n.canBe(state)) {

                        LOGGER_INSTANCE.debug(horizontalLine());

                        // Retrieve the instance for that node
                        final Object instance = findIn(
                            n.getRepresentedType(), 
                            injector, 
                            instances, 
                            true
                        );

                        // Execute all the executions for the next step.
                        n.getExecutions().stream()
                            .filter(e -> e.getState() == state)
                            .map(exec -> {
                                @SuppressWarnings("unchecked")
                                final Execution<Object> casted = 
                                    (Execution<Object>) exec;
                                return casted;
                            })
                            .forEach(exec -> {
                                
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
                                    exec.invoke(instance, classMapper);
                                } catch (final IllegalAccessException 
                                             | IllegalArgumentException 
                                             | InvocationTargetException ex) {

                                    throw new RuntimeException(ex);
                                }
                            });

                        // Update its state to the new state.
                        n.setState(state);
                        hasAnythingChanged.set(true);

                        LOGGER_INSTANCE.debug(
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

        LOGGER_INSTANCE.debug(horizontalLine());
        LOGGER_INSTANCE.debug(
            "| %-79s |",
            "All " + instances.size() + " components have been configured!"
        );
        LOGGER_INSTANCE.debug(horizontalLine());

        return injector;
    }

    private void appendInjectable(String key, Injectable<?> clazz, boolean overwrite) {
        final List<Injectable<?>> list = Optional.ofNullable(
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