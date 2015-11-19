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
    ObservableList<TreeItem<AbstractNodeProperty>> getCurrentSelection();   
}