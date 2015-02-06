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
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.util.Beans;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
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

    private boolean enabled;
    private String name;
    private P parent;
    private final Map<String, C> children;
    private final Set<ConfigParameter<?>> configParameters;
    private final AtomicInteger namingSequence;

    public AbstractConfigEntity() {
        children = new ConcurrentSkipListMap<>();
        configParameters = new ConcurrentSkipListSet<>();
        namingSequence = new AtomicInteger(1);
        setDefaults();
    }

    protected void setDefaults() {
        setEnabled(true);
        setName(getBaseName() + "_" + Integer.toString(namingSequence.getAndIncrement()));
    }

    protected CharSequence getBaseName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public T setEnabled(boolean enabled) {
        return with(enabled, e -> this.enabled = e);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T setName(CharSequence name) {
        return with(name, n -> this.name = Objects.requireNonNull(n).toString());
    }

    @Override
    public Optional<P> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public T setParent(P parent) {
        return with(parent, p -> this.parent = p);
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
        getParent().ifPresent(p -> {
            throw new IllegalStateException("It is illegal to add a child that already has a parent. child=" + child + ", parent=" + p);
        });
        //addParentToChild((ConfigEntity<P, ?, C>) (ConfigEntity) this, (ConfigEntity<C, P, ?>) (ConfigEntity) child);
        castAndSetParent(child);
        /*
         addParentToChild(this, child);
         @SuppressWarnings("unchecked")
         //final DBEntity<DBEntity<P, C>, ?> _child = (DBEntity<DBEntity<P, C>, ?>) child;
         final ConfigEntity<?, T, ?> _child2 = (ConfigEntity<?, T, ?>) child;
         final final ConfigEntity<?, ConfigEntity<? super T, ? super P, ? super C>, ?> _child = (ConfigEntity<?, ?, ?>) child;
         _child2.setParent(this);
         addParentToChild(parent, _child2);*/
        return with(child, c -> children.put(makeKey(c), child));
    }

    @Override
    public T remove(C child) {
        if (getParent().isPresent() && getParent().get() == this) {
            setParent(null);
        }
        return with(child, children::remove);
    }

    @Override
    public boolean contains(C child) {
        return children.containsKey(makeKey(child));
    }

    @Override
    public Stream<? extends C> childrenStream() {
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

    @Override
    public int compareTo(T o) {
        return getName().compareTo(o.getName());
    }

}
