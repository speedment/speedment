package com.speedment.runtime.config.internal.resolver;

import com.speedment.runtime.config.resolver.DocumentResolver;
import com.speedment.runtime.config.resolver.ResolverException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.speedment.runtime.config.internal.resolver.ResolverUtil.deepCopy;
import static com.speedment.runtime.config.internal.resolver.ResolverUtil.isBasic;
import static com.speedment.runtime.config.internal.resolver.ResolverUtil.merge;
import static com.speedment.runtime.config.internal.resolver.ResolverUtil.reorder;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.7
 */
public class NewDocumentResolverImpl implements DocumentResolver {

    private final Function<String, Map<String, Object>> loader;

    public NewDocumentResolverImpl(Function<String, Map<String, Object>> loader) {
        this.loader = requireNonNull(loader);
    }

    public Map<String, Object> loadAndResolve(String resourceName) {
        return resolve(load(resourceName));
    }

    public Map<String, Object> load(String resourceName) {
        return loader.apply(resourceName);
    }

    public Map<String, Object> resolve(Map<String, Object> document) {
        return resolve(document, null);
    }

    private Map<String, Object> resolve(Map<String, Object> document, Object unresolvedPrototype) {
        final Map<String, Object> copy = new LinkedHashMap<>();

        // Copy over all the values from the prototype.
        if (unresolvedPrototype != null) {
            final Map<String, Object> prototype = resolveAny(unresolvedPrototype);
            prototype.forEach((key, value) -> copy.put(key, deepCopy(value)));
        }

        // Copy over all the values from the extended object.
        if (document.containsKey(EXTENDS)) {
            final Object unresolvedExtended = document.get(EXTENDS);
            final Map<String, Object> extended = resolveAny(unresolvedExtended);
            extended.forEach((key, value) -> {
                final Object oldValue = copy.get(key);
                if (oldValue == null) { // Already copied in resolve
                    copy.put(key, value);
                } else {
                    copy.put(key, merge(oldValue, value));
                }
            });
            copy.put(EXTENDS, unresolvedExtended);

        // Set the prototype as the extended object.
        } else {
            if (unresolvedPrototype != null) {
                copy.put(EXTENDS, unresolvedPrototype);
            }
        }

        // Copy over all the values in document (except extends)
        document.forEach((key, value) -> {
            if (!EXTENDS.equals(key)) {
                copy.put(key, merge(copy.get(key), value));
            }
        });

        final Object prototype = copy.get(PROTOTYPE);
        if (prototype != null) {
            final Object items = copy.get(ITEMS);
            if (items != null) {
                @SuppressWarnings("unchecked") // Must always be a list.
                final List<Object> itemsList = (List<Object>) items;
                itemsList.replaceAll(oldValue -> applyPrototype(oldValue, prototype));
            }
        }

        return reorder(copy);
    }

    private Map<String, Object> resolveAny(Object object) {
        if (object == null) return null;
        else if (object instanceof String) {
            final String resourceName = (String) object;
            return loadAndResolve(resourceName);
        } else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> document = (Map<String, Object>) object;
            return resolve(document);
        } else {
            throw new ResolverException(format(
                "Can't resolve unsupported type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    private Object applyPrototype(Object object, Object prototype) {
        if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            return resolve(map, prototype);
        } else {
            return object;
        }
    }

    @Override
    public Map<String, Object> normalize(Map<String, Object> document) {
        final Object extended = document.get(EXTENDS);
        if (extended == null) return document;

        final Map<String, Object> blank = new LinkedHashMap<>();
        blank.put(EXTENDS, extended);

        final Map<String, Object> diff = difference(blank, document);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put(EXTENDS, extended);

        diff.forEach((key, value) -> result.put(key, flattenAny(value)));
        return result;
    }

    @Override
    public Map<String, Object> difference(Map<String, Object> first, Map<String, Object> second) {
        final Map<String, Object> oldResolved  = resolve(first);
        final Map<String, Object> newResolved = resolve(second);
        return ResolverUtil.difference(oldResolved, newResolved);
    }

    private Object flattenAny(Object object) {
        if (object == null) return null;
        else if (isBasic(object)) return object;
        else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            return flatten(map);
        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) object;
            return flatten(list);
        } else {
            throw new ResolverException(format(
                "Can't flatten unsupported type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    private Object flatten(Map<String, Object> map) {
        if (map.size() == 1) {
            final Map.Entry<String, Object> entry = map.entrySet().iterator().next();
            if (entry.getKey().equals(ITEMS)) {
                return flattenAny(entry.getValue());
            }
        }

        final Map<String, Object> copy = new LinkedHashMap<>();
        map.forEach((key, value) -> copy.put(key, flattenAny(value)));
        return copy;
    }

    private Object flatten(List<Object> list) {
        final List<Object> copy = new ArrayList<>();
        for (final Object value : list) {
            copy.add(flattenAny(value));
        }
        return copy;
    }
}
