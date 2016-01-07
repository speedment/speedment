package com.speedment.internal.ui.config;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Emil Forslund
 */
public final class SchemaProperty extends AbstractChildDocumentProperty<Dbms> 
    implements Schema, HasEnabledProperty, HasNameProperty, HasAliasProperty {

    public SchemaProperty(Dbms parent, Map data) {
        super(parent, data);
    }
    
    public final BooleanProperty defaultSchemaProperty() {
        return booleanPropertyOf(DEFAULT_SCHEMA);
    }

    @Override
    public BiFunction<Schema, Map<String, Object>, Table> tableConstructor() {
        return TableProperty::new;
    }
}