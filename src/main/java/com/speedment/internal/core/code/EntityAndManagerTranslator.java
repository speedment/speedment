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
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.internal.codegen.util.Formatting;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.constants.DefaultType;
import com.speedment.config.db.Table;
import com.speedment.exception.SpeedmentException;
import java.util.Optional;
import java.util.function.Function;
import static com.speedment.util.NullUtil.requireNonNulls;
import com.speedment.util.tuple.Tuple1;

/**
 *
 * @author pemi
 * @param <T> type of model to translate into
 */
public abstract class EntityAndManagerTranslator<T extends ClassOrInterface<T>>
        extends DefaultJavaClassTranslator<Table, T> {

    public final class ClassType {

        public final static String GENERATED_PACKAGE = "generated",
                GENERATED_PREFIX = "Generated";

        private final Type type;
        private final Type implType;
        private final Type generatedType;
        private final Type generatedImplType;
        private final Type optionalType;

        private ClassType(String typeSuffix, String implSuffix) {
            requireNonNulls(typeSuffix, implSuffix);

            this.type = Type.of(fullyQualifiedTypeName() + typeSuffix);
            this.implType = Type.of(fullyQualifiedTypeName() + typeSuffix + implSuffix);
            this.generatedType = Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + typeSuffix);
            this.generatedImplType = Type.of(fullyQualifiedTypeName(GENERATED_PACKAGE, GENERATED_PREFIX) + typeSuffix + implSuffix);
            this.optionalType = Type.of(Optional.class).add(Generic.of().add(type));
        }

        public Type getType() {
            return type;
        }

        public Type getImplType() {
            return implType;
        }

        public Type getGeneratedType() {
            return generatedType;
        }

        public Type getGeneratedImplType() {
            return generatedImplType;
        }

        public String getName() {
            return Formatting.shortName(type.getName());
        }

        public String getImplName() {
            return Formatting.shortName(implType.getName());
        }

        public String getGeneratedName() {
            return Formatting.shortName(generatedType.getName());
        }

        public String getGeneratedImplName() {
            return Formatting.shortName(generatedImplType.getName());
        }

        public Type getOptionalType() {
            return optionalType;
        }
    }

    protected final ClassType entity = new ClassType("", "Impl"),
            manager = new ClassType("Manager", "Impl");

    protected final Generic genericOfPk = Generic.of().add(typeOfPK()),
            genericOfEntity = Generic.of().add(entity.getType()),
            genericOfManager = Generic.of().add(manager.getType());

    protected EntityAndManagerTranslator(
            Speedment speedment,
            Generator gen,
            Table table,
            Function<String, T> modelConstructor) {

        super(speedment, gen, table, modelConstructor);
    }

    protected Type typeOfPK() {
        final long pks = primaryKeyColumns().count();

        if (pks == 0) {
            return DefaultType.list(DefaultType.WILDCARD);
        }

        final Class<?> first = primaryKeyColumns()
                .findFirst().get()
                .findColumn().get()
                .findTypeMapper().getJavaType();

        if (pks == 1) {
            return Type.of(first);
        } else if (primaryKeyColumns().allMatch(c -> c.findColumn().get()
                .findTypeMapper().getJavaType().equals(first))) {

            return DefaultType.list(Type.of(first));
        } else {
            return DefaultType.list(DefaultType.WILDCARD);
        }
    }

    protected Type pkTupleType() {
        final long pks = primaryKeyColumns().count();
        final Package package_ = Tuple1.class.getPackage();
        final String tupleClassName = package_.getName() + ".Tuple" + pks;
        final java.lang.Class<?> tupleClass;
        try {
            tupleClass = java.lang.Class.forName(tupleClassName);
        } catch (ClassNotFoundException cnf) {
            throw new SpeedmentException("Speedment does not support " + pks + " primary keys.", cnf);
        }
        final Type result = Type.of(tupleClass);
        primaryKeyColumns().forEachOrdered(pk -> {
            result.add(
                    Generic.of().add(
                            Type.of(java.lang.Class.class)
                            .add(Generic.of().add(
                                    Type.of(pk.findColumn().get().findTypeMapper().getJavaType()))
                            )
                    )
            );
        });
        return result;

    }

    public final ClassType entity() {
        return entity;
    }

    public final Generic genericOfEntity() {
        return genericOfEntity;
    }

    protected final Table tableOrThrow() {
        return table().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Table.class.getSimpleName() + " document."
        ));
    }

    protected final Schema schemaOrThrow() {
        return schema().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Schema.class.getSimpleName() + " document."
        ));
    }

    protected final Dbms dbmsOrThrow() {
        return dbms().orElseThrow(() -> new SpeedmentException(
                getClass().getSimpleName() + " must have a "
                + Dbms.class.getSimpleName() + " document."
        ));
    }
}
