/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.internal.dependency.DependencyGraph;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import com.speedment.common.injector.internal.dependency.impl.DependencyGraphImpl;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.internal.util.ReflectionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Optional;
import java.util.Properties;
import com.speedment.common.injector.annotation.IncludeInjectable;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import java.util.Arrays;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * The default implementation of the {@link Injector} interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class InjectorImpl implements Injector {
    
    private final static Logger LOGGER = LoggerManager.getLogger(Injector.class);
    
    private final static State[] STATES = State.values();
    private final Set<Class<?>> injectables;
    private final List<Object> instances;
    private final Injector.Builder builder;
    
    private InjectorImpl(Set<Class<?>> injectables, List<Object> instances, Injector.Builder builder) {
        this.injectables = requireNonNull(injectables);
        this.instances   = requireNonNull(instances);
        this.builder     = requireNonNull(builder);
    }
    
    @Override
    public <T> T getOrThrow(Class<T> type) throws IllegalArgumentException {
        return findIn(type, true);
    }
    
    @Override
    public <T> Optional<T> get(Class<T> type) {
        return Optional.ofNullable(findIn(type, false));
    }

    @Override
    public <T> T inject(T instance) {
        injectFields(instance);
        return instance;
    }

    @Override
    public void stop() {
        final DependencyGraph graph = DependencyGraphImpl.create(injectables);
        
        final AtomicBoolean hasAnythingChanged = new AtomicBoolean();

        // Loop until all nodes have been started.
        Set<DependencyNode> unfinished;
        while (!(unfinished = graph.nodes()
                .filter(n -> n.getCurrentState() != State.STOPPED)
                .collect(toSet())).isEmpty()) {

            hasAnythingChanged.set(false);

            unfinished.stream()
                .forEach(n -> {

                    // Check if all its dependencies have been satisfied.
                    // TODO: Dependencies should be resolved in the opposite order when stopping.
                    if (n.canBe(State.STOPPED)) { 

                        printLine();

                        // Retreive the instance for that node
                        final Object instance = findIn(n.getRepresentedType(), true);

                        // Execute all the executions for the next step.
                        n.getExecutions().stream()
                            .filter(e -> e.getState() == State.STOPPED)
                            .map(Execution::getMethod)
                            .forEach(m -> {
                                final Object[] params = Stream.of(m.getParameters())
                                    .map(p -> findIn(p.getType(), p.getAnnotation(Inject.class).required()))
                                    .toArray(Object[]::new);

                                m.setAccessible(true);

                                final String shortMethodName = 
                                    n.getRepresentedType().getSimpleName() + "#" + 
                                    m.getName() + "(" + 
                                    Stream.of(m.getParameters())
                                        .map(p -> p.getType().getSimpleName().substring(0, 1))
                                        .collect(joining(", ")) + ")";

                                LOGGER.debug(String.format("| -> %-76s |", shortMethodName));

                                try {
                                    m.invoke(instance, params);
                                } catch (final IllegalAccessException 
                                             | IllegalArgumentException 
                                             | InvocationTargetException ex) {

                                    throw new RuntimeException(ex);
                                }
                            });

                        // Update its state to the new state.
                        n.setState(State.STOPPED);
                        hasAnythingChanged.set(true);

                        LOGGER.debug(String.format(
                            "| %-66s %12s |", 
                            n.getRepresentedType().getSimpleName(), 
                            State.STOPPED.name()
                        ));
                    }
                });

            if (!hasAnythingChanged.get()) {
                throw new IllegalStateException(
                    "Injector appears to be stuck in an infinite loop. The following componenets have not been stopped: " + 
                        unfinished.stream()
                            .map(DependencyNode::getRepresentedType)
                            .map(Class::getSimpleName)
                            .collect(toSet())
                );
            }
        }
    }

    @Override
    public Injector.Builder newBuilder() {
        return builder;
    }
    
    private <T> T findIn(Class<T> type, boolean required) {
        return findIn(this, type, instances, required);
    }
    
    private static <T> T findIn(Injector injector, Class<T> type, List<Object> instances, boolean required) {
        if (Injector.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            final T casted = (T) injector;
            return casted;
        }
        
        for (final Object inst : instances) {
            if (type.isAssignableFrom(inst.getClass())) {
                return type.cast(inst);
            }
        }

        if (required) {
            throw new IllegalArgumentException(
                "Could not find any installed implementation of " + type.getName() + "."
            );
        } else return null;
    }
    
    private static void printLine() {
        LOGGER.debug("+---------------------------------------------------------------------------------+");
    }
    
    private <T> void injectFields(T instance) {
        requireNonNull(instance);
        
        final Set<Field> fields = traverseFields(instance.getClass())
            .filter(f -> f.isAnnotationPresent(Inject.class))
            .collect(toSet());

        for (final Field field : fields) {
            final Object value;
            
            if (Injector.class.isAssignableFrom(field.getType())) {
                value = this;
            } else {
                value = findIn(field.getType(), field.getAnnotation(Inject.class).required());
            }

            field.setAccessible(true);

            try {
                field.set(instance, value);
            } catch (final IllegalAccessException ex) {
                final String err = "Could not access field '" + field.getName() + 
                    "' in class '" + value.getClass().getName() + 
                    "' of type '" + field.getType() + "'.";
                LOGGER.error(ex, err);
                throw new RuntimeException(err, ex);
            }
        }
    }
    
    public static Injector.Builder builder() {
        return new Builder();
    }
    
    private final static class Builder implements Injector.Builder {
        
        private final Set<Class<?>> injectables;
        private final Map<String, String> overriddenParams;
        private Path configFileLocation;
        
        private Builder() {
            this(Collections.emptySet());
        }
        
        private Builder(Set<Class<?>> injectables) {
            requireNonNull(injectables);
            this.injectables = new LinkedHashSet<>(injectables);
            this.overriddenParams = new HashMap<>();
            this.configFileLocation = Paths.get("settings.properties");
        }

        @Override
        public Builder canInject(Class<?>... injectableTypes) {
            requireNonNull(injectableTypes);
            
            Stream.of(injectableTypes)
                .forEach(type -> {
                    LOGGER.warn("CAN INJECT: " + type.getSimpleName());
                    
                    if (type.getSimpleName().equals("OffHeapReadOnlyCacheStreamSupplierComponentImpl")) {
                        LOGGER.warn("FOUND IT!");
                        final List<Class<?>> supers = ReflectionUtil.traverseAncestors(type).collect(toList());
                        LOGGER.warn("Parents: " + supers);
                        final List<Class<?>> afterFilter = ReflectionUtil.traverseAncestors(type)
                            .peek(t -> LOGGER.warn("Does " + t.getSimpleName() + " have annotation? " + Arrays.asList(t.getAnnotations())))
                            .filter(t -> t.isAnnotationPresent(IncludeInjectable.class))
                            .collect(toList());
                        LOGGER.warn("Parents: " + afterFilter);
                    }
                    
                    injectables.add(type);
                    
                    ReflectionUtil.traverseAncestors(type)
                        .filter(t -> t.isAnnotationPresent(IncludeInjectable.class))
                        .map(t -> t.getAnnotation(IncludeInjectable.class))
                        .map(IncludeInjectable::value)
                        .forEach(this::canInject);
                });
            
            return this;
        }

        @Override
        public Builder withConfigFileLocation(Path configFile) {
            this.configFileLocation = requireNonNull(configFile);
            return this;
        }

        @Override
        public Builder withParam(String key, String value) {
            overriddenParams.put(key, value);
            return this;
        }

        @Override
        public Injector build() throws InstantiationException, NoDefaultConstructorException {
            // Load settings
            final File configFile = configFileLocation.toFile();
            final Properties properties = loadProperties(configFile);
            overriddenParams.forEach(properties::setProperty);
            
            final DependencyGraph graph = DependencyGraphImpl.create(injectables);
            final LinkedList<Object> instances = new LinkedList<>();
            
            // Create an instance of every injectable type
            for (final Class<?> injectable : injectables) {
                final Object instance = newInstance(injectable, properties);
                instances.addFirst(instance);
            }
            
            // Build the Injector
            final Injector injector = new InjectorImpl(
                unmodifiableSet(new LinkedHashSet<>(injectables)),
                unmodifiableList(instances),
                this
            );
            
            // Set the auto-injected fields
            instances.forEach(instance -> {
                final Set<Field> fields = traverseFields(instance.getClass())
                    .filter(f -> f.isAnnotationPresent(Inject.class))
                    .collect(toSet());

                for (final Field field : fields) {
                    final Object value;
                    
                    if (Inject.class.isAssignableFrom(field.getType())) {
                        value = injector;
                    } else {
                        value = findIn(injector, field.getType(), instances, field.getAnnotation(Inject.class).required());
                    }

                    field.setAccessible(true);

                    try {
                        field.set(instance, value);
                    } catch (final IllegalAccessException ex) {
                        throw new RuntimeException(
                            "Could not access field '" + field.getName() + 
                            "' in class '" + value.getClass().getName() + 
                            "' of type '" + field.getType() + 
                            "'.", ex
                        );
                    }
                }
            });
            
            final AtomicBoolean hasAnythingChanged = new AtomicBoolean();

            // Loop until all nodes have been started.
            Set<DependencyNode> unfinished;
            while (!(unfinished = graph.nodes()
                    .filter(n -> n.getCurrentState() != State.STARTED)
                    .collect(toSet())).isEmpty()) {
                
                hasAnythingChanged.set(false);

                unfinished.stream()
                    .forEach(n -> {
                        // Determine the next state of this node.
                        final State state = STATES[n.getCurrentState().ordinal() + 1];
                        
                        // Check if all its dependencies have been satisfied.
                        if (n.canBe(state)) {
                            
                            printLine();
                            
                            // Retreive the instance for that node
                            final Object instance = findIn(injector, n.getRepresentedType(), instances, true);

                            // Execute all the executions for the next step.
                            n.getExecutions().stream()
                                .filter(e -> e.getState() == state)
                                .map(Execution::getMethod)
                                .forEach(m -> {
                                    final Object[] params = Stream.of(m.getParameters())
                                        .map(p -> findIn(injector, p.getType(), instances, p.getAnnotation(Inject.class).required()))
                                        .toArray(Object[]::new);

                                    m.setAccessible(true);
                                    
                                    final String shortMethodName = 
                                        n.getRepresentedType().getSimpleName() + "#" + 
                                        m.getName() + "(" + 
                                        Stream.of(m.getParameters())
                                            .map(p -> p.getType().getSimpleName().substring(0, 1))
                                            .collect(joining(", ")) + ")";
                                    
                                    LOGGER.debug(String.format("| -> %-76s |", shortMethodName));

                                    try {
                                        m.invoke(instance, params);
                                    } catch (final IllegalAccessException 
                                                 | IllegalArgumentException 
                                                 | InvocationTargetException ex) {

                                        throw new RuntimeException(ex);
                                    }
                                });

                            // Update its state to the new state.
                            n.setState(state);
                            hasAnythingChanged.set(true);

                            LOGGER.debug(String.format(
                                "| %-66s %12s |", 
                                n.getRepresentedType().getSimpleName(), 
                                state.name()
                            ));
                        }
                    });
                
                if (!hasAnythingChanged.get()) {
                    throw new IllegalStateException(
                        "Injector appears to be stuck in an infinite loop."
                    );
                }
            }
            
            printLine();
            LOGGER.debug(String.format(
                "| %-79s |", 
                "All " + instances.size() + " components have been configured!"
            ));
            printLine();
            
            return injector;
        }
        
        private static Properties loadProperties(File configFile) {
            final Properties properties = new Properties();
            if (configFile.exists() && configFile.canRead()) {
                
                try (final InputStream in = new FileInputStream(configFile)) {
                    properties.load(in);
                } catch (final IOException ex) {
                    final String err = "Error loading default settings from " + 
                        configFile.getAbsolutePath() + "-file.";
                    LOGGER.error(ex, err);
                    throw new RuntimeException(err, ex);
                }
            } else {
                LOGGER.info(
                    "No configuration file '" + 
                    configFile.getAbsolutePath() + "' found."
                );
            }
            
            return properties;
        }
        
        private static <T> T newInstance(Class<T> type, Properties properties) throws InstantiationException, NoDefaultConstructorException {
            try {
                final Constructor<T> constr = type.getDeclaredConstructor();
                constr.setAccessible(true);
                final T instance = constr.newInstance();
                
                traverseFields(type)
                    .filter(f -> f.isAnnotationPresent(Config.class))
                    .forEach(f -> {
                        final Config config = f.getAnnotation(Config.class);
                        
                        final String serialized;
                        if (properties.containsKey(config.name())) {
                            serialized = properties.getProperty(config.name());
                        } else {
                            serialized = config.value();
                        }
                        
                        f.setAccessible(true);
                        
                        try {
                            if (Boolean.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Boolean.parseBoolean(serialized));
                            } else if (Byte.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Byte.parseByte(serialized));
                            } else if (Short.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Short.parseShort(serialized));
                            } else if (Integer.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Integer.parseInt(serialized));
                            } else if (Long.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Long.parseLong(serialized));
                            } else if (Float.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Float.parseFloat(serialized));
                            } else if (Double.class.isAssignableFrom(f.getType())) {
                                f.set(instance, Double.parseDouble(serialized));
                            } else if (String.class.isAssignableFrom(f.getType())) {
                                f.set(instance, serialized);
                            } else if (Character.class.isAssignableFrom(f.getType())) {
                                if (serialized.length() == 1) {
                                    f.set(instance, serialized.charAt(0));
                                } else {
                                    throw new IllegalArgumentException(
                                        "Value '" + serialized + 
                                        "' is to long to be parsed into a field of type '" + 
                                        f.getType().getName() + "'."
                                    );
                                }
                            } else if (File.class.isAssignableFrom(f.getType())) {
                                f.set(instance, new File(serialized));
                            } else if (URL.class.isAssignableFrom(f.getType())) {
                                try {
                                    f.set(instance, new URL(serialized));
                                } catch (final MalformedURLException ex) {
                                    throw new IllegalArgumentException(
                                        "Specified URL '" + serialized + "' is malformed.", ex
                                    );
                                }
                            }
                        } catch (final IllegalAccessException | IllegalArgumentException ex) {
                            throw new RuntimeException(
                                "Failed to set config parameter '" + config.name() + 
                                "' in class '" + type.getName() + "'.", ex
                            );
                        }
                    });
                
                return instance;
                
            } catch (final NoSuchMethodException ex) {
                throw new NoDefaultConstructorException(
                    "Could not find any default constructor for class '" + type.getName() + "'.", ex
                );
                
            } catch (final IllegalAccessException 
                         | IllegalArgumentException 
                         | InvocationTargetException ex) {
                
                throw new RuntimeException(ex);
            }
        }
    }
}