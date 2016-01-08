package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyProperty extends AbstractChildDocumentProperty<Table> 
    implements ForeignKey, HasEnabledProperty, HasNameProperty {

    public ForeignKeyProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.concat(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties()
        );
    }

    @Override
    public BiFunction<ForeignKey, Map<String, Object>, ForeignKeyColumnProperty> foreignKeyColumnConstructor() {
        return ForeignKeyColumnProperty::new;
    }

    @Override
    public Stream<ForeignKeyColumnProperty> foreignKeyColumns() {
        return (Stream<ForeignKeyColumnProperty>) ForeignKey.super.foreignKeyColumns();
    }
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case FOREIGN_KEY_COLUMNS : return new ForeignKeyColumnProperty(this, data);
            default                  : return super.createDocument(key, data);
        }
    }
}