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
package com.speedment.orm.code.model.java.entity;

import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.codegen.lang.models.implementation.ClassImpl;
import com.speedment.codegen.lang.models.implementation.FileImpl;
import com.speedment.codegen.lang.models.implementation.InterfaceImpl;
import com.speedment.codegen.lang.models.implementation.JavadocImpl;
import com.speedment.codegen.lang.models.implementation.TypeImpl;
import com.speedment.orm.code.model.java.DefaultJavaClassTranslator;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import java.util.function.BiConsumer;

/**
 *
 * @author pemi
 */
public abstract class BaseEntityTranslator extends DefaultJavaClassTranslator<Table> {

    public class ClassType {
        //INTERFACE("","Impl"), BEAN(".Bean","BeanImpl"), BUILDER(".Builder","BuilderImpl");

        private ClassType(String typeName, String implTypeName) {
            this.type = new TypeImpl(typeName() + typeName);
            this.implType = new TypeImpl(typeName() + implTypeName);
        }

        private final Type type;
        private final Type implType;

        public Type getType() {
            return type;
        }

        public Type getImplType() {
            return implType;
        }

    }

    public final ClassType INTERFACE = new ClassType("", "Impl");
    public final ClassType BEAN = new ClassType(".Bean", "BeanImpl");
    public final ClassType BUILDER = new ClassType(".Builder", "BuilderImpl");

    public BaseEntityTranslator(Table configEntity) {
        super(configEntity);
    }

    protected abstract String getFileName();

    @Override
    public File get() {
        final File file = new FileImpl(packagePath().replace('.', '/') + "/" + (isInImplPackage() ? "impl/" : "") + getFileName() + ".java");
        final ClassOrInterface clazz = make();
        clazz.setJavadoc(getJavaDoc());
        file.add(clazz);
        return file;
    }

    protected abstract ClassOrInterface make();

    protected abstract String getJavadocRepresentText();

    protected Javadoc getJavaDoc() {
        return new JavadocImpl(getJavadocRepresentText() + " representing an entity (for example, a row) in the " + getConfigEntity().toString() + "." + GENERATED_JAVADOC_MESSAGE)
                .add(Default.AUTHOR.copy().setValue("Speedment"));
    }

    protected boolean isInImplPackage() {
        return false;
    }

    protected class BaseInterface extends InterfaceImpl {

        public BaseInterface(String name, BiConsumer<BaseInterface, Column> columnActor) {
            super(name);
            public_();
            columns().forEachOrdered(c -> columnActor.accept(this, c));
        }

        public Field fieldFor(Column c) {
            return Field.of(variableName(c), Type.of(c.getMappedClass()));
        }

    }

    protected class BaseClass extends ClassImpl {

        public BaseClass(String name, BiConsumer<BaseClass, Column> columnActor) {
            super(name);
            public_();
            columns().forEachOrdered(c -> columnActor.accept(this, c));
        }

        public Field fieldFor(Column c) {
            return Field.of(variableName(c), Type.of(c.getMappedClass()));
        }

        public Constructor emptyConstructor() {
            return Constructor.of().public_();
        }

        public Constructor copyConstructor() {
            return with(Constructor.of().public_().add(Field.of(variableName(), INTERFACE.getType()).final_()), constructor -> {
                columns().forEachOrdered(c -> {
                    constructor.add("set" + typeName(c) + "(" + variableName() + ".get" + typeName(c) + "());");
                });
            });

        }

    }

}
