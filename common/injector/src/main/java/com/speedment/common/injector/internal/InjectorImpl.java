package com.speedment.common.injector.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.Injectable;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.platform.State;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.util.Collections.unmodifiableSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import com.speedment.common.injector.annotation.RequireState;
import com.speedment.common.injector.annotation.ResultingState;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Injector} interface.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class InjectorImpl implements Injector {
    
    private final Set<Class<?>> injectables; // Immutable
    private final Map<Class<?>, Component> instances;
    
    public static Injector.Builder builder() {
        return new Builder();
    }
    
    private InjectorImpl(Set<Class<?>> injectables) {
        this.injectables = requireNonNull(injectables);
        this.instances   = new ConcurrentHashMap<>();
    }
    
    @Override
    public Object newInstance(String className) throws 
            IllegalStateException, 
            ClassNotFoundException {
        
        final Class<?> clazz = getClass().getClassLoader().loadClass(className);
        return newInstance(clazz);
    }

    @Override
    public <T> T newInstance(Class<T> type) throws 
            NoDefaultConstructorException, 
            IllegalStateException {
        
        try {
            final Constructor<T> constr = type.getDeclaredConstructor();
            constr.setAccessible(true);
            final T instance = constr.newInstance();
            
            // Set all fields with the @Inject-annotation
            traverseFields(type)
                .filter(f -> f.isAnnotationPresent(Inject.class))
                .forEach(f -> {
                    final Inject inject     = f.getAnnotation(Inject.class);
                    final Class<?> injected = f.getType();
                    final State state       = inject.value();
                    
                    final Object value = getOrThrow(injected, state);
                    
                    f.setAccessible(true);
                    
                    try {
                        f.set(instance, value);
                    } catch (final IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            
            return instance;
        } catch (final NoSuchMethodException ex) {
            throw new NoDefaultConstructorException(
                "A default constructor is required to instantiate '" + 
                type.getName() + "'.", ex
            );
        } catch (final InstantiationException 
                     | IllegalAccessException 
                     | InvocationTargetException ex) {
            
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> T getOrThrow(Class<T> type, State state) throws 
            NullPointerException, 
            IllegalArgumentException,
            NoDefaultConstructorException, 
            IllegalStateException {
        
        final Class<? extends T> injectable = injectables.stream()
            .filter(type::isAssignableFrom)
            .map(c -> (Class<? extends T>) c)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find any installed injectable that implements '" + 
                type.getName() + "'."
            ));
        
        // TODO: What happends when this recurses?
        @SuppressWarnings("unchecked")
        final Component entry = instances.computeIfAbsent(injectable, this::newEntry);
        entry.ensureState(state);
        
        @SuppressWarnings("unchecked")
        final T instance = (T) entry.getInstance();
        return instance;
    }
    
    private <T> Component newEntry(Class<T> type) throws
            NoDefaultConstructorException, 
            IllegalStateException {
        return new Component(newInstance(type));
    }

    private static Stream<Field> traverseFields(Class<?> clazz) {
        final Class<?> parent = clazz.getSuperclass();
        final Stream<Field> inherited;
        
        if (parent != null) {
            inherited = traverseFields(parent);
        } else {
            inherited = Stream.empty();
        }
        
        return Stream.concat(inherited, Stream.of(clazz.getDeclaredFields()));
    }
    
    private static Stream<Method> traverseMethods(Class<?> clazz) {
        final Class<?> parent = clazz.getSuperclass();
        final Stream<Method> inherited;
        
        if (parent != null) {
            inherited = traverseMethods(parent);
        } else {
            inherited = Stream.empty();
        }
        
        return Stream.concat(inherited, Stream.of(clazz.getDeclaredMethods()));
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
        public Injector build() {
            return new InjectorImpl(unmodifiableSet(injectables));
        }
    }
    
    private final static class Component {
        
        private final Object instance;
        private State currentState;
        
        private Component(Object instantiated) {
            this.currentState = State.CREATED;
            this.instance = requireNonNull(instantiated);
        }
        
        private final static Comparator<Method>
            STATE_AFTER_COMPARATOR = comparing(m -> m.getAnnotation(ResultingState.class).value());
        
        private void ensureState(State desiredState) {
            if (currentState.ordinal() < desiredState.ordinal()) {
                traverseMethods(instance.getClass())
                    .filter(m -> 
                        !m.isAnnotationPresent(RequireState.class) 
                        || m.getAnnotation(RequireState.class)
                            .value().compareTo(currentState) <= 0
                    ).filter(m -> m.isAnnotationPresent(ResultingState.class))
                    .sorted(STATE_AFTER_COMPARATOR)
                    .forEachOrdered(m -> {
                        m.setAccessible(true);
                        
                        try {
                            m.invoke(instance);
                        } catch (final IllegalAccessException 
                                     | IllegalArgumentException 
                                     | InvocationTargetException ex) {
                            
                            throw new RuntimeException(ex);
                        }
                    });
                
                currentState = desiredState;
            }
        }
        
        Object getInstance() {
            return instance;
        }
    }
}
