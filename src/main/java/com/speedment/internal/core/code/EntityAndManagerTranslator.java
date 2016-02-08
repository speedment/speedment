/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.code;

import com.speedment.Speedment;
import com.speedment.internal.codegen.util.Formatting;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.constants.DefaultType;
import com.speedment.internal.codegen.lang.models.implementation.GenericImpl;
import com.speedment.config.db.Table;
import java.util.Optional;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author     pemi
 * @param <T>  type of model to translate into
 */
public abstract class EntityAndManagerTranslator<T extends ClassOrInterface<T>> extends DefaultJavaClassTranslator<Table, T> {

    public final class ClassType {

        private ClassType(String typeName, String implTypeName) {
            requireNonNull(typeName);
            requireNonNull(implTypeName);
            this.type = Type.of(fullyQualifiedTypeName() + typeName);
            this.optionalType = Type.of(Optional.class).add(new GenericImpl().add(type));
            this.implType = Type.of(fullyQualifiedTypeName("impl") + typeName + implTypeName);

        }

        private final Type type;
        private final Type optionalType;
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

        public Type getOptionalType() {
            return optionalType;
        }
    }

    protected final ClassType entity = new ClassType("", "Impl"),
            builder = new ClassType("Builder", "Impl"),
            config = new ClassType("Config", "Impl"),
            manager = new ClassType("Manager", "Impl");

    protected final Generic genericOfPk = Generic.of().add(typeOfPK()),
            genericOfEntity = Generic.of().add(entity.getType()),
            genericOfManager = Generic.of().add(manager.getType());

    protected EntityAndManagerTranslator(Speedment speedment, Generator gen, Table doc, Function<String, T> modelConstructor) {
        super(speedment, gen, doc, modelConstructor);
    }

    protected Type typeOfPK() {
        final long pks = primaryKeyColumns().count();

        if (pks == 0) {
            return DefaultType.list(DefaultType.WILDCARD);
        }

        final Class<?> first = primaryKeyColumns().findFirst().get().findColumn().get().findTypeMapper().getJavaType();

        if (pks == 1) {
            return Type.of(first);
        } else {
            if (primaryKeyColumns().allMatch(c -> c.findColumn().get().findTypeMapper().getJavaType().equals(first))) {
                return DefaultType.list(Type.of(first));
            } else {
                return DefaultType.list(DefaultType.WILDCARD);
            }
        }
    }
    
    public final ClassType entity() {
        return entity;
    }
    
    public final Generic genericOfEntity() {
        return genericOfEntity;
    }
}