package com.speedment.common.codegen.internal.java.view.trait;

import java.util.List;

final class TraitUtil {

    private TraitUtil() {}

    static void renderImportToLists(List<String> customImports, List<String> standardImports, List<String> staticImports, List<String> staticStandardImports, String line) {
        if (line.startsWith("import static")) {
            if (line.startsWith("import static java")) {
                staticStandardImports.add(line);
            } else {
                staticImports.add(line);
            }
        } else {
            if (line.startsWith("import java")) {
                standardImports.add(line);
            } else {
                customImports.add(line);
            }
        }
    }

}
