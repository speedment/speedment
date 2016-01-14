/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.controller;

import com.speedment.component.EventComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.event.PreviewEvent;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.TableProperty;
import com.speedment.internal.ui.config.preview.PreviewNode;
import static com.speedment.internal.util.JavaLanguage.javaTypeName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import static java.util.Objects.requireNonNull;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.layout.VBox;
import javax.annotation.Generated;

/**
 *
 * @author Emil Forslund
 */
public final class PreviewController implements Initializable {
    
    private final UISession session;

    private @FXML TitledPane workspace;
    private @FXML VBox nodes;
    
    private PreviewController(UISession session) {
        this.session = requireNonNull(session);
        
        final UserInterfaceComponent ui = session.getSpeedment().getUserInterfaceComponent();
        final EventComponent events = session.getSpeedment().getEventComponent();
        
        ui.getSelectedTreeItems()
            .addListener((ListChangeListener.Change<? extends TreeItem<DocumentProperty>> change) -> {
                nodes.getChildren().clear();
                
                if (!change.getList().isEmpty()) {
                    final TreeItem<DocumentProperty> treeItem = change.getList().get(0);
                    
                    if (treeItem != null) {
                        final DocumentProperty document = treeItem.getValue();
                        events.notify(new PreviewEvent(document, nodes));
                    }
                }
            });
        
        events.on(PreviewEvent.class, ev -> {
            if (ev.getDocument() instanceof TableProperty) {
                final TableProperty table = (TableProperty) ev.getDocument();
                
                final ObservableStringValue text = Bindings.createStringBinding(
                    () -> {
                        if (table.enabledProperty().get()) {
                            final String name = table.aliasProperty().get() == null ?
                                table.nameProperty().get() : table.aliasProperty().get();

                            final StringBuilder code = new StringBuilder();
                            code.append("@").append(Generated.class.getSimpleName()).append("(\"speedment\")\n")
                                .append("public interface ").append(javaTypeName(name)).append(" extends Entity<").append(javaTypeName(name)).append("> {\n");
                            
                            table.columnsProperty().stream().forEachOrdered(col -> {
                                code.append("    ");
                                
                                final TypeMapper<?, ?> tm = col.findTypeMapper();
                                code.append(tm.getJavaType().getSimpleName()).append(" ");
                                
                                if (Boolean.class.isAssignableFrom(tm.getJavaType())) {
                                    code.append("is");
                                } else {
                                    code.append("get");
                                }
                                
                                final String colName = javaTypeName(col.getJavaName());
                                code.append(colName).append("();\n");
                            });
                            
                            code.append("}");
                            
                            return code.toString();
                        } else {
                            return "(No code is generated for disabled nodes.)";
                        }
                    },
                    table.columnsProperty(),
                    table.enabledProperty(),
                    table.aliasProperty(),
                    table.nameProperty()
                );
                
                ev.getPreview().getChildren().add(new PreviewNode(text));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    public static Node create(UISession session) {
        return Loader.create(session, "Preview", PreviewController::new);
	}
}