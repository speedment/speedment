package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.SCHEMAS;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class SchemaProperty extends AbstractChildDocumentProperty<Dbms> 
    implements Schema, HasEnabledProperty, HasNameProperty, HasAliasProperty {

    public SchemaProperty(Dbms parent, Map data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            HasAliasProperty.super.getUiVisibleProperties(),
            Stream.of(
                new BooleanPropertyItem(
                    defaultSchemaProperty(),       
                    "Is Default Schema",
                    "If this is the default schema that should be used if none other is specified."
                )
            )
        ).flatMap(s -> s);
    }
    
    public final BooleanProperty defaultSchemaProperty() {
        return booleanPropertyOf(DEFAULT_SCHEMA);
    }

    @Override
    public BiFunction<Schema, Map<String, Object>, Table> tableConstructor() {
        return TableProperty::new;
    }
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case TABLES : return new TableProperty(this, data);
            default     : return super.createDocument(key, data);
        }
    }
}