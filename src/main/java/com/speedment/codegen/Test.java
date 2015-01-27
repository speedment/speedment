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
package com.speedment.codegen;

import com.speedment.codegen.control.AccessorImplementer;
import com.speedment.codegen.control.AutomaticDependencies;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.field.Field_;
import static com.speedment.codegen.model.Type_.STRING;
import com.speedment.codegen.model.block.Block_;
import com.speedment.codegen.model.class_.Class_;

import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.package_.Package_;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.codegen.view.java.JavaCodeGen;
import java.io.InputStream;

/**
 *
 * @author pemi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CodeUtil.tab("   ");

        Type_ type = new Type_(InputStream.class);

        final Statement_ s = new Statement_("int bar = 1");

        final Block_ block = new Block_().add(new Statement_("int foo=1")).add(new Statement_("int bar=1"));

        final Class_ class_ = new Class_()
                .package_("org.speedment.codegen.test")
                .javadocAdder().add("A test class for code generation. This class is just a dummy.").add()
                .add(Annotation_.DEPRECATED)
                .public_()
                .setName("TestClass")
                .add(new Field_(STRING, "foo").private_().final_())
                .add(new Field_(STRING, "bar").private_().final_())
                .add(new Method_(type, "getFooBar")
                        .javadocAdder().add("A freeking method.").throws_(NullPointerException.class, "if called").add()
                        .public_().final_()
                        .add(new Parameter_().final_().setType(STRING).setName("baz"))
                        .add(new Parameter_(STRING, "bazer"))
                        .add(new Statement_(
                                        "return (foo + baz + bar);"
                                ))
                        .add(Annotation_.OVERRIDE))
                .methodAdder().public_().setType(STRING).setName("bar").add(s).add()
                .methodAdder().public_().setType(STRING).setName("foo").add(new Statement_("int foo=1")).add()
                .methodAdder().public_().setType(STRING).setName("fooBar").add(new Statement_(block)).add();

        new AccessorImplementer().accept(class_);
        new AutomaticDependencies().accept(class_);

        JavaCodeGen gen = new JavaCodeGen();
        System.out.println(gen.on(class_).get());

    }

}
