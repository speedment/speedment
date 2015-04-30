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
package com.speedment.orm.field.test;

import com.speedment.orm.field.ints.IntField;
import com.speedment.orm.field.reference.string.StringReferenceField;
import static com.speedment.orm.field.FieldUtil.findColumn;

/**
 *
 * @author pemi
 */
public final class HareField {

    private HareField() {
    }

    // TODO: Use Supplier<Column> to escape from static misery
    public static final StringReferenceField<Hare> NAME = new StringReferenceField<>(() -> findColumn(Hare.class, "name"), Hare::getName);
    public static final StringReferenceField<Hare> COLOR = new StringReferenceField<>(() -> findColumn(Hare.class, "color"), Hare::getColor);
    public static IntField<Hare> AGE = new IntField<>(() -> findColumn(Hare.class, "age"), Hare::getAge);

//
//static IntPredicateBuilder<Hare> age() {
////            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
////            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("age")).findAny().get();
//        Column column = Column.newColumn();
//        ToIntFunction<Hare> getter = h -> h.getAge();
//        return new IntPredicateBuilder<Hare>(column, getter);
//    }
}
