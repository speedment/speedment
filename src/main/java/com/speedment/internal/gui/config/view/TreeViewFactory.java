package com.speedment.internal.gui.config.view;

import com.speedment.Speedment;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.aspects.Child;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.ConcurrentHashMap;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class TreeViewFactory {
    
    private final Speedment speedment;
    private final UserInterfaceComponent ui;
    private final Map<Class<?>, NodePropertyView<?>> propertyViews;
    
    public TreeViewFactory(Speedment speedment) {
        this.speedment     = requireNonNull(speedment);
        this.ui            = speedment.getUserInterfaceComponent();
        this.propertyViews = new ConcurrentHashMap<>();
    }
    
    public void bind() {
       ui.getCurrentSelection().addListener((ListChangeListener.Change<? extends TreeItem<Child<?>>> change) -> {
           if (change.wasAdded()) {
               ui.getProperties().clear();
               final List<? extends TreeItem<Child<?>>> additions = change.getAddedSubList();
               final TreeItem<Child<?>> item = additions.get(additions.size() - 1);
               final Child<?> child = item.getValue();
               final NodePropertyView<?> view = propertyViews.get(child.getInterfaceMainClass());
               // View should add listener directly rather than go from the outside.
           }
       });
    }
}