package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.aspects.Childable;
import com.speedment.orm.config.model.aspects.Nameable;
import com.speedment.orm.config.model.aspects.Ordinable;
import com.speedment.orm.config.model.aspects.Parentable;
import com.speedment.util.java.JavaLanguage;
import com.speedment.util.stream.StreamUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class ChildHolder<C extends Parentable> {

    private final Map<Class, Map<String, C>> children;
    private final Map<Class, AtomicInteger> ordinalNumbers;
    private final Map<Class, AtomicInteger> nameNumbers;
    
    private final static Comparator<Class> CLASS_COMPARATOR = (a, b) -> 
        Objects.compare(a.getName(), b.getName(), Comparator.naturalOrder());
    
    public ChildHolder() {
        this (CLASS_COMPARATOR);
    }
    
    public ChildHolder(Comparator<Class> comparator) {
        children       = new ConcurrentSkipListMap<>(comparator);
        ordinalNumbers = new ConcurrentHashMap<>();
        nameNumbers    = new ConcurrentHashMap<>();
    }

    public long size() {
        return stream().count();
    }

    public boolean isEmpty() {
        return stream().findAny().isPresent();
    }

    public Optional<C> get(Class<C> childClass, String name) {
        return Optional.of(ensureMap(childClass).get(name));
    }

    public Optional<C> put(Childable parent, C child) {
        child.getParent().ifPresent(c -> {
            throw new IllegalStateException(
                "It is illegal to add a child that already has a parent. child="
                + child + ", parent=" + child.getParent().get()
            );
        });

        child.setParent(parent);

        Optional.of(child)
            .filter(c -> c.isOrdinable())
            .map(c -> (Ordinable) c)
            .filter(o -> o.getOrdinalPosition() != Ordinable.UNSET)
            .ifPresent(o -> {
                o.setOrdinalPosition(ordinalNumbers.computeIfAbsent(
                        child.getInterfaceMainClass(),
                        m -> new AtomicInteger(Ordinable.ORDINAL_FIRST)
                    ).getAndIncrement());
            });
        
        Optional.of(child)
            .filter(c -> !c.hasName())
            .ifPresent(c -> c.setName(
                JavaLanguage.toUnderscoreSeparated(
                    c.getInterfaceMainClass().getSimpleName()) + "_" + 
                    nameNumbers.computeIfAbsent(
                        child.getInterfaceMainClass(), 
                        m -> new AtomicInteger(Nameable.NAMEABLE_FIRST)
                    ).getAndIncrement()
            ));

        return Optional.ofNullable(children.computeIfAbsent(
            child.getInterfaceMainClass(),
            m -> new ConcurrentHashMap<>()
        ).put(child.getName(), child));
    }

    public Optional<C> remove(Childable parent, C child) {
        if (child.getParent().filter(p -> p == parent).isPresent()) {
            child.setParent(null);
        }
        
        return Optional.ofNullable(children.get(child.getClass()))
            .map(m -> m.remove(child.getName()));
    }

    public boolean contains(C child) {
        return ensureMap(child.getInterfaceMainClass())
            .containsKey(child.getName());
    }

    public Stream<C> stream() {
        return StreamUtil.of(children.values())
            .flatMap(i -> i.values().stream());
    }

    public <T extends C> Stream<T> streamOf(Class<T> clazz) {
        return children.getOrDefault(clazz, Collections.emptyMap())
            .values().stream().map(c -> (T) c);
    }

    private Map<String, C> ensureMap(Class<C> childClass) {
        return children.getOrDefault(
            childClass,
            Collections.emptyMap()
        );
    }
}
