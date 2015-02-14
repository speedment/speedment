/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.ConfigParameter;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.External;
import com.speedment.orm.config.model.OrdinalConfigEntity;
import com.speedment.util.Beans;
import static com.speedment.util.Beans.getterBeanPropertyNameAndValue;
import com.speedment.util.Trees;
import com.speedment.util.stream.CollectorUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 * @param <T>
 * @param <P>
 * @param <C>
 */
public abstract class AbstractConfigEntity<T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>>
        implements ConfigEntity<T, P, C> {

    private static final String NL = "\n";

    private boolean enabled;
    private String name;
    private P parent;
    private final Map<String, C> children;
    private final Set<ConfigParameter<?>> configParameters;
    private final AtomicInteger namingSequence;
    private final Map<Class<? extends ConfigEntity<?, ?, ?>>, AtomicInteger> childSequences;

    public AbstractConfigEntity() {
        children = new ConcurrentSkipListMap<>();
        configParameters = new ConcurrentSkipListSet<>();
        namingSequence = new AtomicInteger(ORDINAL_FIRST);
        childSequences = new ConcurrentHashMap<>();
        init();
    }

    protected void init() {
        setEnabled(true);
        setName(getBaseName() + "_" + Integer.toString(getNamingSequence().getAndIncrement()));
        setDefaults();
    }

    protected abstract void setDefaults();

    protected String getBaseName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public T setEnabled(boolean enabled) {
        return run(() -> this.enabled = enabled);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T setName(String name) {
        return run(() -> this.name = Objects.requireNonNull(name));
    }

    @Override
    public Optional<P> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public T setParent(P parent) {
        return run(() -> this.parent = parent);
    }

    private void castAndSetParent(final C child) {
        final String methodName = "setParent";
        try {
            // I finally got tired of fiddeling around with generics in three dimensions and
            // brought out the Bazooka in the shape of reflection... Erasure makes this work!
            // Todo: Fix this in a typesafe way with generics.
            //System.out.println(Stream.of(child.getClass().getMethods()).map(Method::getName).collect(Collectors.joining(", ")));

            final Method method = child.getClass().getMethod(methodName, ConfigEntity.class);
            method.setAccessible(true);
            method.invoke(child, this);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Unable to invoke " + methodName, ex);
        }
    }

    private static <P extends ConfigEntity<P, ?, C>, C extends ConfigEntity<C, P, ?>> void addParentToChild(P parent, C child) {
        child.setParent(parent);
    }

    protected String makeKey(C child) {
        return child.getName();
    }

    // Children
    @Override
    public T add(final C child) {
        child.getParent().ifPresent(p -> {
            throw new IllegalStateException("It is illegal to add a child that already has a parent. child=" + child + ", parent=" + p);
        });
        //addParentToChild((ConfigEntity<P, ?, C>) (ConfigEntity) this, (ConfigEntity<C, P, ?>) (ConfigEntity) child);
        castAndSetParent(child);
        if (child instanceof OrdinalConfigEntity) {
            @SuppressWarnings("cast")
            final OrdinalConfigEntity<?, T, ?> ordinalConfigEntity = (OrdinalConfigEntity<?, T, ?>) child;
            if (ordinalConfigEntity.getOrdinalPosition() == ORDINAL_UNSET) {
                ordinalConfigEntity.setOrdinalPosition(getChildSequence(child.getInterfaceMainClass()).getAndIncrement());
            }
        }
        /*
         addParentToChild(this, child);
         @SuppressWarnings("unchecked")
         //final DBEntity<DBEntity<P, C>, ?> _child = (DBEntity<DBEntity<P, C>, ?>) child;
         final ConfigEntity<?, T, ?> _child2 = (ConfigEntity<?, T, ?>) child;
         final final ConfigEntity<?, ConfigEntity<? super T, ? super P, ? super C>, ?> _child = (ConfigEntity<?, ?, ?>) child;
         _child2.setParent(this);
         addParentToChild(parent, _child2);*/
        return run(() -> children.put(makeKey(child), child));
    }

    @Override
    public T remove(C child) {
        if (child.getParent().isPresent() && child.getParent().get() == this) {
            setParent(null);
        }
        return run(() -> children.remove(makeKey(child)));
    }

    @Override
    public boolean contains(C child) {
        return children.containsKey(makeKey(child));
    }

    @Override
    public Stream<? extends C> stream() {
        return children.values().stream();
    }

    // Config
    @Override
    public <E> T add(ConfigParameter<? extends E> configParameter) {
        return with(configParameter, configParameters::add);
    }

    @Override
    public <E> T remove(ConfigParameter<? extends E> configParameter) {
        return with(configParameter, configParameters::remove);
    }

    @Override
    public <E> boolean contains(ConfigParameter<? extends E> configParameter) {
        return configParameters.contains(configParameter);
    }

    @Override
    public Stream<ConfigParameter<?>> configStream() {
        return configParameters.stream();
    }

    // Util methods
    @SuppressWarnings("unchecked")
    protected <P> T with(final P item, final Consumer<P> consumer) {
        return Beans.with((T) this, item, consumer);
    }

    @SuppressWarnings("unchecked")
    protected <P> T run(final Runnable runnable) {
        return Beans.run((T) this, runnable);
    }

    @Override
    public int compareTo(T o) {
        return getName().compareTo(o.getName());
    }

    protected static String makeNullSafeString(CharSequence charSequence) {
        return (charSequence == null) ? null : charSequence.toString();
    }

    protected AtomicInteger getNamingSequence() {
        return namingSequence;
    }

    protected AtomicInteger getChildSequence(Class<? extends ConfigEntity<?, ?, ?>> clazz) {
        return childSequences.computeIfAbsent(clazz, c -> new AtomicInteger(ORDINAL_FIRST));
    }

    protected Set<Method> getMethods(Predicate<Method> filter) {
        return addMethods(new HashSet<>(), getClass(), filter);
    }

    private static final Predicate<Method> METHOD_IS_PUBLIC = (m) -> Modifier.isPublic(m.getModifiers());
    private static final Predicate<Method> METHOD_IS_GETTER = (m) -> m.getParameterCount() == 0 && (m.getName().startsWith("get") || m.getName().startsWith("is"));
    private static final Predicate<Method> METHOD_IS_EXTERNAL = AbstractConfigEntity::isExternal;

    private static boolean isExternal(Method method) {
        return isExternal(method, method.getDeclaringClass());
    }

    private static boolean isExternal(final Method method, final Class<?> clazz) {
        if (method == null || clazz == null) {
            return false;
        }
        if (method.getAnnotation(External.class) != null) {
            return true;
        }
        // Also try the superClass and all the interfaces it implements
        final List<Class<?>> classCandidates = new ArrayList<>(Arrays.asList(clazz.getInterfaces()));
        final Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            classCandidates.add(superClass);
        }
        for (final Class<?> classCandidate : classCandidates) {
            try {
                if (isExternal(classCandidate.getMethod(method.getName(), method.getParameterTypes()), classCandidate)) {
                    return true;
                }
            } catch (NoSuchMethodException | SecurityException e) {
                // ignore
            }
        }
        return false;
    }

    private Set<Method> addMethods(Set<Method> methods, Class<?> clazz, Predicate<Method> filter) {
        if (clazz == Object.class) {
            return methods;
        }
        Stream.of(clazz.getDeclaredMethods())
                .filter(filter)
                .forEach(methods::add);
        addMethods(methods, clazz.getSuperclass(), filter); // Recursively add the superclass methods
        return methods;
    }

    private StringBuilder indent(StringBuilder sb, int indentLevel) {
        IntStream.range(0, indentLevel).forEach(i -> sb.append("    "));
        return sb;
    }

    @Override
    public String toGroovy(int indentLevel) {
        return CollectorUtil.of(StringBuilder::new, sb -> {
            getMethods(METHOD_IS_GETTER.and(METHOD_IS_PUBLIC).and(METHOD_IS_EXTERNAL))
                    .stream()
                    .sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
                    .forEach(m -> getterBeanPropertyNameAndValue(m, this)
                            .ifPresent(t -> indent(sb, indentLevel).append(t).append(NL)));

            stream().forEach(c -> {
                indent(sb, indentLevel).append(c.getInterfaceMainClass().getSimpleName()).append(" {").append(NL);
                sb.append(c.toGroovy(indentLevel + 1));
                indent(sb, indentLevel).append("}").append(NL);
            });
        }, StringBuilder::toString);
    }

}
