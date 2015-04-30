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
package com.speedment.orm.function;

import com.speedment.orm.field.test.Hare;
import com.speedment.orm.config.model.Column;
import java.util.function.ToIntFunction;

/**
 *
 * @author pemi
 */
@Deprecated
public interface HareField {

    static PredicateBuilder<Hare, String> name() {
//            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
//            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("name")).findAny().get();
        Column column = Column.newColumn();
        return new PredicateBuilder<>(column, Hare::getName);
    }

    static PredicateBuilder<Hare, String> color() {
//            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
//            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("color")).findAny().get();
        Column column = Column.newColumn();
        return new PredicateBuilder<>(column, Hare::getColor);
    }

    static IntPredicateBuilder<Hare> age() {
//            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
//            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("age")).findAny().get();
        Column column = Column.newColumn();
        ToIntFunction<Hare> getter = h -> h.getAge();
        return new IntPredicateBuilder<Hare>(column, getter);
    }

}
