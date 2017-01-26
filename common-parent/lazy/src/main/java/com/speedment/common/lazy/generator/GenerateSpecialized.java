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
package com.speedment.common.lazy.generator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Per Minborg
 */
public class GenerateSpecialized {

    final static Path TEMPLATE = Paths.get("src/main/resources/", "LazyTemplate.txt");
    final static Path TARGET_DIR = Paths.get("src/main/java/com/speedment/common/lazy/specialized");

    public static void main(String[] args) throws IOException {

        final Set<Class<?>> oneArgClasses = Stream.of(Class.class).collect(toSet());

        for (final Class<?> clazz : Arrays.asList(
            String.class,
            Boolean.class,
            Byte.class,
            Float.class,
            Number.class,
            Short.class,
            BitSet.class,
            UUID.class,
            BigDecimal.class,
            BigInteger.class,
            Timestamp.class,
            Class.class
        )) {

            final String arg0Replacement = clazz.getSimpleName();
            final String arg1Replacement = importStatement(clazz).orElse("");
            final String arg2Replacement = oneArgClasses.contains(clazz) ? clazz.getSimpleName() + "<?>" : clazz.getSimpleName();

            final List<String> code = Files.lines(TEMPLATE)
                .map(l -> l.replace("{$0}", arg0Replacement))
                .map(l -> l.replace("{$1}", arg1Replacement))
                .map(l -> l.replace("{$2}", arg2Replacement))
                .collect(toList());
            final Path target = Paths.get(TARGET_DIR.toString(), "Lazy" + clazz.getSimpleName() + ".java");
            Files.write(target, code, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        }

    }

    private static Optional<String> importStatement(Class<?> clazz) {
        if ("java.lang".equals(clazz.getPackage().getName())) {
            return Optional.empty();
        }
        return Optional.of("import " + clazz.getName() + ";");
    }

}
