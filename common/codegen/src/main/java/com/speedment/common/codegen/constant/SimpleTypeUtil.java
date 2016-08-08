package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.internal.util.Formatting;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.trait.HasClasses;
import java.util.Optional;

/**
 * Common utility methods for the SimpleType-classes.
 * 
 * @author  Emil Forslund
 * @since   2.4.1
 */
final class SimpleTypeUtil {
    
    /**
     * Creates a full name for the specified class in the specified file,
     * suitable to be used as a TypeName.
     * 
     * @param file   the file to reference
     * @param clazz  the class to reference
     * @return       the full name of the class
     */
    public static String nameOf(File file, ClassOrInterface<?> clazz) {
        final StringBuilder name = new StringBuilder(
            Formatting.fileToClassName(file.getName())
                .flatMap(Formatting::packageName)
                .orElseThrow(
                () -> new RuntimeException(
                    "File did not have appropriate name."
                )
            )
        );
        
        name.append(".").append(pathTo(file, clazz.getName()));
        
        return name.toString();
    }
    
    private static String pathTo(HasClasses<?> parent, String needle) {
        return pathTo(parent, "", needle).orElseThrow(() -> new RuntimeException(
            "No class '" + needle + "' found in parent '" + parent + "'."
        ));
    }
    
    private static Optional<String> pathTo(HasClasses<?> parent, String path, String needle) {
        for (final ClassOrInterface<?> child : parent.getClasses()) {
            final String childName = child.getName();
            final String newPath = path.isEmpty() ? "" : path + "." + childName;
            
            if (childName.equals(needle)) {
                return Optional.of(newPath);
            } else {
                final Optional<String> recursion = pathTo(child, newPath, needle);
                if (recursion.isPresent()) {
                    return recursion;
                }
            }
        }
        
        return Optional.empty();
    }
    
    private SimpleTypeUtil() {}
}
