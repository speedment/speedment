package com.speedment.runtime.config.resolver;

import com.speedment.runtime.config.internal.resolver.NewDocumentResolverImpl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This interface describes a resolver that can read links between documents and
 * resolve them so that a flat document is created. The supported syntax is:
 *
 * <em>extends</em>
 * Fill the object with the contents of the {@code extends} value before filling
 * in the inlined attributes. {@code extends} can either be a {@code String}
 * with an external resource name to load or an inlined {@link Map}.
 *
 * <em>prototype</em>
 * The {@code prototype} object will be copied into every element of the
 * {@code items} {@link List} if it exists in this object. The prototype is
 * resolved before the actual elements of the child object is filled in.
 *
 * <em>id</em>
 * If a {@link Map} with a particular {@code id} is added to a list that already
 * contains a {@link Map} with that {@code id}, then the resolver will merge
 * those maps. This is used when {@code extends} and {@code prototype} commands
 * are resolved.
 *
 * @author Emil Forslund
 * @since  3.1.7
 */
public interface DocumentResolver {

    String EXTENDS   = "extends";
    String PROTOTYPE = "prototype";
    String ITEMS     = "items";
    String ID        = "id";

    /**
     * Creates and returns a new resolver using the default implementation. The
     * resolver will use the specified loader to load resources mentioned in
     * {@link #EXTENDS} and {@link #PROTOTYPE} values.
     *
     * @param loader  the loader to use
     * @return        the resolver
     */
    static DocumentResolver create(Function<String, Map<String, Object>> loader) {
        return new NewDocumentResolverImpl(loader);
    }

    /**
     * Loads the specifying resource, resolving any links before returning the
     * flat document.
     *
     * @param resourceName  the resource to load
     * @return  the flat document
     */
    Map<String, Object> loadAndResolve(String resourceName);

    /**
     * Resolves any links in the specified document, returning a copy that is
     * ready to be parsed.
     *
     * @param document  the document to resolve links in
     * @return  a flat copy of the document
     */
    Map<String, Object> resolve(Map<String, Object> document);

    /**
     * Returns a copy of the {@link Map} that only retains the values that have
     * changed from its {@code parents} and {@code prototypes}, reversing the
     * process of {@link #resolve(Map)}.
     *
     * @param document  the document to normalize
     * @return  the normalized document
     */
    Map<String, Object> normalize(Map<String, Object> document);

    /**
     * Returns a copy of {@code second} retaining only the values that differ
     * from those in {@code first}. The difference operation is recursive so
     * that inner maps and lists are also parsed. No modifications will be done
     * to either of the two input maps.
     *
     * @param first   the original map to compare with
     * @param second  copy of first with modifications done to it
     * @return        copy of second containing only the changes made
     */
    Map<String, Object> difference(Map<String, Object> first, Map<String, Object> second);

}