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
package com.speedment.internal.newgui;

import com.speedment.exception.SpeedmentException;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import com.speedment.internal.newgui.property.AbstractPropertyItem;
import com.speedment.internal.newgui.util.UILoader;
import com.speedment.internal.newgui.util.UISession;
import com.speedment.stream.MapStream;
import java.net.URL;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class WorkspaceController implements Initializable {
    
    private final UISession session;
    private final ObservableList<PropertySheet.Item> properties;
    
    private @FXML StackPane workspace;
    
    private WorkspaceController(UISession session) {
        this.session    = requireNonNull(session);
        this.properties = FXCollections.observableArrayList();
        
        session.getSpeedment()
            .getUserInterfaceComponent()
            .getCurrentSelection()
            .addListener((ListChangeListener.Change<? extends TreeItem<AbstractNodeProperty>> change) -> {
                properties.clear();
                
                MapStream.fromValues(
                    change.getList().stream()
                        .map(TreeItem::getValue)
                        .flatMap(node -> node.getGuiVisibleProperties()),
                    property -> property.getName()
                ).groupingBy(item -> item.getName())
                 .mapValue(list ->
                     list.stream().reduce((a, b) -> {
                         if (a == null || b == null) {
                             return null;
                         } else {
                             if (Objects.equals(a.getValue(), b.getValue())) {
                                 return a;
                             } else return null;
                         }
                     }).orElse(null)
                 ).values().forEach(properties::add);
            });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        final PropertySheet sheet = new PropertySheet(properties);
        
        sheet.setMode(PropertySheet.Mode.CATEGORY);
        sheet.setModeSwitcherVisible(true);
        sheet.setSearchBoxVisible(true);
        sheet.setPropertyEditorFactory(item -> {
            if (item instanceof AbstractPropertyItem<?, ?>) {
                @SuppressWarnings("unchecked")
                final AbstractPropertyItem<?, ?> casted = (AbstractPropertyItem<?, ?>) item;
                return casted.createEditor();
            } else throw new SpeedmentException(
                "Unknown property item type '" + item.getClass() + "'."
            );
        });
        
        workspace.getChildren().add(sheet);
    }
    
    public static Node create(UISession session) {
        return UILoader.create(session, "Workspace", WorkspaceController::new);
	}
}