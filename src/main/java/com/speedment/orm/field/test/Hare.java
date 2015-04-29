/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.field.test;

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
