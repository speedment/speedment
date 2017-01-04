/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.trait.HasClasses;
import com.speedment.common.codegen.util.Formatting;

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
        String name = Formatting.fileToClassName(file.getName())
            .flatMap(Formatting::packageName)
            .orElseThrow(
                () -> new RuntimeException(
                    "File did not have appropriate name."
                )
            ) + "." + pathTo(file, clazz.getName());

        return name;
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
