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
package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.Objects.requireNonNull;

/**
 * Control that is used to generate setters, getters and adders for fields in
 * the specified class.
 *
 * @author Emil Forslund
 */
public final class SetGetAdd implements Consumer<File> {

    private static final String RETURN = "return";
    private static final String THIS = "this";
    public static final String OF_THIS = " of this ";

    private final BiPredicate<Field, Method> onlyInclude;

    /**
     * Initializes the control with all methods included.
     */
    public SetGetAdd() {
        onlyInclude = (f, m) -> true;
    }

    /**
     * Initializes the control but with only some {@link Method Methods}
     * included. The input of the <code>BiPredicate</code> will be the field
     * in the class that the method is generated for and the suggested method
     * to add to the class.
     *
     * @param onlyInclude a filter for methods to include
     */
    public SetGetAdd(BiPredicate<Field, Method> onlyInclude) {
        this.onlyInclude = requireNonNull(onlyInclude);
    }

    /**
     * Generates getters, setters and adders for the fields in the specified
     * model and sets the fields to private. Method signatures that has been
     * excluded in the construction of this control will be excluded from the
     * generation.
     * <p>
     * For fields of a subtype to {@link Collection}, an 'adder' will be
     * generated instead of a 'setter'.
     * <p>
     * For fields of type {@link Optional}, the inner type will be used in the
     * setter parameter but the wrapped type will be used as output for the
     * getter.
     *
     * @param file the model to generate for
     */
    @Override
    public void accept(File file) {
        requireNonNull(file);
        file.getClasses().stream()
            .filter(Class.class::isInstance)
            .map(Class.class::cast)
            .forEach(c -> accept(file, c));
    }

    private void accept(File file, Class model) {
        requireNonNull(model);
        final Type self;

        if (model.getGenerics().isEmpty()) {
            self = SimpleType.create(model.getName());
        } else {
            self = SimpleParameterizedType.create(
                model.getName(),
                model.getGenerics().stream()
                    .map(Generic::asType)
                    .toArray(Type[]::new)
            );
        }

        model.getFields().forEach(f -> {
            f.private_();

            if (isCollection(f.getType())) {
                generateAdder(model, self, f);
            } else {
                generateSetter(model, self, f);
            }

            final Method get = Method.of("get" + ucfirst(f.getName()), f.getType())
                .set(Javadoc.of()
                    .setText("Gets the " + f.getName() + OF_THIS + shortName(model.getName()) + ".")
                    .add(JavadocTag.of(RETURN, "the " + f.getName()))
                ).public_()
                .add(RETURN + " " + THIS + "." + f.getName() + ";");

            if (onlyInclude.test(f, get)) {
                model.add(get);
            }
        });

        // Recurse into subclasses
        model.getClasses().stream()
            .filter(Class.class::isInstance)
            .map(Class.class::cast)
            .forEach(c -> accept(file, c));
    }

    /**
     * Generates a setter for the field in the specified
     * class and sets the fields to private. Method signatures that has been
     * excluded in the construction of this control will be excluded from the
     * generation.
     * <p>
     * For fields of type {@link Optional}, the inner type will be used in the
     * setter parameter but the wrapped type will be used as output for the
     * getter.
     *
     * @param model the model to generate for
     * @param self the type to generate for
     * @param f the field to generate for
     *
     */
    private void generateSetter(Class model, Type self, Field f) {
        final Method set = Method.of("set" + ucfirst(f.getName()), self)
            .set(Javadoc.of()
                .setText("Sets the " + f.getName() + OF_THIS + shortName(model.getName()) + ".")
                .add(JavadocTag.of("param", f.getName(), "the new value"))
                .add(JavadocTag.of(RETURN, "a reference to this object"))
            ).public_();

        if (isOptional(f.getType())) {
            // Optional is a parameterized type.
            final ParameterizedType paramType = (ParameterizedType) f.getType();

            set.add(Field.of(f.getName(), paramType.getActualTypeArguments()[0]))
                .add(THIS + "." + f.getName() + " = Optional.of(" + f.getName() + ");")
                .add(RETURN + " this;");
        } else {
            set.add(Field.of(f.getName(), f.getType()))
                .add(THIS + "." + f.getName() + " = " + f.getName() + ";")
                .add(RETURN + " " + THIS + ";");
        }

        if (onlyInclude.test(f, set)) {
            model.add(set);
        }
    }

    /**
     * Generates an adder for the field in the specified
     * class and sets the fields to private. Method signatures that has been
     * excluded in the construction of this control will be excluded from the
     * generation.
     * <p>
     *
     * @param model the model to generate for
     * @param self the type to generate for
     * @param f the field to generate for
     *
     */
    private void generateAdder(Class model, Type self, Field f) {
        f.final_();

        // All collections are parameterized types.
        final ParameterizedType paramType = (ParameterizedType) f.getType();

        final Field param = Field.of(singular(f.getName()), paramType.getActualTypeArguments()[0]);
        final Method add = Method.of("addTo" + ucfirst(f.getName()), self)
            .set(Javadoc.of()
                .setText("Adds the specified " + lcfirst(shortName(param.getType().getTypeName())) + " to the " + f.getName() + OF_THIS + shortName(model.getName()) + ".")
                .add(JavadocTag.of("param", param.getName(), "the new value"))
                .add(JavadocTag.of(RETURN, "a reference to this object"))
            ).public_()
            .add(param)
            .add(THIS + "." + f.getName() + ".add(" + param.getName() + ");")
            .add(RETURN + " " + THIS + ";");

        if (onlyInclude.test(f, add)) {
            model.add(add);
        }
    }

    /**
     * Checks if the specified type is a java {@link Collection}.
     *
     * @param type the type to check
     * @return <code>true</code> if collection, else <code>false</code>
     */
    private boolean isCollection(Type type) {
        try {
            final java.lang.Class<?> clazz = java.lang.Class.forName(type.getTypeName());
            return Collection.class.isAssignableFrom(clazz);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if the specified type is a java {@link Optional}.
     *
     * @param type the type to check
     * @return <code>true</code> if Optional, else <code>false</code>
     */
    private boolean isOptional(Type type) {
        return type.getTypeName().startsWith("java.lang.Optional");
    }

    /**
     * Returns the specified word in singular.
     *
     * @param word the word
     * @return the word in singular
     */
    private String singular(String word) {
        if (requireNonNull(word).endsWith("ies")) {
            return word.substring(0, word.length() - 3) + "y";
        } else if (word.endsWith("s")) {
            return word.substring(0, word.length() - 1);
        } else {
            return word;
        }
    }
}