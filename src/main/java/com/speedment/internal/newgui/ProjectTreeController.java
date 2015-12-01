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

import com.speedment.component.EventComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.event.ProjectLoaded;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import com.speedment.internal.gui.config.AbstractParentProperty;
import com.speedment.internal.gui.config.ProjectProperty;
import com.speedment.internal.gui.resource.SpeedmentIcon;
import com.speedment.internal.newgui.util.UILoader;
import com.speedment.internal.newgui.util.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import static java.util.Objects.requireNonNull;
import static javafx.application.Platform.runLater;
import javafx.beans.binding.Bindings;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectTreeController implements Initializable {
    
    private final UISession session;
    
    private @FXML TreeView<AbstractNodeProperty> hierarchy;
    
    private ProjectTreeController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runLater(() -> populateTree(session.getProject()));
    }
    
    private void populateTree(ProjectProperty project) {
        requireNonNull(project);
        
        final UserInterfaceComponent ui = session.getSpeedment().getUserInterfaceComponent();
        final EventComponent events     = session.getSpeedment().getEventComponent();
        
        events.notify(new ProjectLoaded(project));

        hierarchy.setCellFactory(view -> new TreeCell<AbstractNodeProperty>() {
            @Override
            protected void updateItem(AbstractNodeProperty item, boolean empty) {
                // item can be null
                super.updateItem(item, requireNonNull(empty));

                if (item == null || empty) {
                    setGraphic(null);
                    textProperty().unbind();
                    styleProperty().unbind();
                    setText(null);
                    setStyle(null);
                    setContextMenu(null);
                } else {
                    setGraphic(SpeedmentIcon.forNode(item));
                    textProperty().bind(item.nameProperty());

                    ui.createContextMenu(this, item)
                        .ifPresent(this::setContextMenu);

                    styleProperty().bind(
                        createObjectBinding(
                            () -> item.enabledProperty().get() ?
                            "" : "-fx-text-fill: gray; -fx-font-style: italic;", 
                            item.enabledProperty()
                        )
                    );
                }
            }
        });
        
        hierarchy.getSelectionModel().setSelectionMode(MULTIPLE);
        
        Bindings.bindContent(
            ui.getCurrentSelection(),
            hierarchy.getSelectionModel().getSelectedItems()
        );
        
        hierarchy.setRoot(branch(project));
    }
    
    private TreeItem<AbstractNodeProperty> branch(AbstractNodeProperty node) {
        requireNonNull(node);
        
        final TreeItem<AbstractNodeProperty> branch = new TreeItem<>(node);
        branch.expandedProperty().bindBidirectional(node.expandedProperty());

        if (node instanceof AbstractParentProperty) {
            @SuppressWarnings("unchecked")
            final AbstractParentProperty<AbstractNodeProperty, ? extends AbstractNodeProperty> nodeAsParent 
                = (AbstractParentProperty<AbstractNodeProperty, ? extends AbstractNodeProperty>) node;
            
            nodeAsParent.stream()
                .map(this::branch)
                .forEach(branch.getChildren()::add);
            
            nodeAsParent.children().addListener((ListChangeListener.Change<? extends AbstractNodeProperty> c) -> {
                while (c.next()) {
                    if (c.wasAdded()) {
                        c.getAddedSubList().stream()
                            .map(this::branch)
                            .forEach(branch.getChildren()::add);
                    } else if (c.wasRemoved()) {
                        c.getRemoved().stream()
                            .forEach(val -> branch.getChildren().removeIf(item -> val.equals(item.getValue())));
                    }
                }
            });
        }

        return branch;
    }
    
    public static Node create(UISession session) {
        return UILoader.create(session, "ProjectTree", ProjectTreeController::new);
	}
}