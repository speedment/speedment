package com.speedment.internal.ui.config;

import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.trait.HasColumnProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.config.trait.HasOrdinalPositionProperty;
import java.util.Map;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class PrimaryKeyColumnProperty extends AbstractChildDocumentProperty<Table> 
    implements PrimaryKeyColumn, HasNameProperty, HasEnabledProperty, 
    HasOrdinalPositionProperty, HasColumnProperty {

    public PrimaryKeyColumnProperty(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.concat(
            HasColumnProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties()
        );
    }
}