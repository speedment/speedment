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
package com.speedment.orm.config.model.impl.utils;

import com.speedment.orm.config.DelegatorGroovyTest;
import com.speedment.orm.config.model.aspects.Childable;
import com.speedment.orm.config.model.aspects.Node;
import static com.speedment.orm.config.model.impl.utils.MethodsParser.*;
import static com.speedment.util.Beans.getterBeanPropertyNameAndValue;
import com.speedment.util.stream.CollectorUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.IntStream;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 *
 * @author Emil Forslund
 */
public class GroovyParser {

    private static final String NL = "\n";

    public static String toGroovy(final Node node, final int indentLevel) {
        return CollectorUtil.of(StringBuilder::new, sb -> {
            MethodsParser.getMethods(node.getClass(),
                METHOD_IS_GETTER.and(METHOD_IS_PUBLIC).and(METHOD_IS_EXTERNAL))
                .stream()
                .sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
                .forEach(m -> getterBeanPropertyNameAndValue(m, node)
                    .ifPresent(t -> indent(sb, indentLevel).append(t).append(NL))
                );

            Optional.of(node).filter(n -> n.isChildable()).map(n -> (Childable<?>) n).ifPresent(n
                -> n.stream().forEach(c -> {
                    indent(sb, indentLevel).append(c.getInterfaceMainClass().getSimpleName()).append(" {").append(NL);
                    sb.append(toGroovy(c, indentLevel + 1));
                    indent(sb, indentLevel).append("}").append(NL);
                })
            );
        }, StringBuilder::toString);
    }

    public static void fromGroovy(final Node node, final Path path) throws IOException {

        final Binding binding = new Binding();
        binding.setVariable("implementationVersion", node.getClass().getPackage().getImplementationVersion());
        binding.setVariable("specificationVersion", node.getClass().getPackage().getSpecificationVersion());

        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(DelegatingScript.class.getName());
        configuration.setDebug(true);
        configuration.setVerbose(true);
        configuration.setRecompileGroovySource(true);

        final GroovyShell shell = new GroovyShell(DelegatorGroovyTest.class.getClassLoader(), binding, configuration);

        final DelegatingScript script = (DelegatingScript) shell.parse(path.toFile());

        //final Project project = SpeedmentPlatform.getInstance().getConfigEntityFactory().newProject();
        script.setDelegate(node);

        final Object value = script.run();

        System.out.println(value);

        System.out.println(binding.getVariables());

        System.out.println(node.toString());

    }
    
    private static StringBuilder indent(final StringBuilder sb, final int indentLevel) {
        IntStream.range(0, indentLevel).forEach(i -> sb.append("    "));
        return sb;
    }
}
