package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.injector.internal.dependency.DependencyGraph;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.util.ClassMap;
import com.speedment.common.injector.platform.State;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static java.util.Collections.newSetFromMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * The default implementation of the {@link DependencyGraph} interface.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class DependencyGraphImpl implements DependencyGraph {
    
    private final Map<Class<?>, DependencyNode> nodes;
    private final Set<Class<?>> stack;
    
    private DependencyGraphImpl() {
        nodes = new ConcurrentHashMap<>();
        stack = newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public DependencyNode getOrCreate(Class<?> clazz) {
        if (stack.add(clazz)) {
            try {
                return nodes.computeIfAbsent(clazz, c -> {
                    final DependencyNode node = new DependencyNodeImpl(clazz);

                    traverseFields(clazz)
                        .filter(f -> f.isAnnotationPresent(Inject.class))
                        .forEach(f -> {
                            final Inject inject = f.getAnnotation(Inject.class);
                            final Class<?> type = f.getType();
                            final State state   = inject.value();

                            try {
                                node.getDependencies().add(
                                    new DependencyImpl(getOrCreate(type), state)
                                );
                            } catch (final CyclicReferenceException ex) {
                                throw new CyclicReferenceException(clazz, ex);
                            }
                        });

                    nodes.put(clazz, node);
                    return node;
                });
            } finally {
                stack.remove(clazz);
            }
        } else {
            throw new CyclicReferenceException(clazz);
        }
    }

    @Override
    public Stream<DependencyNode> nodes() {
        return nodes.values().stream();
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
}
