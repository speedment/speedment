package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;

import java.util.List;

final class InterfaceExampleTest extends AbstractExample {

    @Override
    void onFile(File file) {
        final Interface anInterface = Interface.of(simpleName())
                .public_()
                .set(Javadoc.of("This is a test interface.").add(DefaultJavadocTag.AUTHOR.setValue("tester").setText("the one and only")))
                .add(SimpleParameterizedType.create(List.class, String.class))
                .add(Field.of("NAME", String.class).set(Value.ofText("John")))
                .add(Method.of("three", int.class).default_().add("return 3;"))
                .add(Method.of("render", Object.class).add(Field.of("model", Field.class)))
                .add(Method.of("create", SimpleType.create(simpleName())).static_().add("return null;"))
                .add(Import.of(List.class));

        file.add(anInterface);
    }

}