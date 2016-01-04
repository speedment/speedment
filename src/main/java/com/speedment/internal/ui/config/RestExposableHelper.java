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
    
    StringProperty restPathProperty();
    
    @Override
    default boolean isExposedInRest() {
        return exposedInRestProperty().getValue();
    }
    
    @External(type = Boolean.class, isVisibleInGui = false)
    @Override
    default void setExposedInRest(boolean exposed) {
        exposedInRestProperty().setValue(exposed);
    }
    
    @External(type = String.class, isVisibleInGui = false)
    @Override
    default String getRestPath() {
        return restPathProperty().getValue();
    }
    
    @External(type = String.class, isVisibleInGui = false)
    @Override
    default void setRestPath(String restPath) {
        restPathProperty().setValue(restPath);
    }
}