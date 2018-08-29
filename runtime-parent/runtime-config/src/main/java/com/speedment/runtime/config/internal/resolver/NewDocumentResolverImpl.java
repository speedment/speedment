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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * @author Emil Forslund
 * @since  3.1.6
 */
public class NewDocumentResolverImpl implements DocumentResolver {

    private final Function<String, Map<String, Object>> loader;

    public NewDocumentResolverImpl(Function<String, Map<String, Object>> loader) {
        this.loader = requireNonNull(loader);
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

    @Override
    public Map<String, Object> copy(Map<String, Object> original) {
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

    @Override
    public Map<String, Object> merge(Map<String, Object> first, Map<String, Object> second) {
        final Map<String, Object> copy = new LinkedHashMap<>();
        first.forEach((key, value) -> copy.put(key, copyAny(value)));
        second.forEach((key, value) -> {
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

    private List<Object> merge(List<Object> first, List<Object> second) {
        final List<Object> copy = new ArrayList<>();

        for (final Object p : first) {
            copy.add(copyAny(p));
        }

        nextO: for (final Object o : second) {
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

    @Override
    public Map<String, Object> loadAndResolve(String resourceName) {
        return resolve(loader.apply(resourceName));
    }

    private Object resolveAny(Object object) {
        if (object == null) return null;
        else if (isBasic(object)) return object;
        else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            return resolve(map);
        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) object;
            return list.stream().map(this::resolveAny).collect(toList());
        } else {
            throw new ResolverException(format(
                "Unsupported value type '%s'.", object.getClass().getName()
            ));
        }
    }

    @Override
    public Map<String, Object> resolve(Map<String, Object> document) {
        return resolve(document, null);
    }

    private Map<String, Object> resolve(Map<String, Object> document, Map<String, Object> prototype) {
        final Map<String, Object> result = newMap();
        if (prototype != null) apply(result, prototype);
        withExtends(document, parent -> apply(result, parent));
        apply(result, document);
        result.replaceAll((key, value) -> {
            if (isBasic(value)) return value;
            else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> list = (List<Object>) value;
                return list.stream().map(this::resolveAny).collect(toList());
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> map = (Map<String, Object>) value;
                final Object itemsObj = map.get(ITEMS);
                if (itemsObj == null) {
                    return resolve(map);
                } else {
                    final Map<String, Object> newMap = resolve(map);
                    withItems(newMap, this::resolve);
                    return newMap;
                }
            } else {
                throw new ResolverException(format(
                    "Key '%s' has a value of unsupported type '%s'.",
                    key, value
                ));
            }
        });
        return result;
    }

    @Override
    public Map<String, Object> normalize(Map<String, Object> document) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Map<String, Object> difference(Map<String, Object> first, Map<String, Object> second) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void apply(Map<String, Object> result, Map<String, Object> changes) {
        changes.forEach((key, newValue) -> {
            if (newValue == null) {
                result.put(key, null);
                return;
            }

            final Object oldValue = changes.get(key);
            if (oldValue == null || isBasic(oldValue)) {
                result.put(key, copyAny(newValue));
            } else if (!isMap(oldValue, oldMap -> result.put(key, mergeAny(oldMap, newValue))) && !isList(oldValue, oldList -> {
                if (!isList(newValue, newList -> result.put(key, merge(oldList, newList))) && !isMap(newValue, newMap -> {
                    newMap = resolve(newMap);
                    @SuppressWarnings("unchecked") final List<Object> newList = (List<Object>) newMap.computeIfAbsent(ITEMS, i -> new ArrayList<>());
                    newMap.put(ITEMS, merge(oldList, newList));
                    result.put(key, newMap);
                })) {
                    throw new ResolverException(format(
                        "Unsupported newValue type '%s' for key '%s'.",
                        newValue.getClass().getName(), key
                    ));
                }
            })) throw new ResolverException(format(
                "Unsupported oldValue type '%s' for key '%s'.",
                oldValue.getClass().getName(), key
            ));
        });
    }

    private void withPrototype(Object prototype, Consumer<Map<String, Object>> action) {
        if (!isNull(prototype, () -> action.accept(null))
        && !isString(prototype, res -> action.accept(loadAndResolve(res)))
        && !isMap(prototype, map -> action.accept(resolve(map)))) {
            throw new ResolverException(format(
                "Invalid prototype type '%s'.",
                prototype.getClass().getName()
            ));
        }
    }

    private void withExtends(Map<String, Object> document, Consumer<Map<String, Object>> action) {
        if (document != null) {
            final Object extendsObj = document.get(EXTENDS);
            if (extendsObj != null) {
                if (!isString(extendsObj, extendsStr -> action.accept(loadAndResolve(extendsStr)))
                    && !isMap(extendsObj, extendsMap -> action.accept(resolve(extendsMap)))) {
                    throw new ResolverException(format(
                        "Expected 'extends' value to be either a String or a " +
                        "Map, but was a %s.", extendsObj.getClass().getName()
                    ));
                }
            }
        }
    }

    private void withItems(Object document,
                           BiConsumer<Map<String, Object>, Map<String, Object>> action) {
        if (document != null
        && !isList(document,
            list -> list.forEach(
                item -> isMap(item,
                    itemMap -> action.accept(itemMap, null))))
        && !isMap(document,
            map -> withPrototype(map.get(PROTOTYPE),
                prototype -> isList(map.get(ITEMS),
                    list -> list.forEach(
                        item -> isMap(item,
                            itemMap -> action.accept(itemMap, prototype))))))) {
            throw new ResolverException(format(
                "Expected 'items' to be either a Map or a List, but was a %s.",
                document.getClass().getName()));
        }
    }

    private static boolean isNull(Object object, Runnable action) {
        if (object == null) {
            action.run();
            return true;
        } else return false;
    }

    private static boolean isString(Object object, Consumer<String> action) {
        if (object instanceof String) {
            final String string = (String) object;
            action.accept(string);
            return true;
        } else return false;
    }

    private static boolean isMap(Object object, Consumer<Map<String, Object>> action) {
        if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            action.accept(map);
            return true;
        } else return false;
    }

    private static boolean isList(Object object, Consumer<List<Object>> action) {
        if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> map = (List<Object>) object;
            action.accept(map);
            return true;
        } else return false;
    }

    private static boolean isBasic(Object object, Consumer<Object> action) {
        if (isBasic(object)) {
            action.accept(object);
            return true;
        } else return false;
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

    private static Map<String, Object> newMap() {
        return new LinkedHashMap<>();
    }
}
