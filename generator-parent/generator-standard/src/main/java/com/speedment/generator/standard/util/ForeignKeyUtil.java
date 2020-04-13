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
package com.speedment.generator.standard.util;

import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.internal.util.InternalForeignKeyUtil;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Table;

import java.lang.reflect.Type;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class ForeignKeyUtil {

    private ForeignKeyUtil() {}

    public static class ReferenceFieldType {

        private final Type type;

        public ReferenceFieldType(Type type) {
            this.type     = requireNonNull(type);
        }

        public Type getType() {
            return type;
        }
    }

    public static ReferenceFieldType getReferenceFieldType(
        final File file,
        final Table table,
        final Column column,
        final Type entityType,
        final Injector injector
    ) {
        requireNonNull(file);
        requireNonNull(table);
        requireNonNull(column);
        requireNonNull(entityType);
        requireNonNull(injector);
        return InternalForeignKeyUtil.getReferenceFieldType(file,table,column,entityType,injector);
    }

    public static Optional<? extends ForeignKeyColumn> getForeignKey(Table table, Column column) {
        requireNonNull(table);
        requireNonNull(column);
        return InternalForeignKeyUtil.getForeignKey(table, column);
    }

}
