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
