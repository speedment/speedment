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

import com.speedment.config.Project;
import com.speedment.config.Node;
import static com.speedment.internal.util.Beans.getterBeanPropertyNameAndValue;
import com.speedment.internal.util.JavaLanguage;
import com.speedment.SpeedmentVersion;
import com.speedment.Speedment;
import com.speedment.config.Dbms;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

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

        MethodsParser.streamOfExternalNoneSecretGetters(node.getClass())
            .sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
            .forEach(m -> getterBeanPropertyNameAndValue(m, node)
                .ifPresent(t -> sb.add(indent(indentLevel) + t))
            );

//        if (node instanceof Dbms) {
//            System.out.println("found DBMS:");
//            MethodsParser.streamOfExternalNoneSecretGetters(node.getClass()).map(Method::toString).forEach(System.out::println);
//        }

        Optional.of(node).flatMap(n -> n.asParent()).ifPresent(n
            -> n.stream().forEach(c -> {
                sb.add(indent(indentLevel) + JavaLanguage.javaVariableName(c.getInterfaceMainClass().getSimpleName()) + " {");
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

        //final GroovyShell shell = new GroovyShell(DelegatorGroovyTest.class.getClassLoader(), binding, configuration);
        final GroovyShell shell = new GroovyShell(GroovyParser.class.getClassLoader(), binding, configuration);

        final DelegatingScript script = scriptMapper.apply(shell);
//
//        final DelegatingScript scripts = (DelegatingScript) shell.parse(path.toFile());

        //final Project project = SpeedmentPlatform.getInstance().getConfigEntityFactory().newProject();
        script.setDelegate(node);

        final Object value = script.run();

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
        final StringBuilder sb = new StringBuilder();
        return indent(sb, indentLevel).toString();
    }

    private static StringBuilder indent(final StringBuilder sb, final int indentLevel) {
        requireNonNull(sb);

        IntStream.range(0, indentLevel).forEach(i -> sb.append("    "));
        return sb;
    }
}
