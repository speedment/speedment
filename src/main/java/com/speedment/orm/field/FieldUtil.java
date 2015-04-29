package com.speedment.orm.field;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class FieldUtil {

    private FieldUtil() {
    }

    public static Column findColumn(Class<?> entityClass, String name) {
        final Table table = Platform.get().get(ManagerComponent.class).managerOf(entityClass).getTable();
        final Optional<Column> oColumn = table.streamOf(Column.class).filter(c -> c.getName().equals(name)).findAny();
        return oColumn.orElseThrow(() -> new IllegalStateException("A column named " + name + " can not be found in the table " + table.getName()));
    }

}
