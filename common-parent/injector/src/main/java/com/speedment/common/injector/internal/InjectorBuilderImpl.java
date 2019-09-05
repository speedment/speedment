/*
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
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.InjectOrNull;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.*;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.Execution.ClassMapper;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.internal.execution.ReflectionExecutionImpl;
import com.speedment.common.injector.internal.util.ReflectionUtil;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.common.injector.internal.util.InjectorUtil.findIn;
import static com.speedment.common.injector.internal.util.PrintUtil.horizontalLine;
import static com.speedment.common.injector.internal.util.PrintUtil.limit;
import static com.speedment.common.injector.internal.util.PropertiesUtil.loadProperties;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseAncestors;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseMethods;
import static com.speedment.common.injector.internal.util.ReflectionUtil.tryToCreate;
import static com.speedment.common.injector.internal.util.StringUtil.commaAnd;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Default implementation of the {@link InjectorBuilder}-interface.
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class InjectorBuilderImpl implements InjectorBuilder {

    public static final Logger LOGGER_INSTANCE = LoggerManager.getLogger(InjectorBuilderImpl.class);

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

    private InjectorBuilderImpl(ClassLoader classLoader, Set<Class<?>> injectables) {
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
    public Injector build() throws InstantiationException {

        // Load settings
        final File configFile = configFileLocation.toFile();
        final Properties properties = loadProperties(LOGGER_INSTANCE, configFile);
        overriddenParams.forEach(properties::setProperty);

        final Set<Class<?>> allInjectableTypes = unmodifiableSet(
            injectables.values().stream()
                .flatMap(List::stream)
                .map(Injectable::get)
                .collect(toSet())
        );

        final Set<Injectable<?>> injectablesSet = unmodifiableSet(
            injectables.values().stream()
                .flatMap(List::stream)
                .collect(toCollection((Supplier<Set<Injectable<?>>>) LinkedHashSet::new))
        );

        final DependencyGraph graph = DependencyGraph.create(injectablesSet.stream().map(Injectable::get));

        final LinkedList<Object> instances = new LinkedList<>();

        LOGGER_INSTANCE.debug(String.format("Creating %d injectable instances.", injectablesSet.size()));

        LOGGER_INSTANCE.debug(horizontalLine());

        // Create an instance of every injectable type

        final Set<Injectable<?>> injectablesLeft = new LinkedHashSet<>(injectablesSet);
        int injectablesLeftSize = injectablesLeft.size();
        while (!injectablesLeft.isEmpty()) {
            final Iterator<Injectable<?>> it = injectablesLeft.iterator();

            while (it.hasNext()) {
                final Injectable<?> injectable = it.next();

                boolean created = false;
                if (injectable.hasSupplier()) {
                    final Object instance = injectable.supplier().get();
                    instances.addFirst(instance);
                    created = true;
                } else {
                    final Optional<?> instance = tryToCreate(injectable.get(), properties, instances, allInjectableTypes);
                    if (instance.isPresent()) {
                        instances.addFirst(instance.get());
                        created = true;
                    }
                }

                if (created) {
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
                            .map(a -> format(
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
                    it.remove();
                } else {

                    // If we are currently debugging, print out every created
                    // instance and which configuration options are available for
                    // it.
                    if (LOGGER_INSTANCE.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                        LOGGER_INSTANCE.debug("| %-71s PENDING |",
                            limit(injectable.get().getSimpleName(), 71)
                        );
                        LOGGER_INSTANCE.debug(horizontalLine());
                    }
                }
            }

            // Check if no injectables was instantiated this pass
            if (injectablesLeftSize == injectablesLeft.size()) {
                final StringBuilder msg = new StringBuilder();
                msg.append(injectablesLeft.size());
                msg.append(" injectables could not be instantiated. These where: [\n");
                injectablesLeft.stream()
                    .map(Injectable::get)
                    .map(c -> ReflectionUtil.errorMsg(c, instances))
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
            public <T> T apply(Class<T> type) {
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

        // Make sure annotations are used correctly
        instances.forEach(instance ->
            traverseMethods(instance.getClass())
                .filter(m -> m.isAnnotationPresent(ExecuteBefore.class))
                .forEach(m -> {
                    final ExecuteBefore execute = m.getAnnotation(ExecuteBefore.class);
                    final List<Parameter> errenousParams = Stream.of(m.getParameters())
                        .filter(p -> p.isAnnotationPresent(WithState.class))
                        .filter(p -> {
                            final State paramState = p.getAnnotation(WithState.class).value();
                            return !(paramState == execute.value()
                            || (paramState.isBefore(State.STARTED) && paramState.next() == execute.value()));
                        })
                        .collect(toList());

                    if (!errenousParams.isEmpty()) {
                        throw new MisusedAnnotationException(format(
                            "The class %s has an auto-executed method %s(%s) " +
                            "that should execute just before the instance " +
                            "enters the %s state, so the parameters must %s " +
                            "when the method is invoked. Yet, the " +
                            "@WithState-annotation is present on %d " +
                            "parameter%s with requested state%s %s.",
                            instance.getClass().getSimpleName(),
                            m.getName(),
                            Stream.of(m.getParameters())
                                .map(Parameter::getType)
                                .map(Class::getSimpleName)
                                .collect(joining(", ")),
                            execute.value().name(),
                            execute.value() == State.CREATED
                                ? "also be in the CREATED state"
                                : String.format(
                                    "either be in the %s or the %s state",
                                    execute.value().previous().name(),
                                    execute.value().name()
                                ),
                            errenousParams.size(),
                            errenousParams.size() > 1 ? "s" : "",
                            errenousParams.size() > 1 ? "s" : "",
                            commaAnd(errenousParams.stream()
                                .map(p -> p.getAnnotation(WithState.class))
                                .map(WithState::value)
                                .map(State::name)
                                .toArray(String[]::new))
                        ));
                    }
                })
        );

        // Set the auto-injected fields
        instances.forEach(instance -> traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Inject.class)
                      || f.isAnnotationPresent(InjectOrNull.class)
            )
            .distinct()
            .forEachOrdered(field -> {
                final Object value;

                if (Injector.class.isAssignableFrom(field.getType())) {
                    value = injector;
                } else {
                    try {
                        value = findIn(
                            field.getType(),
                            injector,
                            instances,
                            field.isAnnotationPresent(Inject.class)
                        );
                    } catch (final IllegalArgumentException ex) {
                        throw new IllegalArgumentException(String.format(
                            "The injectable class %s has a member field '%s' " +
                            "of type %s that could not be resolved in the " +
                            "Injector. You might be missing a required " +
                            "InjectBundle or Component. If the field should " +
                            "not be injected when it doesn't exist, try " +
                            "solving the problem by removing the " +
                            "@Inject-annotation from the field and pass it " +
                            "through an @Inject-annotated constructor " +
                            "instead, or change the annotation on the field " +
                            "to @InjectOrNull.",
                            instance.getClass().getSimpleName(),
                            field.getName(),
                            field.getType().getSimpleName()
                        ), ex);
                    }
                }

                field.setAccessible(true);

                try {
                    field.set(instance, value);
                } catch (final IllegalAccessException ex) {
                    throw new InjectorException(
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
                            false
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
                                    if (!exec.invoke(instance, classMapper)) {
                                        switch (exec.getMissingArgumentStrategy()) {
                                            case THROW_EXCEPTION: {
                                                throw new InjectorException(format(
                                                    "The injector could not invoke the method '%s' " +
                                                    "before state '%s' since one of the parameters is not available.",
                                                    exec.getName(), exec.getState()));
                                            }
                                            case SKIP_INVOCATION:
                                                if (LOGGER_INSTANCE.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
                                                    LOGGER_INSTANCE.debug(
                                                        "|      %-74s |",
                                                        limit("(Not invoked due to missing optional dependencies.)", 74)
                                                    );
                                                }
                                                break;
                                        }
                                    }
                                } catch (final IllegalAccessException 
                                             | IllegalArgumentException 
                                             | InvocationTargetException ex) {

                                    LOGGER_INSTANCE.error("Exception thrown by method invoked by Injector:");
                                    if (ex.getCause() != null) {
                                        LOGGER_INSTANCE.error("Exception: " + ex.getCause().getClass().getSimpleName());
                                    }
                                    LOGGER_INSTANCE.error("Class: " + exec.getType().getName());
                                    LOGGER_INSTANCE.error("    @ExecuteBefore(" + exec.getState().name() + ")");

                                    if (exec instanceof ReflectionExecutionImpl) {
                                        final Method method = ((ReflectionExecutionImpl<?>) exec).getMethod();
                                        LOGGER_INSTANCE.error(format("    %s %s%s",
                                            method.getReturnType().getSimpleName(),
                                            method.getName(),
                                            method.getParameterCount() == 0 ? "()" : "("));

                                        if (method.getParameterCount() > 0) {
                                            Stream.of(method.getParameters())
                                                .map(param -> {
                                                    final Config config = param.getAnnotation(Config.class);
                                                    final WithState withState = param.getAnnotation(WithState.class);
                                                    if (config != null) {
                                                        return format("        @Config(name=\"%s\", value=\"%s\") %s",
                                                            config.name(),
                                                            config.value(),
                                                            param.getType().getSimpleName()
                                                        );
                                                    } else {

                                                        return format("        %s%s (%s)",
                                                            withState == null ? "" : format("@WithState(%s) ", withState.value().name()),
                                                            param.getType().getSimpleName(),
                                                            graph.getIfPresent(param.getType())
                                                                .map(node -> String.format(
                                                                    "Implemented as: %s, state: %s",
                                                                    node.getRepresentedType().getSimpleName(),
                                                                    node.getCurrentState().name()
                                                                ))
                                                                .orElse("No implementation found")
                                                        );
                                                    }
                                                })
                                                .forEachOrdered(LOGGER_INSTANCE::error);
                                            LOGGER_INSTANCE.error("    );");
                                        }
                                    }

                                    throw new InjectorException(ex);
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