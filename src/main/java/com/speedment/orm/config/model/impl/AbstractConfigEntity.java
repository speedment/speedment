package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.ConfigParameter;
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.util.Beans;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
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
public abstract class AbstractConfigEntity
        <T extends ConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>>
        implements ConfigEntity<T, P, C> {

    private String name;
    private P parent;
    private final Map<String, C> children;
    private final Set<ConfigParameter<?>> configParameters;

    public AbstractConfigEntity() {
        children = new ConcurrentSkipListMap<>();
        configParameters = new ConcurrentSkipListSet<>();
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

    protected String makeKey(C child) {
        return child.getName();
    }

    // Children
    @Override
    public T add(C child) {
        return with(child, c -> children.put(makeKey(c), child));
    }

    @Override
    public T remove(C child) {
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
