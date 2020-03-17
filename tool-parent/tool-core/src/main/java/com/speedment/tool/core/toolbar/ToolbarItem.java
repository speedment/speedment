/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.toolbar;

import com.speedment.common.injector.annotation.Inject;
import javafx.scene.Node;

/**
 * One control in the 'toolbar'-section of the Speedment Tool. Implementations
 * of this interface can use the {@link Inject}-annotation, but does not have a
 * lifecycle.
 *
 * @param <T>  the type of JavaFX node that this produces
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public interface ToolbarItem<T extends Node> {

    /**
     * Creates and returns a new JavaFX-node to serve as this item in the
     * toolbar.
     *
     * @return  the new item
     */
    T createNode();

    /**
     * Returns the side on which this tool will appear. The default value is
     * {@link ToolbarSide#LEFT}.
     *
     * @return  the side to appear on
     */
    default ToolbarSide getSide() {
        return ToolbarSide.LEFT;
    }
}
