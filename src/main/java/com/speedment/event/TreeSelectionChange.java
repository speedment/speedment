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
package com.speedment.event;

import com.speedment.annotation.Api;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.PropertySheet;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
@Api(version="2.4")
public final class TreeSelectionChange implements Event {

    private final ListChangeListener.Change<? extends TreeItem<DocumentProperty>> changeEvent;
    private final ObservableList<PropertySheet.Item> properties;

    public TreeSelectionChange(
            ListChangeListener.Change<? extends TreeItem<DocumentProperty>> changeEvent, 
            ObservableList<PropertySheet.Item> properties) {
        
        this.changeEvent = requireNonNull(changeEvent);
        this.properties  = requireNonNull(properties);
    }
    
    public ListChangeListener.Change<? extends TreeItem<DocumentProperty>> changeEvent() {
        return changeEvent;
    }
    
    public ObservableList<PropertySheet.Item> properties() {
        return properties;
    }
}