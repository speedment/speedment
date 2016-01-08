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
package com.speedment.internal.ui.controller;

import com.speedment.component.EventComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.event.ProjectLoaded;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasExpandedProperty;
import com.speedment.internal.ui.resource.SilkIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import static javafx.application.Platform.runLater;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.Parent;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectTreeController implements Initializable {
    
    private final UISession session;
    private @FXML TreeView<DocumentProperty> hierarchy;
    
    private ProjectTreeController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final UserInterfaceComponent ui = session.getSpeedment().getUserInterfaceComponent();
        
        ui.installContextMenu(Project.class,    this::createDefaultContextMenu);
        ui.installContextMenu(Dbms.class,       this::createDefaultContextMenu);
        ui.installContextMenu(Schema.class,     this::createDefaultContextMenu);
        ui.installContextMenu(Table.class,      this::createDefaultContextMenu);
        ui.installContextMenu(Index.class,      this::createDefaultContextMenu);
        ui.installContextMenu(ForeignKey.class, this::createDefaultContextMenu);
        
        runLater(() -> prepareTree(session.getProject()));
        
        // TODO: React on changes in the config model
    }
    
    private void prepareTree(ProjectProperty project) {
        requireNonNull(project);
        
        final UserInterfaceComponent ui = session.getSpeedment().getUserInterfaceComponent();
        final EventComponent events     = session.getSpeedment().getEventComponent();
        
        events.notify(new ProjectLoaded(project));

        hierarchy.setCellFactory(view -> new DocumentPropertyCell());
        
        Bindings.bindContent(ui.getSelectedTreeItems(), hierarchy.getSelectionModel().getSelectedItems());
        hierarchy.getSelectionModel().setSelectionMode(MULTIPLE);
        
        populateTree(project);
    }
    
    private void populateTree(ProjectProperty project) {
        requireNonNull(project);
        final TreeItem<DocumentProperty> root = branch(project);
        hierarchy.setRoot(root);
        hierarchy.getSelectionModel().select(root);
    }
    
    private <P extends DocumentProperty & HasExpandedProperty> TreeItem<DocumentProperty> branch(P doc) {
        requireNonNull(doc);
        
        final TreeItem<DocumentProperty> branch = new TreeItem<>(doc);
        branch.expandedProperty().bindBidirectional(doc.expandedProperty());

        doc.children()
            .filter(d -> d instanceof DocumentProperty)
            .filter(d -> d instanceof HasExpandedProperty)
            .map(d -> (P) d)
            .map(this::branch)
            .forEachOrdered(branch.getChildren()::add);

        // TODO: React when new children are added to the tree

//            nodeAsParent.children().addListener((ListChangeListener.Change<? extends AbstractNodeProperty> c) -> {
//                while (c.next()) {
//                    if (c.wasAdded()) {
//                        c.getAddedSubList().stream()
//                            .map(this::branch)
//                            .forEachOrdered(branch.getChildren()::add);
//                    } else if (c.wasRemoved()) {
//                        c.getRemoved().stream()
//                            .forEach(val -> branch.getChildren().removeIf(item -> val.equals(item.getValue())));
//                    }
//                }
//            });

        return branch;
    }
    
    private <DOC extends DocumentProperty> Optional<ContextMenu> createDefaultContextMenu(TreeCell<DocumentProperty> treeCell, DOC node) {
        final MenuItem expandAll = new MenuItem("Expand All", SilkIcon.BOOK_OPEN.view());
        final MenuItem collapseAll = new MenuItem("Collapse All", SilkIcon.BOOK.view());
        
        expandAll.setOnAction(ev -> {
            
            node.traverseOver(AbstractNodeProperty.class)
                .forEach(n -> n.setExpanded(true));
        });
        
        collapseAll.setOnAction(ev -> {
            node.traverseOver(AbstractNodeProperty.class)
                .forEach(n -> n.setExpanded(false));
        });
        
        return Optional.of(new ContextMenu(expandAll, collapseAll));
    }
    
    public static Node create(UISession session) {
        return Loader.create(session, "ProjectTree", ProjectTreeController::new);
	}
    
    private final static class DocumentPropertyCell extends TreeCell<DocumentProperty> {
        
        private final ChangeListener<Boolean> change = (ob, o, enabled) -> {
            if (enabled) enable(); 
            else disable();
        };
        
        public DocumentPropertyCell() {
            
        }

        // Listener should be initiated with a listener attached
        // that removes enabled-listeners attached to the previous
        // node when a new node is selected.
        {
            itemProperty().addListener((ob, o, n) -> {

                if (o != null && o instanceof HasEnabledProperty) {
                    final HasEnabledProperty hasEnabled = (HasEnabledProperty) o;
                    hasEnabled.enabledProperty().removeListener(change);
                }

                if (n != null && n instanceof HasEnabledProperty) {
                    final HasEnabledProperty hasEnabled = (HasEnabledProperty) n;
                    hasEnabled.enabledProperty().addListener(change);
                }

            });
        }

        private void disable() {
            getStyleClass().add("gui-disabled");
        }

        private void enable() {
            while (getStyleClass().remove("gui-disabled")) {}
        }

        @Override
        protected void updateItem(DocumentProperty item, boolean empty) {
            // item can be null
            super.updateItem(item, requireNonNull(empty));

            if (empty || item == null) {
                textProperty().unbind();

                setText(null);
                setGraphic(null);
                setContextMenu(null);

                disable();
            } else {
                setGraphic(SpeedmentIcon.forNode(item));
                textProperty().bind(item.nameProperty());

                ui.createContextMenu(this, item)
                    .ifPresent(this::setContextMenu);

                if (item.isEnabled()) enable(); 
                else disable();
            }
        }
    }
}