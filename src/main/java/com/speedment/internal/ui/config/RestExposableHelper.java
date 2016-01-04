package com.speedment.internal.ui.config;

import com.speedment.annotation.External;
import com.speedment.config.aspects.RestExposable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pemi
 */
public interface RestExposableHelper extends RestExposable {
    
    BooleanProperty exposedInRestProperty();
    
    @Override
    default boolean isExposedInRest() {
        return exposedInRestProperty().getValue();
    }
    
    @External(type = Boolean.class, isVisibleInGui = false)
    @Override
    default void setExposedInRest(boolean exposed) {
        exposedInRestProperty().setValue(exposed);
    }
}