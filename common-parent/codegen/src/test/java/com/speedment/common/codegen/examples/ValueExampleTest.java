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

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

final class ValueExampleTest extends AbstractExample {

    @Override
    void onFile(File file) {
        final Interface anInterface = Interface.of(simpleName())
                .public_()
                .add(Field.of("text", String.class).set(Value.ofText("a")))
                .add(Field.of("number", Number.class).set(Value.ofNumber(1)))
                .add(Field.of("array", Integer[].class).set(Value.ofArray(Arrays.asList(Value.ofNumber(1), Value.ofNumber(2)))))
                .add(Field.of("emptyArray", Integer[].class).set(Value.ofArray()))
                .add(Field.of("epoch", long.class).set(Value.ofReference("System.currentTimeMillis()")))
                .add(Field.of("debug", boolean.class).set(Value.ofBoolean(true)))
                .add(Field.of("stream", SimpleParameterizedType.create(Stream.class, String.class)).set(Value.ofInvocation(Stream.class, "of", Value.ofText("A"), Value.ofText("B"))))
                .add(Field.of("nullable",  String.class).set(Value.ofNull()))
                .add(Field.of("function", SimpleParameterizedType.create(Function.class, Integer.class, String.class)).set(
                        Value.ofAnonymous(Function.class)
                                .add(SimpleType.create(""))
                                .add(
                                        Method.of("apply", String.class)
                                                .add(Field.of("val", Integer.class))
                                                .override()
                                                .add("return Integer.toString(val);")

                                )
                        )
                )
                .add(Field.of("charset",  Charset.class).set(Value.ofEnum(StandardCharsets.class, "UTF_8")))
                ;

        file.add(anInterface);
    }

}