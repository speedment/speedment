package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.aspects.Child;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * @author Emil Forslund
 */
public final class UserInterfaceComponentImpl extends Apache2AbstractComponent implements UserInterfaceComponent {
    
    private final ObservableList<Node> properties;
    private final ObservableList<TreeItem<Child<?>>> currentSelection;
    
    public UserInterfaceComponentImpl(Speedment speedment) {
        super(speedment);
        properties       = observableArrayList();
        currentSelection = observableArrayList();
    }

    @Override
    public ObservableList<Node> propertiesProperty() {
        return properties;
    }
    
    @Override
    public ObservableList<TreeItem<Child<?>>> currentSelectionProperty() {
        return currentSelection;
    }
}