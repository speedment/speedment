package com.speedment.orm.field.test;

import com.speedment.orm.field.ints.IntField;
import com.speedment.orm.field.reference.string.StringReferenceField;
import static com.speedment.orm.field.FieldUtil.findColumn;

/**
 *
 * @author pemi
 */
public class HareField {

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
