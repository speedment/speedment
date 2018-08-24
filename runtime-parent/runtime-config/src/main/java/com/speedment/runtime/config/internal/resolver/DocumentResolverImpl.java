package com.speedment.runtime.config.internal.resolver;

import com.speedment.runtime.config.resolver.DocumentResolver;
import com.speedment.runtime.config.resolver.ResolverException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link DocumentResolver}.
 *
 * @author Emil Forslund
 * @since  3.1.6
 */
public final class DocumentResolverImpl implements DocumentResolver {

    private final Function<String, Map<String, Object>> loader;

    public DocumentResolverImpl(Function<String, Map<String, Object>> loader) {
        this.loader = requireNonNull(loader);
    }

    @Override
    public Map<String, Object> loadAndResolve(String resourceName) {
        return resolve(loader.apply(resourceName));
    }

    @Override
    public Map<String, Object> resolve(Map<String, Object> document) {
        document = resolveExtends(document);
        document = expandPrototypes(document);
        return expandPrototypes(resolveExtends(document));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> normalize(Map<String, Object> document) {
        Map<String, Object> map = normalizePrototype(document);
        map = normalizeExtends(map);

        final Object flat = flatten(map);
        if (flat instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> flatMap = (Map<String, Object>) flat;
            return flatMap;
        } else { // Top level should be a map.
            final Map<String, Object> root = new LinkedHashMap<>();
            root.put(ITEMS, flat);
            return root;
        }
    }

    private Map<String, Object> normalizePrototype(Map<String, Object> document) {
        Map<String, Object> diff = copy(document);

        // Normalize prototypes
        final Object prototypeObj = document.get(PROTOTYPE);
        final Object itemsObj     = document.get(ITEMS);
        if (prototypeObj != null && itemsObj != null) {

            final List<Object> items;
            if (itemsObj instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> casted = (List<Object>) itemsObj;
                items = casted;
            } else {
                throw new ResolverException(format(
                    "Can't normalize where 'items' is of unsupported type " +
                    "'%s'.", itemsObj.getClass().getName()
                ));
            }

            final Map<String, Object> prototype;
            if (prototypeObj instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> casted = (Map<String, Object>) prototypeObj;
                prototype = casted;

            } else if (prototypeObj instanceof String) {
                prototype = loadAndResolve((String) prototypeObj);

            } else {
                throw new ResolverException(format(
                    "Can't normalize where 'prototype' is of unsupported " +
                    "type '%s'.", prototypeObj.getClass().getName()));
            }

            final List<Object> normalizedItems = new ArrayList<>();
            for (final Object item : items) {
                if (item instanceof Map) {
                    @SuppressWarnings("unchecked")
                    final Map<String, Object> itemMap = (Map<String, Object>) item;
                    final Map<String, Object> norm = difference(prototype, itemMap);
                    if (norm == null) { // An empty map (no difference) is still a map
                        normalizedItems.add(new LinkedHashMap<>());
                    } else {
                        normalizedItems.add(norm);
                    }
                } else {
                    final Object norm = differenceAny(prototype, item);
                    if (norm != null) normalizedItems.add(norm);
                }
            }

            diff.put(ITEMS, normalizedItems);
        }

        final Map<String, Object> normalized = new LinkedHashMap<>();
        diff.forEach((key, value) -> {
            final Object n = normalizePrototypeAny(value);
            if (n != null) normalized.put(key, n);
        });

        return normalized;
    }

    private List<Object> normalizePrototype(List<Object> list) {
        final List<Object> normalized = new ArrayList<>();
        for (final Object obj : list) {
            normalized.add(normalizePrototypeAny(obj));
        }
        return normalized;
    }

    private Object normalizePrototypeAny(Object object) {
        if (object == null || isBasic(object)) {
            return object;

        } else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            return normalizePrototype(map);

        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) object;
            return normalizePrototype(list);

        } else {
            throw new ResolverException(format(
                "Can't normalize value of unsupported type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    private Map<String, Object> normalizeExtends(Map<String, Object> document) {
        final Object extendsObj = document.get(EXTENDS);

        final Map<String, Object> diff;
        if (extendsObj == null) {
            diff = copy(document);

        } else {
            final Map<String, Object> extendsMap;
            if (extendsObj instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> casted = (Map<String, Object>) extendsObj;
                extendsMap = resolve(casted);

            } else if (extendsObj instanceof String) {
                final String resName = (String) extendsObj;
                extendsMap = loadAndResolve(resName);

            } else {
                throw new ResolverException(format(
                    "Invalid extends value '%s' found when normalizing.",
                    extendsObj.getClass().getName()
                ));
            }

            diff = difference(extendsMap, document);
        }

        if (diff == null) return new LinkedHashMap<>();

        final Map<String, Object> normalized = new LinkedHashMap<>();
        diff.forEach((key, value) -> {
            final Object n = normalizeExtendsAny(value);
            if (n != null) normalized.put(key, n);
        });

        return normalized;
    }

    private List<Object> normalizeExtends(List<Object> list) {
        final List<Object> normalized = new ArrayList<>();
        for (final Object obj : list) {
            normalized.add(normalizeExtendsAny(obj));
        }
        return normalized;
    }

    private Object normalizeExtendsAny(Object object) {
        if (object == null || isBasic(object)) {
            return object;

        } else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            return normalizeExtends(map);

        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) object;
            return normalizeExtends(list);

        } else {
            throw new ResolverException(format(
                "Can't normalize value of unsupported type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    @Override
    public Map<String, Object> difference(Map<String, Object> first, Map<String, Object> second) {
        if (first == null || second == null) {
            return copy(second);
        } else if (first.equals(second)) {
            return null;
        }

        final Map<String, Object> copy = new LinkedHashMap<>();

        second.forEach((key, newValue) -> {
            if (newValue == null) return;
            final Object oldValue = first.get(key);
            final Object diff = differenceAny(oldValue, newValue);
            if (diff != null) {
                copy.put(key, diff);
            }
        });

        return copy;
    }

    private List<Object> difference(List<Object> first, List<Object> second) {
        if (first == null || second == null) {
            return copy(second);
        } else if (first.equals(second)) {
            return null;
        }

        final List<Object> copy = new ArrayList<>();

        nextS: for (final Object s : second) {
            final String sId = identify(s);

            if (sId != null) {
                for (final Object f : first) {
                    final String fId = identify(f);
                    if (sId.equals(fId)) {
                        copy.add(differenceAny(f, s));
                        continue nextS;
                    }
                }
            }

            copy.add(copyAny(s));
        }

        return copy;
    }

    private Object differenceAny(Object oldValue, Object newValue) {
        if (oldValue == null || newValue == null) {
            return newValue;

        } else if (isBasic(oldValue) || isBasic(newValue)) {
            if (oldValue.equals(newValue)) {
                return null;
            } else {
                return newValue;
            }

        } else if (oldValue instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> oldMap = (Map<String, Object>) oldValue;

            if (newValue instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> newMap = (Map<String, Object>) newValue;
                return difference(oldMap, newMap);

            } else if (newValue instanceof List) {

                @SuppressWarnings("unchecked")
                final List<Object> newList = (List<Object>) newValue;

                final Object oldItemsObj = oldMap.get(ITEMS);
                if (oldItemsObj == null) {
                    return copy(newList);

                } else if (oldItemsObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    final List<Object> oldList = (List<Object>) oldItemsObj;
                    return difference(oldList, newList); // TODO: Since prototype is not saved, this diff potentially contains too many elements

                } else {
                    throw new ResolverException(
                        "Can't compute difference between non-prototype " +
                        "object.");
                }
            } else {
                throw new ResolverException(format(
                    "Can't compute difference between Map and " +
                    "unsupported type '%s'.",
                    newValue.getClass().getName()));
            }

        } else if (oldValue instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> oldList = (List<Object>) oldValue;

            if (newValue instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> newList = (List<Object>) newValue;
                return difference(oldList, newList);

            } else {
                throw new ResolverException(format(
                    "Can't compute difference between a List and a %s",
                    newValue.getClass().getName()
                ));
            }
        } else {

            throw new ResolverException(format(
                "Can't compute difference between values " +
                "since the first is of unrecognized type '%s'.",
                oldValue.getClass().getName()));

        }
    }

    private Object resolveExtendsAny(Object value) {
        if (value == null) return null;
        final Object resolvedValue;

        if (isBasic(value)) {
            resolvedValue = value;

        } else if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> casted = (Map<String, Object>) value;
            resolvedValue = resolveExtends(casted);

        } else if (value instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> casted = (List<Object>) value;
            resolvedValue = resolveExtends(casted);

        } else {
            throw new ResolverException(format(
                "Could not resolve 'extends' since one descending element " +
                "was of unknown type '%s'.", value.getClass().getName()
            ));
        }

        return resolvedValue;
    }

    private List<Object> resolveExtends(List<Object> document) {
        final List<Object> newList = new ArrayList<>();

        for (final Object obj : document) {
            newList.add(resolveExtendsAny(obj));
        }

        return newList;
    }

    private Map<String, Object> resolveExtends(Map<String, Object> document) {
        final Object extendsObj = document.get(EXTENDS);
        final Map<String, Object> result = new LinkedHashMap<>();

        if (extendsObj != null) {
            final Map<String, Object> prototype;

            if (extendsObj instanceof String) {
                final String resourceName = (String) extendsObj;
                prototype = resolveExtends(loader.apply(resourceName));

            } else if (extendsObj instanceof Map) {

                @SuppressWarnings("unchecked")
                final Map<String, Object> casted = (Map<String, Object>) extendsObj;
                prototype = resolveExtends(casted);

            } else {
                throw new ResolverException(format(
                    "Unknown 'extends' type '%s'.",
                    extendsObj.getClass().getName()
                ));
            }

            result.put(EXTENDS, ""); // Make sure order is correct.
            result.putAll(prototype);
            result.put(EXTENDS, extendsObj); // So that the process can be reversed
        }

        document.forEach((key, newValue) -> {
            if (EXTENDS.equals(key)) return;
            else if (newValue == null) {
                result.put(key, null);
                return;
            }

            final Object existingValue = result.get(key);
            if (existingValue == null || isBasic(existingValue)) {
                result.put(key, resolveExtendsAny(newValue));

            } else if (existingValue instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> existingMap = (Map<String, Object>) existingValue;
                result.put(key, mergeAny(existingMap, resolveExtendsAny(newValue)));

            } else if (existingValue instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> existingList = (List<Object>) existingValue;

                if (newValue instanceof List) {
                    @SuppressWarnings("unchecked")
                    final List<Object> newList = (List<Object>) newValue;

                    result.put(key, merge(existingList, resolveExtends(newList)));

                } else {
                    throw new ResolverException(format(
                        "Can't merge extended list with document of type " +
                        "'%s' at key '%s'.",
                        newValue.getClass().getName(),
                        key
                    ));
                }
            } else {
                throw new ResolverException(format(
                    "Can't merge extended value at key '%s' with unknown " +
                    "value of type '%s'.",
                    key, newValue.getClass().getName()
                ));
            }
        });

        return result;
    }

    private Object expandPrototypesAny(Object object) {
        if (object == null) return null;
        else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> casted = (Map<String, Object>) object;
            return expandPrototypes(casted);

        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> casted = (List<Object>) object;
            final Map<String, Object> expanded = new LinkedHashMap<>();
            expanded.put(ITEMS, casted);
            return expandPrototypes(expanded);

        } else {
            return object;
        }
    }

    private Map<String, Object> expandPrototypes(Map<String, Object> document) {
        final Map<String, Object> prototype = resolvePrototype(document.get(PROTOTYPE));
        final Object itemsObj = document.get(ITEMS);

        // If the 'items' element is missing, simply copy over every key-value
        // pair.
        final Map<String, Object> resolved = new LinkedHashMap<>();
        if (itemsObj == null) {
            document.forEach((key, value) -> {
                resolved.put(key, expandPrototypesAny(value));
            });

        // If an 'items' list exists, go through it, merging every element with
        // the prototype before adding it.
        } else {
            if (!(itemsObj instanceof List)) {
                throw new ResolverException(format(
                    "Every object mapped to an 'items' key is expected to be " +
                    "of type 'java.util.List', but was '%s'.",
                    itemsObj.getClass().getName()));
            }

            @SuppressWarnings("unchecked")
            final List<Object> items = (List<Object>) itemsObj;

            final List<Object> itemsCopy = new ArrayList<>();
            for (final Object item : items) {
                itemsCopy.add(expandPrototypes(mergeAny(prototype, item)));
            }

            document.forEach((key, value) -> {
                if (ITEMS.equals(key)) {
                    resolved.put(ITEMS, itemsCopy);
                } else {
                    resolved.put(key, expandPrototypesAny(value));
                }
            });
        }

        return resolved;
    }

    private Map<String, Object> mergeAny(Map<String, Object> prototype, Object any) {
        if (any == null) {
            return copy(prototype);

        } else if (any instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) any;
            return merge(prototype, map);

        } else if (any instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) any;
            final Object itemsObj = prototype.get(ITEMS);
            final Map<String, Object> copy = copy(prototype);
            if (itemsObj instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> items = (List<Object>) itemsObj;
                copy.put(ITEMS, merge(items, list));
            } else {
                copy.put(ITEMS, copy(list));
            }
            return copy;
        } else {
            throw new ResolverException(format(
                "Can't merge with value of unknown type '%s'.",
                any.getClass().getName()
            ));
        }
    }

    private Map<String, Object> merge(Map<String, Object> prototype, Map<String, Object> other) {
        final Map<String, Object> copy = new LinkedHashMap<>();
        prototype.forEach((key, value) -> copy.put(key, copyAny(value)));
        other.forEach((key, value) -> {
            final Object original = copy.get(key);
            if (original == null || isBasic(original)) {
                copy.put(key, copyAny(value));

            } else if (original instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> originalMap = (Map<String, Object>) original;
                copy.put(key, mergeAny(originalMap, value));

            } else if (original instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> originalList = (List<Object>) original;
                if (value instanceof List) {
                    @SuppressWarnings("unchecked")
                    final List<Object> valueList = (List<Object>) value;
                    copy.put(key, merge(originalList, valueList));

                } else {
                    throw new ResolverException(format(
                        "Can't merge List at key '%s' with value of type '%s'.",
                        key, value.getClass().getName()
                    ));
                }

            } else {
                throw new ResolverException(format(
                    "Can't merge value at key '%s' since the type '%s' " +
                    "isn't recognized.", key, original.getClass().getName()
                ));
            }
        });

        return copy;
    }

    private List<Object> merge(List<Object> prototype, List<Object> other) {
        final List<Object> copy = new ArrayList<>();

        for (final Object p : prototype) {
            copy.add(copyAny(p));
        }

        nextO: for (final Object o : other) {
            final String id = identify(o);

            if (id != null) {
                for (int i = 0; i < copy.size(); i++) {
                    final Object p   = copy.get(i);
                    final String pId = identify(p);
                    if (id.equals(pId)) {
                        @SuppressWarnings("unchecked") // Wouldn't have an ID otherwise.
                        final Map<String, Object> pMap = (Map<String, Object>) p;

                        copy.set(i, mergeAny(pMap, o));
                        continue nextO;
                    }
                }
            }

            copy.add(copyAny(o));
        }

        return copy;
    }

    private String identify(Object object) {
        if (object == null || isBasic(object) || object instanceof List) {
            return null;

        } else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            final Object id = map.get(ID);
            if (id == null) return null;
            else if (id instanceof String) {
                return (String) id;
            } else {
                throw new ResolverException(format(
                    "Found 'id' attribute in object, but the value type was " +
                        "'%s' instead of 'String'.",
                    id.getClass().getName()
                ));
            }

        } else {
            throw new ResolverException(format(
                "Can't identify unknown type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    private Object copyAny(Object any) {
        if (any == null || isBasic(any)) {
            return any;

        } else if (any instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) any;
            return copy(map);

        } else if (any instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) any;
            return copy(list);

        } else {
            throw new ResolverException(format(
                "Can't copy '%s' because the type '%s' was not recognized.",
                any, any.getClass().getName()
            ));
        }
    }

    private Map<String, Object> copy(Map<String, Object> original) {
        if (original == null) return null;
        final Map<String, Object> copy = new LinkedHashMap<>();
        original.forEach((key, value) -> copy.put(key, copyAny(value)));
        return copy;
    }

    private List<Object> copy(List<Object> original) {
        if (original == null) return null;
        final List<Object> copy = new ArrayList<>();

        for (final Object obj : original) {
            copy.add(copyAny(obj));
        }

        return copy;
    }

    private Map<String, Object> resolvePrototype(Object prototypeObj) {
        if (prototypeObj == null) return new LinkedHashMap<>();

        if (prototypeObj instanceof String) {
            final String resourceName = (String) prototypeObj;
            return expandPrototypes(resolveExtends(loader.apply(resourceName)));

        } else if (prototypeObj instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> prototype =
                (Map<String, Object>) prototypeObj;

            return expandPrototypes(prototype);

        } else {
            throw new ResolverException(format(
                "Expected 'prototype' value to be either a String or a " +
                "Map, but was a '%s'.", prototypeObj.getClass().getName()
            ));
        }
    }

    private Object flatten(Map<String, Object> document) {
        final Object itemsObj = document.get(ITEMS);
        if (itemsObj instanceof List) {
            final Object prototypeObj = document.get(PROTOTYPE);
            if (document.size() == 1 // There is no prototype, only items
            ||  (prototypeObj instanceof Map && ((Map<?, ?>) prototypeObj).isEmpty())) {
                @SuppressWarnings("unchecked")
                final List<Object> items = (List<Object>) itemsObj;
                return flatten(items);
            }
        }

        final Map<String, Object> copy = new LinkedHashMap<>();
        document.forEach((key, value) -> {
            copy.put(key, flattenAny(value));
        });

        return copy;
    }

    private List<Object> flatten(List<Object> items) {
        final List<Object> copy = new ArrayList<>();
        for (final Object val : items) {
            copy.add(flattenAny(val));
        }
        return copy;
    }

    private Object flattenAny(Object value) {
        if (value == null || isBasic(value)) {
            return value;
        } else if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) value;
            return flatten(map);
        } else if (value instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) value;
            return flatten(list);
        } else {
            throw new ResolverException(format(
                "Can't flatten value of type '%s' since it is not supported.",
                value.getClass().getName()
            ));
        }
    }

    /**
     * Returns {@code true} if the specified value is of a type that is
     * immutable and that can't be recursed down into. This method only checks
     * some common types. There might be types not checked by this method that
     * could also satisfy the definition <i>basic</i> given above.
     *
     * @param value  the value to check
     * @return  {@code true} if basic, otherwise {@code false}
     */
    private static boolean isBasic(Object value) {
        return (value instanceof String
            ||  value instanceof Integer
            ||  value instanceof Long
            ||  value instanceof Float
            ||  value instanceof Double
            ||  value instanceof Boolean
            ||  value instanceof UUID
            ||  value instanceof LocalTime
            ||  value instanceof LocalDate
            ||  value instanceof LocalDateTime);
    }
}
