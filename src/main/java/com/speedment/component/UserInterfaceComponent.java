/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import com.speedment.internal.newgui.ProjectTreeController;
import com.speedment.internal.newgui.output.Line;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
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
    ObservableList<TreeItem<AbstractNodeProperty>> getCurrentSelection();
    
    /**
     * Returns an observable list with all the output messages currently
     * visible in the output area. New messages can be added to this list or
     * removed safely.
     * <p>
     * Messages should ideally be created using the {@link Line} class, but
     * could be any node implementation.
     * 
     * @return  the currently visible output messages
     */
    ObservableList<Node> getOutputMessages();
    
    /**
     * Installs a new context menu builder that will be used in the 
     * {@link ProjectTreeController} of the GUI. This is useful for plugins
     * that require a custom menu to handle custom project tree nodes. If no
     * builder exists for a particular type of node, no menu will be displayed.
     * 
     * @param <NODE>       the implementation type of the node
     * @param nodeType     the interface main type of the node
     * @param menuBuilder  the builder to use
     */
    <NODE extends AbstractNodeProperty> void installContextMenu(Class<? super NODE> nodeType, ContextMenuBuilder<NODE> menuBuilder);
    
    /**
     * If a builder exists for the interface main type of the specified node,
     * it will be called and the result will be returned. If no builder exists,
     * an {@code empty} will be returned.
     * 
     * @param <NODE>    the implementation type of the node
     * @param treeCell  the tree cell that invoced the context menu
     * @param node      the node to create a context menu for
     * @return          the created context menu or {@code empty}
     */
    <NODE extends AbstractNodeProperty> Optional<ContextMenu> createContextMenu(TreeCell<AbstractNodeProperty> treeCell, NODE node);
    
    @FunctionalInterface
    interface ContextMenuBuilder<NODE extends AbstractNodeProperty> {
        Optional<ContextMenu> build(TreeCell<AbstractNodeProperty> treeCell, NODE node);
    }
}