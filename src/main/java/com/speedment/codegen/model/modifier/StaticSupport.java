package com.speedment.codegen.model.modifier;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class StaticSupport<T extends Modifier_> {

    private final T[] values;
    private final Map<String, T> nameMap;

    public StaticSupport(T[] values) {
        this.values = values;
        this.nameMap = Stream.of(values).collect(Collectors.toMap((m) -> m.name().toLowerCase(), (m) -> m));
    }

    public T byName(final String name) {
        return nameMap.get(name.toLowerCase());
    }

    public Set<T> byText(final String text) {
        final String[] tokens = text.split("\\s+");
        return Stream.of(tokens).filter((token) -> byName(token) != null).map((token) -> byName(token)).collect(Collectors.toSet());
    }

    public Set<T> byCode(final int code) {
        return Stream.of(values).filter((cm) -> Modifier_.valuesContains(code, cm.getValue())).collect(Collectors.toSet());
    }

    public Set<T> of(final T... classModifiers) {
        return Stream.of(classModifiers).collect(Collectors.toSet());
    }

}
