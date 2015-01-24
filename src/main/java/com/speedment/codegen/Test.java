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
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.field.Field_;
import static com.speedment.codegen.model.Type_.STRING;
import com.speedment.codegen.model.class_.Class_;
import static com.speedment.codegen.model.modifier.ClassModifier_.*;
import com.speedment.codegen.model.package_.Package_;
import com.speedment.codegen.view.java.JavaCodeGen;

/**
 *
 * @author pemi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Package_ package_ = new Package_();

        final Field_ field = new Field_(STRING, "foo").private_();

        final Class_ class_ = new Class_();
        class_.add(field);
        class_.add(new Field_(STRING, "bar"));
        class_.add(PUBLIC, STATIC);

        new AccessorImplementer().apply(class_);

        final Class_ c2 = new Class_();
        c2.set(of("private static final"));

        c2.add(Annotation_.DEPRECATED);

        JavaCodeGen gen = new JavaCodeGen();

        Field_ f = new Field_(STRING, "olle");

        System.out.println(gen.on(f));

    }

}
