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
package com.speedment.orm.code.model.java;

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.DefaultType;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 * @param <T> Type of item to generate
 */
public abstract class BaseEntityAndManagerTranslator<T extends ClassOrInterface<T>> extends DefaultJavaClassTranslator<Table, T> {

//    private final CodeGenerator cg;

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

    protected final ClassType ENTITY = new ClassType("", "Impl"),
            BUILDER = new ClassType("Builder", "Impl"),
            CONFIG = new ClassType("Config", "Impl"),
            MANAGER = new ClassType("Manager", "Impl");

    protected final Generic GENERIC_OF_PK = Generic.of().add(typeOfPK()),
            GENERIC_OF_ENTITY = Generic.of().add(ENTITY.getType()),
            GENERIC_OF_MANAGER = Generic.of().add(MANAGER.getType()),
            GENERIC_OF_BUILDER = Generic.of().add(BUILDER.getType());

    public BaseEntityAndManagerTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
//        this.cg = cg;
    }

    protected Type typeOfPK() {
        final long pks = primaryKeyColumns().count();

        if (pks == 0) {
            throw new UnsupportedOperationException("Table '" + table().getName() + "' does not have a valid primary key.");
        }

        final Class<?> first = primaryKeyColumns().findAny().get().getColumn().getMapping();

        if (pks == 1) {
            return Type.of(first);
        } else {
            if (primaryKeyColumns().allMatch(c -> c.getColumn().getMapping().equals(first))) {
                return DefaultType.list(Type.of(first));
            } else {
                return DefaultType.list(DefaultType.WILDCARD);
            }
        }
    }
/*
    protected abstract String getFileName();

    @Override
    public File get() {
        final File file = new FileImpl(baseDirectoryName() + "/" + (isInImplPackage() ? "impl/" : "") + getFileName() + ".java");
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
    } */

//    @Override
//    public String packagePath() {
//        return super.packagePath() + "/" + table().getRelativeName(project());
//    }

}
