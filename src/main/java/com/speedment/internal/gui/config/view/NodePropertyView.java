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
package com.speedment.internal.gui.config.view;

import com.speedment.config.Node;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;

/**
 *
 * @author Emil Forslund
 * @param <NODE>  the node type to build UI for
 */
public interface NodePropertyView<NODE extends AbstractNodeProperty> {
    
    /**
     * Creates and configures a new tree cell to represent the
     * specified node object.
     * 
     * @param node  the observable config node to represent
     * @return      the created tree cell
     */
    TreeCell<NODE> newTreeCell(NODE node);

}