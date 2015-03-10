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

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.controller.AutoImports;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.AUTHOR;
import com.speedment.codegen.lang.models.implementation.FileImpl;
import com.speedment.codegen.lang.models.implementation.JavadocImpl;
import com.speedment.orm.code.model.java.DefaultJavaClassTranslator;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 * @param <T> Type of item to generate
 */
public abstract class BaseEntityTranslator<T extends ClassOrInterface<T>> extends DefaultJavaClassTranslator<Table> {

    private final CodeGenerator cg;

    public class ClassType {

        private ClassType(String typeName, String implTypeName) {
            this.type = Type.of(fullyQualifiedTypeName() + typeName);
            this.implType = Type.of(fullyQualifiedTypeName("impl") + typeName + implTypeName);
        }

        private final Type type;
        private final Type implType;

        public Type getType() {
            return type;
        }

        public Type getImplType() {
            return implType;
        }

        public String getName() {
            return Formatting.shortName(type.getName());
        }

        public String getImplName() {
            return Formatting.shortName(implType.getName());
        }
    }

    protected final ClassType 
        ENTITY = new ClassType("", "Impl"),
        BUILDER = new ClassType("Builder", "Impl"),
        CONFIG = new ClassType("Config", "Impl"),
        MANAGER = new ClassType("Manager", "Impl");
    
    protected final Generic 
        GENERIC_OF_ENTITY = Generic.of().add(ENTITY.getType()),
        GENERIC_OF_MANAGER = Generic.of().add(MANAGER.getType()),
        GENERIC_OF_BUILDER = Generic.of().add(BUILDER.getType());

    public BaseEntityTranslator(CodeGenerator cg, Table configEntity) {
        super(configEntity);
        this.cg = cg;
    }

    protected abstract String getFileName();

    @Override
    public File get() {
        final File file = new FileImpl(packagePath().replace('.', '/') + "/" + (isInImplPackage() ? "impl/" : "") + getFileName() + ".java");
        final T item = make(file);
        item.set(getJavaDoc());
        file.add(item);
        file.call(new AutoImports(cg.getDependencyMgr()));
        return file;
    }

    protected abstract T make(File file);

    protected abstract String getJavadocRepresentText();

    protected Javadoc getJavaDoc() {
        return new JavadocImpl(getJavadocRepresentText() + " representing an entity (for example, a row) in the " + getNode().toString() + "." + GENERATED_JAVADOC_MESSAGE)
            .add(AUTHOR.setValue("Speedment"));
    }

    public CodeGenerator getCodeGenerator() {
        return cg;
    }

    protected boolean isInImplPackage() {
        return false;
    }

}
