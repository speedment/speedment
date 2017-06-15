/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.ProjectLoaded;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.*;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasIconPath;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.resource.SpeedmentIcon;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import static javafx.application.Platform.runLater;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import static javafx.scene.control.SelectionMode.SINGLE;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectTreeController implements Initializable {

    private @Inject UserInterfaceComponent ui;
    private @Inject EventComponent events;

    private @FXML TreeView<DocumentProperty> hierarchy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ui.installContextMenu(ProjectProperty.class, this::createDefaultContextMenu);
        ui.installContextMenu(DbmsProperty.class, this::createDefaultContextMenu);
        ui.installContextMenu(SchemaProperty.class, this::createDefaultContextMenu);
        ui.installContextMenu(TableProperty.class, this::createDefaultContextMenu);
        ui.installContextMenu(IndexProperty.class, this::createDefaultContextMenu);
        ui.installContextMenu(ForeignKeyProperty.class, this::createDefaultContextMenu);

        runLater(() -> prepareTree(ui.projectProperty()));
    }

    private void prepareTree(ProjectProperty project) {
        requireNonNull(project);

        events.notify(new ProjectLoaded(project));

        Bindings.bindContent(ui.getSelectedTreeItems(), hierarchy.getSelectionModel().getSelectedItems());
        hierarchy.setCellFactory(view -> new DocumentPropertyCell(ui));
        hierarchy.getSelectionModel().setSelectionMode(SINGLE);

        populateTree(project);
    }

    private void populateTree(ProjectProperty project) {
        requireNonNull(project);
        final TreeItem<DocumentProperty> root = branch(project);
        hierarchy.setRoot(root);
        hierarchy.getSelectionModel().select(root);
    }

    private <P extends DocumentProperty & HasExpandedProperty>
    TreeItem<DocumentProperty> branch(P doc) {
        requireNonNull(doc);

        final TreeItem<DocumentProperty> branch = new TreeItem<>(doc);
        branch.expandedProperty().bindBidirectional(doc.expandedProperty());

        final ListChangeListener<? super DocumentProperty> onListChange =
            (ListChangeListener.Change<? extends DocumentProperty> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().stream()
                        .filter(HasExpandedProperty.class::isInstance)
                        .map(d -> (DocumentProperty & HasExpandedProperty) d)
                        .map(this::branch)
                        .forEachOrdered(branch.getChildren()::add);
                }

                if (change.wasRemoved()) {
                    change.getRemoved()
                        .forEach(val -> branch.getChildren()
                            .removeIf(item -> val.equals(item.getValue()))
                        );
                }
            }
        };

        // Create a branch for every child
        doc.children()
            .filter(HasExpandedProperty.class::isInstance)
            .map(d -> (DocumentProperty & HasExpandedProperty) d)
            .map(this::branch)
            .forEachOrdered(branch.getChildren()::add);

        //  Listen to changes in the actual map
        doc.childrenProperty().addListener(
            (MapChangeListener.Change<
                ? extends String,
                ? extends ObservableList<DocumentProperty>> change) -> {

            if (change.wasAdded()) {

                // Listen for changes in the added list
                change.getValueAdded().addListener(onListChange);

                // Create a branch for every child
                change.getValueAdded().stream()
                    .filter(HasExpandedProperty.class::isInstance)
                    .map(d -> (DocumentProperty & HasExpandedProperty) d)
                    .map(this::branch)
                    .forEachOrdered(branch.getChildren()::add);
            }
        });

        // Listen to changes in every list inside the map
        doc.childrenProperty()
            .values()
            .forEach(list -> list.addListener(onListChange));

        return branch;
    }

    private <DOC extends DocumentProperty> Stream<MenuItem>
    createDefaultContextMenu(TreeCell<DocumentProperty> treeCell, DOC node) {

        final MenuItem expandAll   = new MenuItem("Expand All", SpeedmentIcon.BOOK_OPEN.view());
        final MenuItem collapseAll = new MenuItem("Collapse All", SpeedmentIcon.BOOK.view());

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

        return Stream.of(expandAll, collapseAll);
    }

    private final static class DocumentPropertyCell extends TreeCell<DocumentProperty> {

        private final ChangeListener<Boolean> change = (ob, o, enabled) -> {
            if (enabled) {
                enable();
            } else {
                disable();
            }
        };

        private final UserInterfaceComponent ui;

        DocumentPropertyCell(UserInterfaceComponent ui) {
            this.ui = requireNonNull(ui);

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
        }

        private void disable() {
            getStyleClass().add("gui-disabled");
        }

        private void enable() {
            while (getStyleClass().remove("gui-disabled")) {
            }
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
                final ImageView icon;

                if (item instanceof HasIconPath) {
                    final HasIconPath hasIcon = (HasIconPath) item;
                    icon = new ImageView(new Image(hasIcon.getIconPath()));
                } else {
                    icon = SpeedmentIcon.forNode(item);
                }

                setGraphic(icon);

                if (item instanceof HasNameProperty) {
                    @SuppressWarnings("unchecked")
                    final HasNameProperty withName = (HasNameProperty) item;
                    textProperty().bind(withName.nameProperty());
                } else {
                    textProperty().unbind();
                    textProperty().setValue(null);
                }

                ui.createContextMenu(this, item)
                    .ifPresent(this::setContextMenu);

                if (HasEnabled.test(item)) {
                    enable();
                } else {
                    disable();
                }
            }
        }
    }
}
