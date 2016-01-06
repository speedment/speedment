/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.config.utils;

import com.speedment.config.db.Project;
import com.speedment.config.Node;
import static com.speedment.internal.util.Beans.getterBeanPropertyNameAndValue;
import com.speedment.internal.util.JavaLanguage;
import com.speedment.SpeedmentVersion;
import com.speedment.Speedment;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public class GroovyParser {

    private static final Package IMPORT_PACKAGE = com.speedment.config.parameters.FieldStorageType.class.getPackage();

    public static String toGroovy(final Node node) {
        return toGroovyLines(node).collect(joining("\n"));
    }

    public static Stream<String> toGroovyLines(final Node node) {
        requireNonNull(node);

        final Stream.Builder<String> sb = Stream.builder();
        sb.add("import " + IMPORT_PACKAGE.getName() + ".*");
        sb.add("");
        toGroovyLines(sb, node, 0);
        return sb.build();
    }

    private static Stream.Builder<String> toGroovyLines(final Stream.Builder<String> sb, final Node node, final int indentLevel) {
        requireNonNull(sb);
        requireNonNull(node);

        // Properties
        MethodsParser.streamOfExternalNoneSecretGetters(node.getClass())
            .sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
            .forEach(m -> getterBeanPropertyNameAndValue(m, node)
                .ifPresent(t -> sb.add(indent(indentLevel) + t))
            );

        // Childs
        Optional.of(node).flatMap(Node::asParent).ifPresent(n
            -> n.stream().filter(Node::isEnabled).forEach(c -> {
                sb.add(indent(indentLevel) + JavaLanguage.javaVariableName(c.nodeTypeName()) + " {");
                toGroovyLines(sb, c, indentLevel + 1);
                sb.add(indent(indentLevel) + "}");
            })
        );
        
        return sb;
    }

    public static Project projectFromGroovy(Speedment speedment, final Path path) throws IOException {
        requireNonNull(speedment);
        requireNonNull(path);

        final Project project = Project.newProject(speedment);
        fromGroovy(project, path);
        return project;
    }

    public static Project projectFromGroovy(Speedment speedment, final String groovyFile) throws IOException {
        requireNonNull(speedment);
        requireNonNull(groovyFile);

        final Project project = Project.newProject(speedment);
        fromGroovy(project, groovyFile);
        return project;
    }
    
    public static void fromGroovy(final Node node, final Path path) throws IOException {
        requireNonNull(node);
        requireNonNull(path);

        final File file = path.toFile();
        fromGroovy(node, groovyShell -> (DelegatingScript) groovyShell.parse(file));
        
        if (node instanceof Project) {
            @SuppressWarnings("unchecked")
            final Project project = (Project) node;
            project.setConfigPath(path);
        }
    }

    private static void fromGroovy(final Node node, final FunctionThrowsException<GroovyShell, DelegatingScript> scriptMapper) throws IOException {
        requireNonNull(node);
        requireNonNull(scriptMapper);

        final Binding binding = new Binding();
        binding.setVariable("implementationVersion", SpeedmentVersion.getImplementationVersion());

        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(DelegatingScript.class.getName());
        configuration.setDebug(true);
        configuration.setVerbose(true);
        configuration.setRecompileGroovySource(true);
        configuration.setSourceEncoding(StandardCharsets.UTF_8.toString());

        final GroovyShell shell = new GroovyShell(GroovyParser.class.getClassLoader(), binding, configuration);
        final DelegatingScript script = scriptMapper.apply(shell);
        script.setDelegate(node);

        script.run();
    }

    public static void fromGroovy(final Node node, final String script) throws IOException {
        requireNonNull(node);
        requireNonNull(script);

        fromGroovy(node, groovyShell -> (DelegatingScript) groovyShell.parse(script));
        
        if (node instanceof Project) {
            @SuppressWarnings("unchecked")
            final Project project = (Project) node;
            project.setConfigPath(null);
        }
    }

    @FunctionalInterface
    interface FunctionThrowsException<T, R> {

        R apply(T t) throws CompilationFailedException, IOException;
    }

    private static String indent(final int indentLevel) {
        final char[] array = new char[indentLevel * 4];
        Arrays.fill(array, ' ');
        return String.valueOf(array);
    }
}
