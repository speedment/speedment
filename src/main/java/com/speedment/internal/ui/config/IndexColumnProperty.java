package com.speedment.internal.ui.config;

import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.internal.ui.config.trait.HasColumnProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.config.trait.HasOrderTypeProperty;
import com.speedment.internal.ui.config.trait.HasOrdinalPositionProperty;
import java.util.Map;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class IndexColumnProperty extends AbstractChildDocumentProperty<Index> 
    implements IndexColumn, HasNameProperty, HasOrdinalPositionProperty,
    HasOrderTypeProperty, HasColumnProperty {
    
    public IndexColumnProperty(Index parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.concat(
            HasColumnProperty.super.getUiVisibleProperties(),
            HasOrderTypeProperty.super.getUiVisibleProperties()
        );
    }
}