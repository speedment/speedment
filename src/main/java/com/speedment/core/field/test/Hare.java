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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.core.field.test;

/**
 *
 * @author pemi
 */
public interface Hare {

    String getName();

    String getColor();

    int getAge();

//    public interface Field {
//
//        static ComparableField<Hare, String> name() {
//            BiConsumer<HareBuilder, String> setter = (e, v) -> e.setName(v);
//            return new ComparableField<>(Hare::getName, HareBuilder);
//        }
//
//        static PredicateBuilder<Hare, String> color() {
////            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
////            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("color")).findAny().get();
//            Column column = Column.newColumn();
//            return new PredicateBuilder<>(column, Hare::getColor);
//        }
//
//        static IntPredicateBuilder<Hare> age() {
////            Table table = Platform.get().get(ManagerComponent.class).managerOf(Hare.class).getTable();
////            Column column = table.streamOf(Column.class).filter(c -> c.getName().equals("age")).findAny().get();
//            Column column = Column.newColumn();
//            ToIntFunction<Hare> getter = h -> h.getAge();
//            return new IntPredicateBuilder<Hare>(column, getter);
//        }
//
//    }

}
