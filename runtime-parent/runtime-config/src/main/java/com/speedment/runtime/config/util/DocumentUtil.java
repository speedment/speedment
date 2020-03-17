/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.internal.util.Trees;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Common utility methods for working with instances of the {@code Document}
 * interface.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DocumentUtil {

    /**
     * Traverses all the documents at and below the specified document in a
     * tree. Traversal is done depth first. The order of sub-document traversal
     * within a specific Document is unspecified (For example, a tables columns
     * may be traversed in any order).
     *
     * @param document  the document to start at
     * @return          stream of descendants
     */
    @SuppressWarnings("unchecked")
    public static Stream<? extends Document> traverseOver(Document document) {
        
        requireNonNull(document);
        
        return Trees.traverse(
            document,
            Document::children,
            Trees.TraversalOrder.DEPTH_FIRST_PRE
        );
    }

    /**
     * Returns the first ancestor found of the specified type to the specified
     * document when walking up the tree. If there was no ancestor of the
     * specified type and the root was reached, an empty {@code Optional} is
     * returned.
     *
     * @param <E>       ancestor type
     * @param document  the starting point
     * @param clazz     the ancestor type to look for
     * @return          first ancestor found or empty
     */
    public static <E extends Document> Optional<E> ancestor(
            final Document document,
            final Class<E> clazz) {
        
        requireNonNull(document);
        requireNonNull(clazz);
        return document.ancestors()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst();
    }

    /**
     * Returns a stream of child documents to a specified document by using the
     * supplied constructor.
     *
     * @param <E>               the expected child type
     * @param document          the parent document
     * @param childConstructor  child constructor
     * @return                  stream of children
     */
    @SuppressWarnings("unchecked")
    public static <E extends Document> Stream<E> childrenOf(
        final Document document,
        final BiFunction<Document, Map<String, Object>, E> childConstructor) {
        
        requireNonNull(document);
        requireNonNull(childConstructor);
        
        return document.getData().values().stream()
            .filter(obj -> obj instanceof List<?>)
            .map(list -> (List<Object>) list)
            .flatMap(Collection::stream)
            .filter(obj -> obj instanceof Map<?, ?>)
            .map(map -> (Map<String, Object>) map)
            .map(map -> childConstructor.apply(document, map));
    }

    /**
     * Creates and returns a new raw map on a specified key in the specified
     * document. This might involve creating a new list if no such existed
     * already. If children already existed on that key, the new one is simply
     * added to the end of the list.
     *
     * @param parent the parent to create it in
     * @param key the key to create it under
     * @return the newly creating raw child map
     */
    public static Map<String, Object> newDocument(Document parent, String key) {
        requireNonNull(parent);
        requireNonNull(key);

        final List<Map<String, Object>> children = parent.get(key)
            .map(DocumentUtil::castToDocumentList)
            .orElseGet(() -> {
                final List<Map<String, Object>> list = 
                    new CopyOnWriteArrayList<>();
                
                parent.put(key, list);
                return list;
            });

        final Map<String, Object> child = new ConcurrentSkipListMap<>();
        children.add(child);

        return child;
    }

    /**
     * An enumeration of the types of names that documents can have. This is
     * used to control which method should be called when parsing the document
     * into a name.
     */
    public enum Name {

        /**
         * The name used in the database to reference this document.
         */
        DATABASE_NAME,
        /**
         * A user defined name that is used for the document primarily in
         * generated code.
         */
        JAVA_NAME;

        /**
         * Returns the appropriate name of the specified document.
         *
         * @param document the document
         * @return the name
         */
        public String of(HasAlias document) {
            switch (this) {
                case DATABASE_NAME:
                    return document.getName();
                case JAVA_NAME:
                    return document.getJavaName();
                default:
                    throw new UnsupportedOperationException(
                        "Unknown enum constant '" + name() + "'."
                    );
            }
        }
    }

    /**
     * Returns the relative name for the given Document up to the point given by
     * the parent Class.
     * <p>
     * For example, {@code relativeName(column, Dbms.class, DATABASE_NAME)}
     * would return the String "dbms_name.schema_name.table_name.column_name".
     *
     * @param <T>       parent type
     * @param <D>       document type
     * @param document  to use
     * @param from      the document type to get the name from
     * @param name      if java or database name should be used
     * @return          the relative name for this Node from the point given by the
     *                  parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName>
        String relativeName(D document, Class<T> from, Name name) {
        return relativeName(document, from, name, UnaryOperator.identity());
    }

    /**
     * Returns the relative name for the given Document up to the point given by
     * the parent Class by successively applying the provided nameMapper onto
     * the Node names.
     * <p>
     * For example, {@code relativeName(column, Dbms.class, DATABASE_NAME)}
     * would return the String "dbms_name.schema_name.table_name.column_name".
     *
     * @param <T>        parent type
     * @param <D>        Document type
     * @param document   to use
     * @param from       class
     * @param name       if java or database name should be used
     * @param nameMapper to apply to all names encountered during traversal
     * @return           the relative name for this Node from the point given by the
     *                   parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName>
        String relativeName(
            D document,
            Class<T> from,
            Name name,
            UnaryOperator<String> nameMapper) {

        return relativeName(document, from, name, ".", nameMapper);
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
     * @param <T>         parent type
     * @param <D>         Document type
     * @param document    to use
     * @param from        class
     * @param name        if java or database name should be used
     * @param separator   to use between the document names
     * @param nameMapper  to apply to all names encountered during traversal
     * @return            the relative name for this Node from the point 
     *                    given by the parent Class
     */
    public static <T extends Document & HasName, D extends Document & HasName> 
    String relativeName(
            final D document,
            final Class<T> from,
            final Name name,
            final CharSequence separator,
            final UnaryOperator<String> nameMapper) {
        
        requireNonNull(document);
        requireNonNull(from);
        requireNonNull(nameMapper);
        
        final StringJoiner sj = new StringJoiner(separator).setEmptyValue("");
        final List<HasAlias> ancestors = document.ancestors()
            .map(HasAlias::of)
            .collect(toList());

        boolean add = false;
        for (final HasAlias parent : ancestors) {
            if (add || parent.mainInterface().isAssignableFrom(from)) {
                sj.add(nameMapper.apply(name.of(parent)));
                add = true;
            }
        }
        sj.add(nameMapper.apply(name.of(HasAlias.of(document))));
        return sj.toString();
    }

    /**
     * Creates a deep copy of the raw map in the specified document and wrap it
     * in a new typed document using the specified constructor.
     *
     * @param <DOC>        the document type
     * @param document     the document
     * @param constructor  the document constructor
     * @return             the copy
     */
    public static <DOC extends Document> DOC deepCopy(
        DOC document,
        Function<Map<String, Object>, DOC> constructor) {

        return constructor.apply(deepCopyMap(document.getData()));
    }

    /**
     * Creates a deep copy of the raw map in the specified document and wrap it
     * in a new typed document using the specified constructor.
     *
     * @param <P>          the parent type
     * @param <DOC>        the document type
     * @param document     the document
     * @param constructor  the document constructor
     * @return             the copy
     */
    public static <P extends Document, DOC extends Document & HasParent<P>>
        DOC deepCopy(DOC document, BiFunction<P, Map<String, Object>, DOC> constructor) {

        return constructor.apply(
            document.getParent().orElse(null),
            deepCopyMap(document.getData())
        );
    }

    /**
     * Returns an {@code Exception} supplier for when no attribute could be
     * found on a specified key in a specified document.
     *
     * @param document the document
     * @param key      the key
     * @return         the {@code Exception} supplier
     */
    public static Supplier<NoSuchElementException> newNoSuchElementExceptionFor(
        Document document,
        String key) {

        return () -> new NoSuchElementException(
            "An attribute with the key '" + key
            + "' could not be found in " + document
            + " with name (" + Optional.ofNullable(document)
            .flatMap(doc -> doc.getAsString("name"))
            .orElse("null")
            + ")"
        );
    }

    /**
     * Helps documents to format a {@code toString()}-method.
     *
     * @param document the document
     * @return         the string
     */
    public static String toStringHelper(Document document) {

        return document.getClass().getSimpleName()
            + " {"
            + document.getData().entrySet().stream()
            .map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), VALUE_MAPPER.apply(e.getValue())))
            .map(e -> "\"" + e.getKey() + "\": " + (e.getValue() == null ? "null" : quoteIfString(e.getValue())))
            .collect(joining(", "))
            + "}";
    }

    private static String quoteIfString(Object o) {
        return (o instanceof String ? ("\"" + o + "\"") : o.toString());
    }

    /**
     * Casts the specified object to a {@code List<Map<String, Object>>}.
     * <p>
     * Careful! This method does not cause any unchecked warnings, so it might
     * be unsafe to use.
     * 
     * @param obj  the object to cast
     * @return     the typed list
     */
    public static List<Map<String, Object>> castToDocumentList(Object obj) {
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
        return list;
    }

    private static <K, V> Map<K, V> deepCopyMap(Map<K, V> original) {
        final Map<K, V> copy = new ConcurrentSkipListMap<>();

        original.entrySet().stream()
            .map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), deepCopyObject(e.getValue())))
            .forEach(e -> copy.put(e.getKey(), e.getValue()));

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
            || Number.class.isAssignableFrom(original.getClass())
            || Boolean.class.isAssignableFrom(original.getClass())
            || Enum.class.isAssignableFrom(original.getClass())) {
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

    private static final UnaryOperator<Object> VALUE_MAPPER = o -> {
        if (o instanceof List) {
            return "[" + ((List<?>) o).size() + "]";
        } else {
            return o;
        }
    };

    /**
     * Utility classes should not be instantiated.
     */
    private DocumentUtil() {
        throw new UnsupportedOperationException();
    }
}
