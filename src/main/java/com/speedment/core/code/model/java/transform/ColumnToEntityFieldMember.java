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
package com.speedment.core.code.model.java.transform;

import static com.speedment.codegen.util.Formatting.shortName;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.values.ReferenceValue;
import static com.speedment.core.code.model.java.TransformUtil.importType;
import static com.speedment.core.code.model.java.TransformUtil.typeUsing;
import com.speedment.core.config.model.Column;
import com.speedment.core.field.FieldUtil;
import com.speedment.core.field.reference.ComparableReferenceField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.string.StringReferenceField;
import static com.speedment.util.java.JavaLanguage.javaStaticFieldName;
import static com.speedment.util.java.JavaLanguage.javaTypeName;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class ColumnToEntityFieldMember implements Transform<Column, Field> {

    @Override
    public Optional<Field> transform(Generator gen, Column column) {
        final Type refType = getReferenceFieldType(gen, column);

        final Type entityType = typeUsing(gen, column, TableToEntityType.class);
        final String shortEntityName = shortName(entityType.getName());
        
        importType(gen, Import.of(entityType));
        importType(gen, Import.of(Type.of(FieldUtil.class)).static_().setStaticMember("findColumn"));

        return Optional.of(
            Field.of(javaStaticFieldName(column.getName()), refType)
            .public_().final_().static_()
            .set(new ReferenceValue(
                "new " + shortName(refType.getName()) + 
                "<>(() -> findColumn(" + 
                        shortEntityName + ".class, \"" + 
                        column.getName() + 
                    "\"), " + 
                    shortEntityName + "::get" + 
                    javaTypeName(column.getName()) + 
                ")"
            ))
        );
    }

    private Type getReferenceFieldType(Generator gen, Column column) {
        final Class<?> mapping = column.getMapping();
        final Type entityType = typeUsing(gen, column, TableToEntityType.class);

        final Type fieldType;
        if (String.class.equals(mapping)) {
            fieldType = Type.of(StringReferenceField.class)
                .add(Generic.of().add(entityType));
        } else if (Comparable.class.isAssignableFrom(mapping)) {
            fieldType = Type.of(ComparableReferenceField.class)
                .add(Generic.of().add(entityType))
                .add(Generic.of().add(Type.of(mapping)));
        } else {
            fieldType = Type.of(ReferenceField.class)
                .add(Generic.of().add(entityType))
                .add(Generic.of().add(Type.of(mapping)));
        }
        
        return fieldType;
    }
}
