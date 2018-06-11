package com.speedment.common.codegen.internal.util;

import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.trait.HasImports;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for operating on a tree of code models.
 *
 * @author Emil Forslund
 * @since  2.5
 */
public final class ModelTreeUtil {

    /**
     * Returns a list of all the imports of the specified model by traversing
     * the entire tree.
     *
     * @param root  the root to start at (usually the {@link File})
     * @return      the list of imports
     */
    public static List<Import> importsOf(HasImports<?> root) {
        return traverse(HasImports.class, root, HasImports::getImports);
    }

    /**
     * Traverses the tree of models searching for children of a particular type
     * by applying the getter method on each found element. Even if the root
     * itself is of the searched type, it will not be added.
     *
     * @param parentType  the parent type
     * @param root        the root node in the tree
     * @param getter      getter method used to obtain a list of children
     * @param <T>         the type of the model searched for
     * @param <P>         the type of the parent
     * @return            list of hits
     */
    public static <T, P> List<T>
    traverse(Class<P> parentType, P root, Function<? super P, List<? extends T>> getter) {
        final List<T> found = new ArrayList<>(getter.apply(root));

        for (final T element : found) {
            if (parentType.isInstance(element)) {
                @SuppressWarnings("unchecked")
                final P asParent = (P) element;
                found.addAll(getter.apply(asParent));
            }
        }
        return found;
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ModelTreeUtil() {}
}
