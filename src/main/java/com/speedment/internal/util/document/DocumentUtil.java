/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.document;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.util.Trees;
import com.speedment.stream.MapStream;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author pemi
 */
public final class DocumentUtil {

    @SuppressWarnings("unchecked")
    public static Stream<? extends Document> traverseOver(Document document) {
        requireNonNull(document);
        return Trees.traverse(
                document,
                d -> (Stream<Document>) d.children(),
                Trees.TraversalOrder.DEPTH_FIRST_PRE
        );
    }

    public static <E extends Document> Optional<E> ancestor(Document document, final Class<E> clazz) {
        requireNonNulls(document, clazz);
        return document.ancestors()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                //                .map(p -> (E) p)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public static <E extends Document> Stream<Document> childrenOf(Document document, BiFunction<Document, Map<String, Object>, E> childConstructor) {
        return document.stream().values()
                .filter(obj -> obj instanceof List<?>)
                .map(list -> (List<Object>) list)
                .flatMap(list -> list.stream())
                .filter(obj -> obj instanceof Map<?, ?>)
                .map(map -> (Map<String, Object>) map)
                .map(map -> childConstructor.apply(document, map));
    }

    public static Map<String, Object> newDocument(Document parent, String key) {
        final List<Map<String, Object>> children = parent.get(key)
                .map(Document.DOCUMENT_LIST_TYPE::cast)
                .orElseGet(() -> {
                    final List<Map<String, Object>> list = new CopyOnWriteArrayList<>();
                    parent.put(key, list);
                    return list;
                });

        final Map<String, Object> child = new ConcurrentHashMap<>();
        children.add(child);

        return child;
    }

    /**
     * Returns the relative name for the given Document up to the point given by
     * the parent Class.
     * <p>
     * For example, {@code relativeName(column, Dbms.class)} would return the
     * String "dbms_name.schema_name.table_name.column_name".
     *
     * @param <T> parent type
     * @param <D> Document type
     * @param document to use
     * @param from class
     * @return the relative name for this Node from the point given by the
     * parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName> String relativeName(
            final D document,
            final Class<T> from
    ) {
        return relativeName(document, from, Function.identity());
    }

    /**
     * Returns the relative name for the given Document up to the point given by
     * the parent Class by successively applying the provided nameMapper onto
     * the Node names.
     * <p>
     * For example, {@code relativeName(column, Dbms.class)} would return the
     * String "dbms_name.schema_name.table_name.column_name".
     *
     * @param <T> parent type
     * @param <D> Document type
     * @param document to use
     * @param from class
     * @param nameMapper to apply to all names encountered during traversal
     * @return the relative name for this Node from the point given by the
     * parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName> String relativeName(
            final D document,
            final Class<T> from,
            final Function<String, String> nameMapper
    ) {
        return relativeName(document, from, ".", nameMapper);
    }

    /**
     * Returns the relative name for the given Document up to the point given by
     * the parent Class by successively applying the provided nameMapper onto
     * the Node names and separating the names with the provided separator.
     * <p>
     * For example, {@code relativeName(column, Dbms.class)} would return the
     * String "dbms_name.schema_name.table_name.column_name" if the separator is
     * "."
     *
     * @param <T> parent type
     * @param <D> Document type
     * @param document to use
     * @param from class
     * @param separator to use between the document names
     * @param nameMapper to apply to all names encountered during traversal
     * @return the relative name for this Node from the point given by the
     * parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName> String relativeName(
            final D document,
            final Class<T> from,
            final CharSequence separator,
            final Function<String, String> nameMapper
    ) {
        requireNonNulls(document, from, nameMapper);
        final StringJoiner sj = new StringJoiner(separator).setEmptyValue("");
        final List<HasName> ancestors = document.ancestors()
                .filter(HasName.class::isInstance)
                .map(HasName.class::cast)
                .collect(toList());
        boolean add = false;
        for (final HasName parent : ancestors) {
            if (add || from.isAssignableFrom(parent.getClass())) {
                sj.add(nameMapper.apply(nameFrom(parent)));
                add = true;
            }
        }
        sj.add(nameMapper.apply(nameFrom(document)));
        return sj.toString();
    }

    public static String relativeName(HasName document, final Class<? extends HasName> from) {
        return relativeName(document, from, s -> s);

    }

    public static Supplier<NoSuchElementException> newNoSuchElementExceptionFor(Document document, String key) {
        return () -> new NoSuchElementException(
                "An attribute with the key '" + key
                + "' could not be found in " + document
                + " with name (" + Optional.ofNullable(document)
                    .flatMap(doc -> doc.getAsString("name"))
                    .orElse("null")
                + ")"
        );
    }
    
    public static <DOC extends Document> DOC deepCopy(DOC document, Function<Map<String, Object>, DOC> constructor) {
        return constructor.apply(deepCopyMap(document.getData()));
    }
    
    public static <P extends Document, DOC extends Document & HasParent<P>> DOC deepCopy(DOC document, BiFunction<P, Map<String, Object>, DOC> constructor) {
        return constructor.apply(
            document.getParent().orElse(null), 
            deepCopyMap(document.getData())
        );
    }
    
    private static <K, V> Map<K, V> deepCopyMap(Map<K, V> original) {
        final Map<K, V> copy = new ConcurrentHashMap<>();
        
        MapStream.of(original)
            .mapValue(DocumentUtil::deepCopyObject)
            .forEachOrdered(copy::put);
        
        return copy;
    }
    
    private static <V> List<V> deepCopyList(List<V> original) {
        final List<V> copy = new CopyOnWriteArrayList<>();
        
        original.stream()
            .map(DocumentUtil::deepCopyObject)
            .forEachOrdered(copy::add);
        
        return copy;
    }

    private static <V> V deepCopyObject(V original) {
        if (String.class.isAssignableFrom(original.getClass())
        ||  Number.class.isAssignableFrom(original.getClass())
        ||  Boolean.class.isAssignableFrom(original.getClass())
        ||  Enum.class.isAssignableFrom(original.getClass())) {
            return original;
        } else if (List.class.isAssignableFrom(original.getClass())) {
            @SuppressWarnings("unchecked")
            final V result = (V) deepCopyList((List<?>) original);
            return result;
        } else if (Map.class.isAssignableFrom(original.getClass())) {
            @SuppressWarnings("unchecked")
            final V result = (V) deepCopyMap((Map<?, ?>) original);
            return result;
        } else {
            throw new UnsupportedOperationException(
                "Can't deep copy unknown type '" + original.getClass() + "'."
            );
        }
    }
    
    private static final Function<Object, Object> VALUE_MAPPER = o -> {
        if (o instanceof List) {
            return "[" + ((List) o).size() + "]";
        } else {
            return o;
        }
    };

    public static String toStringHelper(Document document) {

        return document.getClass().getSimpleName()
                + " {"
                + MapStream.of(document.getData())
                .mapValue(VALUE_MAPPER)
                .map((k, v) -> "\"" + k + "\": " + v.toString())
                .collect(joining(", "))
                + "}";
    }

    private static String nameFrom(HasName document) {
        if (document instanceof HasAlias) {
            return ((HasAlias) document).getJavaName();
        } else {
            return document.getName();
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private DocumentUtil() {
        instanceNotAllowed(getClass());
    }
}
