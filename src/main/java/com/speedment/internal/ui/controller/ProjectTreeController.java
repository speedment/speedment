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
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.event.ProjectLoaded;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.config.TableProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasExpandedProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.resource.SilkIcon;
import com.speedment.internal.util.document.DocumentUtil;
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
import javafx.collections.ListChangeListener;
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
        
        ui.installContextMenu(ProjectProperty.class,    this::createDefaultContextMenu);
        ui.installContextMenu(DbmsProperty.class,       this::createDefaultContextMenu);
        ui.installContextMenu(SchemaProperty.class,     this::createDefaultContextMenu);
        ui.installContextMenu(TableProperty.class,      this::createDefaultContextMenu);
        ui.installContextMenu(IndexProperty.class,      this::createDefaultContextMenu);
        ui.installContextMenu(ForeignKeyProperty.class, this::createDefaultContextMenu);
        
        runLater(() -> prepareTree(session.getProject()));
    }
    
    private void prepareTree(ProjectProperty project) {
        requireNonNull(project);
        
        final UserInterfaceComponent ui = session.getSpeedment().getUserInterfaceComponent();
        final EventComponent events     = session.getSpeedment().getEventComponent();
        
        events.notify(new ProjectLoaded(project));

        Bindings.bindContent(ui.getSelectedTreeItems(), hierarchy.getSelectionModel().getSelectedItems());
        hierarchy.setCellFactory(view -> new DocumentPropertyCell(ui));
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
            .filter(DocumentProperty.class::isInstance)
            .filter(HasExpandedProperty.class::isInstance)
            .map(d -> (DocumentProperty & HasExpandedProperty) d)
            .map(this::branch)
            .forEachOrdered(branch.getChildren()::add);

        // TODO: React when new new child types are added to the tree
        doc.childrenProperty()
            .forEach(list -> list.addListener((ListChangeListener.Change<? extends DocumentProperty> c) -> {
                
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().stream()
                        .filter(HasExpandedProperty.class::isInstance)
                        .map(d -> branch((DocumentProperty & HasExpandedProperty) d))
                        .forEachOrdered(branch.getChildren()::add);
                } else if (c.wasRemoved()) {
                    c.getRemoved().stream()
                        .forEach(val -> branch.getChildren()
                            .removeIf(item -> val.equals(item.getValue()))
                        );
                }
            }
        }));

        return branch;
    }
    
    private <DOC extends DocumentProperty> Optional<ContextMenu> createDefaultContextMenu(TreeCell<DocumentProperty> treeCell, DOC node) {
        
        final MenuItem expandAll = new MenuItem("Expand All", SilkIcon.BOOK_OPEN.view());
        final MenuItem collapseAll = new MenuItem("Collapse All", SilkIcon.BOOK.view());
        
        expandAll.setOnAction(ev -> {
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(true));
        });
        
        collapseAll.setOnAction(ev -> {
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(false));
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
        
        private final UserInterfaceComponent ui;
        
        public DocumentPropertyCell(UserInterfaceComponent ui) {
            
            // Listener should be initiated with a listener attached
            // that removes enabled-listeners attached to the previous
            // node when a new node is selected.
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
            
            this.ui = requireNonNull(ui);
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
                
                if (item instanceof HasNameProperty) {
                    @SuppressWarnings("unchecked")
                    final HasNameProperty withName = (HasNameProperty) item;
                    textProperty().bind(withName.nameProperty());
                } else {
                    textProperty().unbind();
                    textProperty().setValue(null);
                }

                if (item instanceof HasMainInterface) {
                    ui.createContextMenu(this, (DocumentProperty & HasMainInterface) item)
                        .ifPresent(this::setContextMenu);
                }
                
                if (HasEnabled.test(item)) {
                    enable();
                } else {
                    disable();
                }
            }
        }
    }
}