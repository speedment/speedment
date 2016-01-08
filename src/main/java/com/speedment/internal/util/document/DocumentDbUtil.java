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
package com.speedment.internal.util.document;

import com.speedment.Speedment;
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.exception.SpeedmentException;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class DocumentDbUtil {

    public static DbmsType dbmsTypeOf(Speedment speedment, Dbms dbms) {
        return speedment.getDbmsHandlerComponent().findByName(dbms.getTypeName())
                .orElseThrow(() -> new SpeedmentException("Unable to find the database type "+dbms.getTypeName()));
    }
    
    public static <T> Stream<T> traverseOver(Project project, Class<T> clazz) {
        if (Dbms.class.isAssignableFrom(clazz)) {
            return project.dbmses().map(clazz::cast);
        } else {
            return project.dbmses().flatMap(dbms -> traverseOver(dbms, clazz));
        }
    }

    public static <T> Stream<T> traverseOver(Dbms dbms, Class<T> clazz) {
        if (Schema.class.isAssignableFrom(clazz)) {
            return dbms.schemas().map(clazz::cast);
        } else {
            return dbms.schemas().flatMap(schema -> traverseOver(schema, clazz));
        }
    }

    public static <T> Stream<T> traverseOver(Schema schema, Class<T> clazz) {
        if (Table.class.isAssignableFrom(clazz)) {
            return schema.tables().map(clazz::cast);
        } else {
            return schema.tables().flatMap(table -> traverseOver(table, clazz));
        }
    }

    public static <T> Stream<T> traverseOver(Table table, Class<T> clazz) {
        if (Column.class.isAssignableFrom(clazz)) {
            return table.columns().map(clazz::cast);
        } else if (PrimaryKeyColumn.class.isAssignableFrom(clazz)) {
            return table.primaryKeyColumns().map(clazz::cast);
        } else if (Index.class.isAssignableFrom(clazz)) {
            return table.indexes().map(clazz::cast);
        } else if (ForeignKey.class.isAssignableFrom(clazz)) {
            return table.foreignKeys().map(clazz::cast);
        } else {
            final Stream.Builder<T> sb = Stream.builder();
            table.columns().flatMap(c -> traverseOver(c, clazz)).forEachOrdered(sb::accept);
            table.primaryKeyColumns().flatMap(c -> traverseOver(c, clazz)).forEachOrdered(sb::accept);
            table.indexes().flatMap(c -> traverseOver(c, clazz)).forEachOrdered(sb::accept);
            table.foreignKeys().flatMap(c -> traverseOver(c, clazz)).forEachOrdered(sb::accept);
            return sb.build();
        }
    }

    public static <T> Stream<T> traverseOver(Column column, Class<T> clazz) {
        return Stream.empty();
    }

    public static <T> Stream<T> traverseOver(PrimaryKeyColumn pkColumn, Class<T> clazz) {
        return Stream.empty();
    }

    public static <T> Stream<T> traverseOver(Index index, Class<T> clazz) {
        if (IndexColumn.class.isAssignableFrom(clazz)) {
            return index.indexColumns().map(clazz::cast);
        } else {
            return index.indexColumns().flatMap(ic -> traverseOver(ic, clazz));
        }
    }

    public static <T> Stream<T> traverseOver(IndexColumn indexColumn, Class<T> clazz) {
        return Stream.empty();
    }

    public static <T> Stream<T> traverseOver(ForeignKey fk, Class<T> clazz) {
        if (ForeignKeyColumn.class.isAssignableFrom(clazz)) {
            return fk.foreignKeyColumns().map(clazz::cast);
        } else {
            return fk.foreignKeyColumns().flatMap(fcc -> traverseOver(fcc, clazz));
        }
    }

    public static <T> Stream<T> traverseOver(ForeignKeyColumn foreignKeyColumn, Class<T> clazz) {
        return Stream.empty();
    }


//    public static Class<? extends Document> mainInterfaceClass(Document document) {
//        return Stream.of(
//                Column.class,
//                Dbms.class,
//                ForeignKey.class,
//                ForeignKeyColumn.class,
//                Index.class,
//                IndexColumn.class,
//                PrimaryKeyColumn.class,
//                Project.class,
//                Schema.class,
//                Table.class
//        )
//                .filter(c -> c.isAssignableFrom(document.getClass()))
//                .findAny()
//                .orElseThrow(() -> new SpeedmentException("Unable to find main interface for " + document));
//
//    }

//    
//    
//    public static <T, P, C, B> Stream<T> traverseOver(
//            P parent,
//            Class<T> clazz,
//            Function<P, Stream<B>> streamer,
//            Class<C> childClass,
//            Function<B, Stream<T>> recursor
//    ) {
//       if (childClass.isAssignableFrom(clazz)) {
//            return streamer.apply(parent).map(clazz::cast);
//        } else {
//           return streamer.apply(parent).flatMap(recursor);
//        }
//    }
    /**
     * Utility classes should not be instantiated.
     */
    private DocumentDbUtil() {
        instanceNotAllowed(getClass());
    }
}
