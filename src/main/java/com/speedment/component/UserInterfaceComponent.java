package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.config.aspects.Child;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version="2.3")
public interface UserInterfaceComponent extends Component {

    @Override
    default Class<UserInterfaceComponent> getComponentClass() {
        return UserInterfaceComponent.class;
    }

    /**
     * Returns a observable and modifiable view of all the currently visible properties
     * in the workspace of the GUI. This can for an example be modified to show more
     * options.
     * <p>
     * The list will be modified by the GUI each time a new node in the tree is selected.
     * It is therefore recommended to only modify this component as a result of a
     * {@link #getCurrentSelection()} event.
     * 
     * @return  a view of the properties area
     */
    ObservableList<Node> getProperties();

    /**
     * Returns the currently selected nodes in the tree view of the GUI. This collection
     * could be listened to by extensions that want to react when the user change the focus
     * of the workspace.
     * 
     * @return  a view of the selected tree nodes
     */
    ObservableList<TreeItem<Child<?>>> getCurrentSelection();   
}