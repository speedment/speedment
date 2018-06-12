package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;

import java.util.Objects;
import java.util.function.Consumer;

import static com.speedment.common.codegen.constant.DefaultType.isPrimitive;
import static com.speedment.common.codegen.model.modifier.Modifier.FINAL;
import static java.lang.String.format;

/**
 * Generates a public constructor with all the final fields as input
 * parameters.
 *
 * @author Emil Forslund
 * @since  2.5
 */
public final class AutoConstructor implements Consumer<Class> {

    @Override
    public void accept(Class aClass) {
        aClass.add(Constructor.newPublic()
            .call(constr -> aClass.getFields().stream()
                .filter(f -> f.getModifiers().contains(FINAL))
                .map(Field::copy)
                .peek(f -> f.getModifiers().clear())
                .forEachOrdered(f -> {
                    constr.add(f).imports(Objects.class, "requireNonNull");
                    if (isPrimitive(f.getType())) {
                        constr.add(format("this.%1$s = %1$s;", f.getName()));
                    } else {
                        constr.add(format("this.%1$s = requireNonNull(%1$s);", f.getName()));
                    }
                })
            )
        );
    }
}
