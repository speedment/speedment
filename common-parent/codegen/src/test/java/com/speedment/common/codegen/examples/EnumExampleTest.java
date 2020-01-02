package com.speedment.common.codegen.examples;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Enum;

import java.util.List;

final class EnumExampleTest extends AbstractExample {

    @Override
    void onFile(File file) {
        final Enum anEnum = Enum.of(simpleName())
                .public_()
                .set(Javadoc.of("This is a test enum.").add(DefaultJavadocTag.AUTHOR.setValue("tester").setText("the one and only")))
                .add(SimpleParameterizedType.create(List.class, String.class))
                .add(Field.of("NAME", String.class).set(Value.ofText("John")).private_().static_().final_())
                .add(Method.of("three", int.class).default_().add("return 3;"))
                .add(Method.of("render", Object.class).add(Field.of("model", Field.class)).public_())
                .add(Method.of("create", SimpleType.create(simpleName())).static_().add("return null;"))
                .add(EnumConstant.of("A").set(Javadoc.of("A constant")).add(Value.ofText("a")))
                .add(EnumConstant.of("B").set(Javadoc.of("B constant")).add(Value.ofText("b")))
                .add(Field.of("text", String.class).private_().final_())
                .add(Constructor.of().add(Field.of("text", String.class)).add("this.text = text;").private_())
                .add(Initializer.of().add("int a = 1;"))
                .add(Import.of(List.class));

        file.add(anEnum);
    }

}