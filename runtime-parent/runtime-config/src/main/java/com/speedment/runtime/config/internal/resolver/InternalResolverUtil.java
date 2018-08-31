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
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * @author Emil Forslund
 * @since  3.1.7
 */
public final class InternalResolverUtil {

    public static <T> T deepCopy(T object) {
        if (object == null) return null;
        else if (isBasic(object)) return object;
        else if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            @SuppressWarnings("unchecked")
            final T result = (T) deepCopyMap(map);
            return result;
        } else if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) object;
            @SuppressWarnings("unchecked")
            final T result = (T) deepCopyList(list);
            return result;
        } else {
            throw new ResolverException(format(
                "Can't copy unsupported type '%s'.",
                object.getClass().getName()
            ));
        }
    }

    private static Map<String, Object> deepCopyMap(Map<String, Object> map) {
        if (map == null) return null;
        final Map<String, Object> copy = new LinkedHashMap<>();
        map.forEach((key, value) -> copy.put(key, deepCopy(value)));
        return copy;
    }

    private static List<Object> deepCopyList(List<Object> list) {
        if (list == null) return null;
        final List<Object> copy = new ArrayList<>();

        for (final Object obj : list) {
            copy.add(deepCopy(obj));
        }

        return copy;
    }

    public static <A, B> Object merge(A oldValue, B newValue) {
        if (oldValue == null || newValue == null
        || isBasic(oldValue) || isBasic(newValue)) {
            return deepCopy(newValue);

        } else if (oldValue instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> oldMap = (Map<String, Object>) oldValue;

            if (newValue instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> newList = (List<Object>) newValue;
                return mergeMaps(oldMap, listToMap(newList));
            } else if (newValue instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> newMap = (Map<String, Object>) newValue;
                return mergeMaps(oldMap, newMap);
            } else {
                throw new ResolverException(format(
                    "Can't merge values '%s' and '%s' since " +
                    "the type '%s' is not supported.",
                    oldValue, newValue, newValue.getClass().getName()
                ));
            }

        } else if (oldValue instanceof List) {

            @SuppressWarnings("unchecked")
            final List<Object> oldList = (List<Object>) oldValue;

            if (newValue instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> newList = (List<Object>) newValue;
                return mergeLists(oldList, newList);
            } else if (newValue instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> newMap = (Map<String, Object>) newValue;
                return mergeMaps(listToMap(oldList), newMap);
            } else {
                throw new ResolverException(format(
                    "Can't merge values '%s' and '%s' since " +
                    "the type '%s' is not supported.",
                    oldValue, newValue, newValue.getClass().getName()
                ));
            }
        } else {
            throw new ResolverException(format(
                "Can't merge values '%s' and '%s' since " +
                "the type '%s' is not supported.",
                oldValue, newValue, oldValue.getClass().getName()
            ));
        }
    }

    private static Map<String, Object> mergeMaps(Map<String, Object> first, Map<String, Object> second) {
        final Map<String, Object> map = new LinkedHashMap<>();
        first.forEach((key, value) -> map.put(key, deepCopy(value)));
        second.forEach((key, newValue) -> {
            // Can't use merge since value might be null.
            final Object oldValue = map.get(key);
            map.put(key, merge(oldValue, newValue));
        });
        return map;
    }

    private static List<Object> mergeLists(List<Object> first, List<Object> second) {
        final List<Object> merged = new ArrayList<>();
        first.forEach(obj -> merged.add(deepCopy(obj)));
        second.forEach(newValue -> {
            final Object oldValue = find(merged, newValue);
            if (oldValue == null) {
                merged.add(newValue);
            } else {
                merged.set(merged.indexOf(oldValue), deepCopy(newValue));
            }
        });
        return merged;
    }

    private static Object find(List<Object> list, Object needle) {
        final String needleId = identify(needle);
        if (needleId == null) return null;
        for (final Object obj : list) {
            if (needleId.equals(identify(obj))) {
                return obj;
            }
        }
        return null;
    }

    private static String identify(Object object) {
        if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>) object;
            @SuppressWarnings("unchecked")
            final String id = (String) map.get(DocumentResolver.ID);
            return id;
        } else return null;
    }

    public static Map<String, Object> listToMap(List<Object> list) {
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put(DocumentResolver.ITEMS, deepCopyList(list));
        return map;
    }

    public static Map<String, Object> reorder(Map<String, Object> map) {
        final LinkedHashMap<String, Object> copy = new LinkedHashMap<>();

        if (map.containsKey(DocumentResolver.EXTENDS)) {
            copy.put(DocumentResolver.EXTENDS, map.get(DocumentResolver.EXTENDS));
        }

        if (map.containsKey(DocumentResolver.ID)) {
            copy.put(DocumentResolver.ID, map.get(DocumentResolver.ID));
        }

        if (map.containsKey(DocumentResolver.PROTOTYPE)) {
            copy.put(DocumentResolver.PROTOTYPE, map.get(DocumentResolver.PROTOTYPE));
        }

        if (map.containsKey(DocumentResolver.ITEMS)) {
            copy.put(DocumentResolver.ITEMS, map.get(DocumentResolver.ITEMS));
        }

        map.forEach((key, value) -> {
            switch (key) {
                case DocumentResolver.EXTENDS:
                case DocumentResolver.ID:
                case DocumentResolver.PROTOTYPE:
                case DocumentResolver.ITEMS: return;
                default: copy.put(key, value);
            }
        });

        return copy;
    }

    public static Map<String, Object> difference(Map<String, Object> oldResolved, Map<String, Object> newResolved) {
        final Map<String, Object> changes = new LinkedHashMap<>();

        newResolved.forEach((key, newValue) -> {
            final Object oldValue = oldResolved.get(key);
            differenceAny(oldValue, newValue, diff -> changes.put(key, diff));
        });

        return changes;
    }

    private static List<Object> difference(List<Object> first, List<Object> second) {
        final List<Object> changes = new ArrayList<>(first);
        for (final Object obj : second) {
            final Object found = find(changes, obj);
            if (found == null) {
                changes.add(obj);
            } else {
                final int i = changes.indexOf(found);
                if (!differenceAny(found, obj, diff -> changes.set(i, diff))) {
                    changes.remove(i);
                }
            }
        }
        return changes;
    }

    private static boolean differenceAny(Object oldValue, Object newValue, Consumer<Object> action) {
        if (newValue == null) {
            if (oldValue != null) {
                // There is a difference between setting a value to 'null' and
                // not setting it at all.
                action.accept(null);
                return true;
            }
        } else {
            if (isBasic(newValue)) {
                if (!Objects.equals(oldValue, newValue)) {
                    action.accept(newValue);
                    return true;
                }
            } else if (newValue instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> newMap = (Map<String, Object>) newValue;

                if (oldValue instanceof Map) {
                    @SuppressWarnings("unchecked")
                    final Map<String, Object> oldMap = (Map<String, Object>) oldValue;
                    final Map<String, Object> subchanges = difference(oldMap, newMap);
                    if (!subchanges.isEmpty()) {
                        action.accept(subchanges);
                        return true;
                    }
                } else if (oldValue instanceof List) {
                    @SuppressWarnings("unchecked")
                    final List<Object> oldList = (List<Object>) oldValue;
                    final Map<String, Object> oldMap = listToMap(oldList);
                    final Map<String, Object> subchanges = difference(oldMap, newMap);
                    if (!subchanges.isEmpty()) {
                        action.accept(subchanges);
                        return true;
                    }
                } else {
                    throw new ResolverException(format(
                        "Can't compute difference between '%s' and '%s' " +
                        "since the type '%s' is unsupported.",
                        oldValue, newValue, oldValue.getClass().getName()
                    ));
                }
            } else if (newValue instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> newList = (List<Object>) newValue;

                if (oldValue instanceof Map) {
                    @SuppressWarnings("unchecked")
                    final Map<String, Object> oldMap = (Map<String, Object>) oldValue;
                    final Map<String, Object> subchanges = difference(oldMap, listToMap(newList));
                    if (subchanges.isEmpty()) {
                        action.accept(subchanges);
                        return true;
                    }
                } else if (oldValue instanceof List) {
                    @SuppressWarnings("unchecked")
                    final List<Object> oldList = (List<Object>) oldValue;
                    final List<Object> subchanges = difference(oldList, newList);
                    if (!subchanges.isEmpty()) {
                        action.accept(subchanges);
                        return true;
                    }
                } else {
                    throw new ResolverException(format(
                        "Can't compute difference between '%s' and '%s' " +
                        "since the type '%s' is unsupported.",
                        oldValue, newValue, oldValue.getClass().getName()
                    ));
                }
            } else {
                throw new ResolverException(format(
                    "Can't compute difference between '%s' and '%s' " +
                    "since the type '%s' is unsupported.",
                    oldValue, newValue, newValue.getClass().getName()
                ));
            }
        }

        return false;
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
    public static boolean isBasic(Object value) {
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

    private InternalResolverUtil() {}
}
