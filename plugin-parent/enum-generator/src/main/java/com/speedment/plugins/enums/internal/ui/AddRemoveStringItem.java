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
package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.tool.propertyeditor.item.AbstractLabelTooltipItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static javafx.application.Platform.runLater;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Item for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor item allows the user
 * to easily edit such a string.
 * 
 * @author  Simon Jonasson
 * @since   1.0.0
 */
public final class AddRemoveStringItem extends AbstractLabelTooltipItem {
    
    //***********************************************************
    // 				VARIABLES
    //***********************************************************   
    private final static Logger LOGGER = LoggerManager.getLogger(AddRemoveStringItem.class);

    private final ObservableList<String> strings;
    private final ObservableBooleanValue enabled;
    
    @SuppressWarnings("FieldCanBeLocal")
    private final StringProperty cache;
    
    private final String DEFAULT_FIELD = "ENUM_CONSTANT_";
    private final double SPACING = 10.0;
    private final int LIST_HEIGHT = 200;
    
    //***********************************************************
    // 				CONSTRUCTOR
    //***********************************************************
     
    public AddRemoveStringItem(
            String label,
            StringProperty value,
            String tooltip,
            ObservableBooleanValue enableThis) {
        
        super(label, tooltip, NO_DECORATOR);
        
        final String currentValue = value.get();
        if (currentValue == null) {
            this.strings  = FXCollections.observableArrayList();
        } else {
            this.strings  = FXCollections.observableArrayList(
                Stream.of(currentValue.split(","))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new)
            );
        }
        
        this.enabled = enableThis;
        this.cache   = new SimpleStringProperty();
        
        this.strings.addListener((ListChangeListener.Change<? extends String> c) -> {
            @SuppressWarnings("unchecked")
            final List<String> list = (List<String>) c.getList();
            value.setValue(getFormatedString(list));
        });
    }
    
    //***********************************************************
    // 				PUBLIC
    //***********************************************************  

    @Override
    public Node createLabel() {
        Node node = super.createLabel();
        hideShowBehaviour(node);        
        return node;
    }
    
    @Override
    protected Node createUndecoratedEditor() {
        final VBox container = new VBox();
        
        final ListView<String> listView = new ListView<>(strings);
        listView.setCellFactory(view -> new EnumCell(strings));
        listView.setEditable(true);

        listView.setMaxHeight(USE_PREF_SIZE);
        listView.setPrefHeight(LIST_HEIGHT);

        final HBox controls = new HBox(SPACING);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(addButton(listView), removeButton(listView));

        container.setSpacing(SPACING);
        container.getChildren().addAll(listView, controls);
        hideShowBehaviour(container);

        
        return container;
    }
    
    //***********************************************************
    // 				PRIVATE
    //***********************************************************
    
    /**
     * Removes any empty substrings and makes sure the entire string is either
     * {@code null} or non-empty.
     * 
     * @return  the formatted string
     */
    private String getFormatedString(List<String> newValue) {
        final String formated = newValue.stream()
            .filter(v -> !v.isEmpty())
            .collect(Collectors.joining(","));
        
        if (formated == null || formated.isEmpty()) {
            return null;
        } else {
            return formated;
        }
    }
    
    private void setValue(String value) {
        if (value == null) {
            strings.clear();
        } else {
            strings.setAll(value.split(","));
        }
    }
    
    private void hideShowBehaviour(Node node){
        node.visibleProperty().bind(enabled);
        node.managedProperty().bind(enabled);
        node.disableProperty().bind(Bindings.not(enabled));
    }
    
    private Button removeButton(final ListView<String> listView) {
        final Button button = new Button("Remove Selected");
        
        button.setOnAction(e -> {
            final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1 && listView.getItems().size() > 1) {
                final int newSelectedIdx = (selectedIdx == listView.getItems().size() - 1) ? selectedIdx - 1
                    : selectedIdx;
                listView.getItems().remove(selectedIdx);
                listView.getSelectionModel().select(newSelectedIdx);
            }
        });
        
        return button;
    }

    private Button addButton(final ListView<String> listView) {
        final Button button = new Button("Add Item");
        
        button.setOnAction(e -> {
            final int newIndex = listView.getItems().size();

            final Set<String> set = strings.stream().collect(toSet());
            final AtomicInteger i = new AtomicInteger(0);
            while (!set.add(DEFAULT_FIELD + i.incrementAndGet())) {}

            listView.getItems().add(DEFAULT_FIELD + i.get());
            listView.scrollTo(newIndex);
            listView.getSelectionModel().select(newIndex);
            
            /* There is a strange behavior in JavaFX if you try to start editing
			 * a field on the same animation frame as another field lost focus.
			 * 
			 * Therefore, we wait one animation cycle before setting the field
			 * into the editing state 
             */
            runLater(() -> listView.edit(newIndex));
        });
        
        return button;
    }

    //***********************************************************
    // 				PRIVATE CLASS : ENUM CELL
    //***********************************************************    
    private final static class EnumCell extends TextFieldListCell<String> {

        private final ObservableList<String> strings;
        private String labelString;

        private EnumCell(ObservableList<String> strings) {
            super();
            this.strings = requireNonNull(strings);
            setConverter(myConverter());
        }

        @Override
        public void startEdit() {
            labelString = getText();
            super.startEdit();
        }

        private StringConverter<String> myConverter() {
            return new StringConverter<String>() {
               
                @Override
                public String toString(String value) {
                    return (value != null) ? value : "";
                }

                @Override
                public String fromString(String value) {
                    // Avoid false positives (ie showing an error that we match ourselves)
                    if (value.equalsIgnoreCase(labelString)) {
                        return value;
                    } else if (value.isEmpty()) {
                        LOGGER.info("An enum field cannot be empty. Please remove the field instead.");
                        return labelString;
                    }
                    
                    // Make sure this is not a douplicate entry
                    final AtomicBoolean douplicate = new AtomicBoolean(false);
                    strings.stream()
                        .filter(elem -> elem.equalsIgnoreCase(value))
                        .forEach(elem -> douplicate.set(true));
                    if (douplicate.get()){
                        LOGGER.info("Enum cannot contain the same constant twice");
                        return labelString;
                        
                    // Make sure this entry contains only legal characters
                    } else if ( !value.matches("([\\w\\-\\_\\ ]+)")) {
                        LOGGER.info("Enum should only contain letters, number, underscore and/or dashes");
                        return labelString;
                        
                    // Warn if it contains a space
                    } else if (value.contains(" ")) {
                        LOGGER.warn("Enum spaces will be converted to underscores in Java");
                        return value;
                    } else {
                        return value; 
                    }
                }
            };
        }
    }
}