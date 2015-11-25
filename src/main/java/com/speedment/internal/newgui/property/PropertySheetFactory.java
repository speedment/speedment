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
package com.speedment.internal.newgui.property;

import com.speedment.internal.gui.config.AbstractNodeProperty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet.Item;

/**
 *
 * @author Emil Forslund
 */
public final class PropertySheetFactory {
    
    private final Map<Class<?>, Function<AbstractNodeProperty, ObservableList<Item>>> constructors;
    
    public PropertySheetFactory() {
        this.constructors = new ConcurrentHashMap<>();
    }
    
    public PropertySheetFactory install(Class<?> type, Function<AbstractNodeProperty, ObservableList<Item>> propertylister) {
        this.constructors.put(type, propertylister);
        return this;
    }
        
    public ObservableList<Item> build(AbstractNodeProperty node) {
        return constructors.get(node.getInterfaceMainClass()).apply(node);
    }
}