/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

final class AnnotationExampleTest extends AbstractExample {

    @Override
    @Deprecated
    void onFile(File file) {

/*        final Annotation annotation = Annotation.of("Foo")
                .set(Javadoc.of("This is a test annotation.").add(DefaultJavadocTag.AUTHOR.setValue("tester").setText("the one and only")))
                .add()*/




        final Class clazz = Class.of("Foo")
                .add(AnnotationUsage.of(Foo.class).put("bar", Value.ofReference(
                        "@Bar(value = \"Mislav\")"
                )));

        file.add(clazz);
    }


    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Bar {
        String value() default "";
    }


    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Foo {
        Bar bar();
    }


    @Bar("Pero")
    @Foo(bar = @Bar(value = "Mislav"))
    class A{}

}