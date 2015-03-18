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
package com.speedment.codegen.examples;

import com.speedment.codegen.Formatting;
import static com.speedment.codegen.Formatting.indent;
import static com.speedment.codegen.Formatting.nl;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.base.CodeView;
import com.speedment.codegen.base.DefaultInstaller;
import com.speedment.codegen.base.Installer;
import com.speedment.codegen.base.MultiGenerator;
import com.speedment.codegen.java.JavaInstaller;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.constants.DefaultType;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public class MultiTarget {
    
    private final static Installer 
        XML = new DefaultInstaller("XMLInstaller")
            .install(Method.class, MethodXMLView.class)
            .install(Field.class, FieldXMLView.class),
        
        JAVA = new JavaInstaller();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final MultiGenerator gen = new MultiGenerator(JAVA, XML);
        Formatting.tab("    ");
        
        gen.codeOn(
            Method.of("concat", DefaultType.STRING).public_()
                    .add(Field.of("str1", DefaultType.STRING))
                    .add(Field.of("str2", DefaultType.STRING))
                    .add("return str1 + str2;")
        ).forEach(code -> {
            System.out.println("-------------------------------------");
            System.out.println("  " + code.getInstaller().getName() + ":");
            System.out.println("-------------------------------------");
            System.out.println(code.getText());
        });
    }

    public static class MethodXMLView implements CodeView<Method> {
        @Override
        public Optional<String> render(CodeGenerator cg, Method model) {
            return Optional.of(
                "<method name=\"" + model.getName() + "\" type=\"" + cg.on(model.getType()).get() + "\">" + nl() + indent(
                    "<params>" + nl() + indent(
                        cg.codeOn(model.getFields())
                            .filter(c -> XML.equals(c.getInstaller()))
                            .map(c -> c.getText())
                            .collect(Collectors.joining(nl()))
                    ) + nl() + "</params>" + nl() +
                    "<code>" + nl() + indent(
                        model.getCode().stream().collect(Collectors.joining(nl()))
                    ) + nl() + "</code>"
                ) + nl() + "</methods>"
            );
        }
    }
    
    public static class FieldXMLView implements CodeView<Field> {
        @Override
        public Optional<String> render(CodeGenerator cg, Field model) {
            return Optional.of("<field name=\"" + model.getName() + "\" />");
        }
    }
}
