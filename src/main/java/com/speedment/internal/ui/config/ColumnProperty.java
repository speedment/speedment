package com.speedment.internal.ui.config;

import com.speedment.config.db.Column;
import static com.speedment.config.db.Column.AUTO_INCREMENT;
import static com.speedment.config.db.Column.DATABASE_TYPE;
import static com.speedment.config.db.Column.NULLABLE;
import static com.speedment.config.db.Column.TYPE_MAPPER;
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnProperty extends AbstractChildDocumentProperty<Table> 
    implements Column, HasEnabledProperty, HasNameProperty, HasAliasProperty {
    
    public ColumnProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }

    public final BooleanProperty nullableProperty() {
        return booleanPropertyOf(NULLABLE);
    }

    public final BooleanProperty autoIncrementProperty() {
        return booleanPropertyOf(AUTO_INCREMENT);
    }
 
    public final StringProperty typeMapperProperty() {
        return stringPropertyOf(TYPE_MAPPER);
    }
  
    public final ObjectBinding<TypeMapper<?, ?>> typeMapperObjectProperty() {
        return createObjectBinding(this::findTypeMapper, typeMapperProperty());
    }

    public final StringProperty databaseTypeProperty() {
        return stringPropertyOf(DATABASE_TYPE);
    }

    public final ObjectBinding<Class<?>> databaseTypeObjectProperty() {
        return createObjectBinding(this::findDatabaseType, databaseTypeProperty());
    }
}