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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import static java.util.Collections.unmodifiableList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * The default implementation of the {@link Injector} interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class InjectorImpl implements Injector {
    
    private final static State[] STATES = State.values();
    private final List<Object> instances;
    
    private InjectorImpl(List<Object> instances) {
        this.instances = requireNonNull(instances);
    }
    
    @Override
    public <T> T get(Class<T> type) throws IllegalArgumentException {
        return findIn(type, instances);
    }
    
    private static <T> T findIn(Class<T> type, List<Object> instances) {
        for (final Object inst : instances) {
            if (type.isAssignableFrom(inst.getClass())) {
                return type.cast(inst);
            }
        }

        throw new IllegalArgumentException(
            "Could not find any installed implementation of " + type.getName() + "."
        );
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
        public Builder canInject(Class<?>... injectableTypes) {
            requireNonNull(injectableTypes);
            Stream.of(injectableTypes).forEach(injectables::add);
            return this;
        }

        @Override
        public Injector build() throws InstantiationException, NoDefaultConstructorException {
            final DependencyGraph graph = DependencyGraphImpl.create(injectables);
            final LinkedList<Object> instances = new LinkedList<>();
            
            // Create an instance of every injectable type
            for (final Class<?> injectable : injectables) {
                instances.addFirst(newInstance(injectable));
            }
            
            // Set the auto-injected fields
            instances.forEach(instance -> {
                final Set<Field> fields = traverseFields(instance.getClass())
                    .filter(f -> f.isAnnotationPresent(Inject.class))
                    .collect(toSet());

                for (final Field field : fields) {
                    final Object value = findIn(field.getType(), instances);

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
                            final Object instance = findIn(n.getRepresentedType(), instances);

                            // Execute all the executions for the next step.
                            n.getExecutions().stream()
                                .filter(e -> e.getState() == state)
                                .map(Execution::getMethod)
                                .forEach(m -> {
                                    final Object[] params = Stream.of(m.getParameters())
                                        .map(p -> findIn(p.getType(), instances))
                                        .toArray(Object[]::new);

                                    m.setAccessible(true);
                                    
                                    final String shortMethodName = 
                                        n.getRepresentedType().getSimpleName() + "#" + 
                                        m.getName() + "(" + 
                                        Stream.of(m.getParameters())
                                            .map(p -> p.getType().getSimpleName().substring(0, 1))
                                            .collect(joining(", ")) + ")";
                                    
                                    System.out.printf("| -> %-76s |%n", shortMethodName);

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

                            System.out.printf("| %-66s %12s |%n", 
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
            
            printLine();
            System.out.printf("| %-79s |%n", "All " + instances.size() + " components have been configured!");
            printLine();
            
            return new InjectorImpl(unmodifiableList(instances));
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
        
        private static void printLine() {
            System.out.println("+---------------------------------------------------------------------------------+");
        }
    }
}