package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import static com.speedment.config.db.Dbms.SCHEMAS;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.Table;
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
public final class IndexProperty extends AbstractChildDocumentProperty<Table> 
    implements Index, HasEnabledProperty, HasNameProperty {

    public IndexProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            Stream.of(
                new BooleanPropertyItem(
                    uniqueProperty(),       
                    "Is Unique",
                    "True if elements in this index are unique."
                )
            )
        ).flatMap(s -> s);
    }
    
    public final BooleanProperty uniqueProperty() {
        return booleanPropertyOf(UNIQUE);
    }

    @Override
    public BiFunction<Index, Map<String, Object>, IndexColumn> indexColumnConstructor() {
        return IndexColumnProperty::new;
    }
    
    @Override
    protected final Document createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case INDEX_COLUMNS : return new IndexColumnProperty(this, data);
            default            : return super.createDocument(key, data);
        }
    }
}