package com.speedment.common.injector.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.Injectable;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.internal.dependency.DependencyGraph;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import com.speedment.common.injector.internal.dependency.impl.DependencyGraphImpl;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseFields;
import com.speedment.common.injector.State;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import static java.util.Collections.unmodifiableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.util.stream.Collectors.joining;

/**
 * The default implementation of the {@link Injector} interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class InjectorImpl implements Injector {
    
    private final static State[] STATES = State.values();
    private final Map<Class<?>, Object> instances;
    
    private InjectorImpl(Map<Class<?>, Object> instances) {
        this.instances = unmodifiableMap(instances);
    }
    
    @Override
    public <T> T get(Class<T> type) throws IllegalArgumentException {
        @SuppressWarnings("unchecked")
        final T result = (T) instances.get(type);
        
        if (result == null) {
            throw new IllegalArgumentException(
                "No component of type '" + type + 
                "' have been installed in the injector."
            );
        }
        
        return result;
    }
    
    public static Injector.Builder builder() {
        return new Builder();
    }
    
    private final static class Builder implements Injector.Builder {
        
        private final Set<Class<?>> injectables;
        
        private Builder() {
            injectables = new HashSet<>();
        }

        @Override
        public Builder canInject(Class<?> injectableType) {
            requireNonNull(injectableType);
            
            if (!injectableType.isAnnotationPresent(Injectable.class)) {
                throw new IllegalArgumentException(
                    "The specified class '" + injectableType.getName() + 
                    "' does not have the " + Injectable.class.getSimpleName() + "-annotation."
                );
            }
            
            injectables.add(injectableType);
            return this;
        }

        @Override
        public Injector build() throws InstantiationException {
            final DependencyGraph graph = DependencyGraphImpl.create(injectables);
            final Map<Class<?>, Object> instances = new HashMap<>();
            
            // Create an instance of every injectable type
            for (final Class<?> injectable : injectables) {
                instances.put(injectable, newInstance(injectable));
            }
            
            // Set the auto-injected fields
            instances.forEach((clazz, instance) -> {
                final Set<Field> fields = traverseFields(clazz)
                    .filter(f -> f.isAnnotationPresent(Inject.class))
                    .collect(toSet());

                for (final Field field : fields) {
                    final Object value = instances.get(field.getType());

                    if (value == null) {
                        throw new UnsupportedOperationException(
                            "A field '" + field.getName() + 
                            "' in class '" + clazz.getName() + 
                            "' of type '" + field.getType() + 
                            "' was annoted with '@" + Inject.class.getSimpleName() + 
                            "' but it was not listed as an injectable."
                        );
                    }

                    field.setAccessible(true);

                    try {
                        field.set(instance, value);
                    } catch (final IllegalAccessException ex) {
                        throw new RuntimeException(
                            "Could not access field '" + field.getName() + 
                            "' in class '" + clazz.getName() + 
                            "' of type '" + field.getType() + 
                            "'.", ex
                        );
                    }
                }
            });
            
            System.out.println("Configuring platform.");
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
                            
                            // Retreive the instance for that node
                            final Object instance = instances.get(n.getRepresentedType());

                            // Execute all the executions for the next step.
                            n.getExecutions().stream()
                                .filter(e -> e.getState() == state)
                                .map(Execution::getMethod)
                                .forEach(m -> {
                                    final Object[] params = Stream.of(m.getParameters())
                                        .map(p -> instances.get(p.getType()))
                                        .toArray(Object[]::new);

                                    m.setAccessible(true);
                                    System.out.println("... Executing " + n.getRepresentedType().getSimpleName() + "#" + m.getName() + "(" + Stream.of(m.getParameters()).map(p -> p.getType().getSimpleName().substring(0, 1)).collect(joining(", ")) + ")");

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

                            System.out.printf("%32s has been %s.", 
                                n.getRepresentedType().getSimpleName(), 
                                state.name()
                            );
                        }
                    });
                
                if (!hasAnythingChanged.get()) {
                    throw new IllegalStateException(
                        "Injector appears to be stuck in an infinite loop."
                    );
                }
            }
            
            System.out.println("All " + instances.size() + " components have been configured!");
            return new InjectorImpl(unmodifiableMap(instances));
        }
        
        private static <T> T newInstance(Class<T> type) throws InstantiationException, NoDefaultConstructorException {
            try {
                final Constructor<T> constr = type.getConstructor();
                constr.setAccessible(true);
                return constr.newInstance();
                
            } catch (final NoSuchMethodException ex) {
                throw new NoDefaultConstructorException(ex);
                
            } catch (final IllegalAccessException 
                         | IllegalArgumentException 
                         | InvocationTargetException ex) {
                
                throw new RuntimeException(ex);
            }
        }
    }
}