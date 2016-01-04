package com.speedment.event;

import com.speedment.annotation.Api;
import com.speedment.internal.ui.config.AbstractNodeProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.PropertySheet;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
@Api(version="2.4")
public final class TreeSelectionChange implements Event {

    private final ListChangeListener.Change<? extends TreeItem<AbstractNodeProperty>> changeEvent;
    private final ObservableList<PropertySheet.Item> properties;

    public TreeSelectionChange(
            ListChangeListener.Change<? extends TreeItem<AbstractNodeProperty>> changeEvent, 
            ObservableList<PropertySheet.Item> properties) {
        
        this.changeEvent = requireNonNull(changeEvent);
        this.properties  = requireNonNull(properties);
    }
    
    public ListChangeListener.Change<? extends TreeItem<AbstractNodeProperty>> changeEvent() {
        return changeEvent;
    }
    
    public ObservableList<PropertySheet.Item> properties() {
        return properties;
    }
}