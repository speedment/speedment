package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.tool.property.item.BaseLabelTooltipItem;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Item for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor item allows the user
 * to easily edit such a string.
 * 
 * @author Simon Jonasson
 * @since 1.0.0
 */
public class AddRemoveStringItem extends BaseLabelTooltipItem{
    //***********************************************************
    // 				VARIABLES
    //***********************************************************   
    private final static Logger LOGGER = LoggerManager.getLogger(CommaSeparatedStringEditor.class);

    private final ObservableList<String> strings;
    private final ObservableBooleanValue enabled;
    private final ObservableBooleanValue disabled;
    private final StringProperty cache;
    
    private final String DEFAULT_FIELD = "ENUM_CONSTANT_";
    private final double SPACING = 10.0;
    private final int LIST_HEIGHT = 200;
    
    //***********************************************************
    // 				CONSTRUCTOR
    //***********************************************************
     
    public AddRemoveStringItem(String label, StringProperty value, String oldValue, String tooltip, ObservableBooleanValue enableThis) {
        super(label, tooltip);
        this.strings = FXCollections.observableArrayList();
        this.enabled = enableThis;
        this.disabled = Bindings.not(enableThis);
        this.cache = new SimpleStringProperty();
        
        StringBinding binding = Bindings.createStringBinding(() -> getFormatedString(), strings);
        value.unbind();
        value.bind( binding );
       
        setValue( oldValue );
    }
    
    //***********************************************************
    // 				PUBLIC
    //***********************************************************  

    @Override
    public Node getLabel() {
        Node node = super.getLabel();
        hideShowBehaviour(node);        
        return node;
    }
    
    
    @Override
    public Node getEditor() {
        final VBox container = new VBox();
        
        ListView<String> listView = new ListView<>(strings);
        listView.setCellFactory((ListView<String> param) -> new EnumCell());
        listView.setEditable(true);

        listView.setMaxHeight(USE_PREF_SIZE);
        listView.setPrefHeight(LIST_HEIGHT);

        final HBox controls = new HBox(SPACING);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(addButton(listView), removeButton(listView));

        container.setSpacing(SPACING);
        container.getChildren().addAll(listView, controls);
        hideShowBehaviour(container);
        
        attachListener(disabled, (ov, was, isDisabled) -> {
            if( isDisabled ){
                cache.set( getFormatedString() );
                setValue(null);
            } else {
                setValue( cache.get() );
            }
        });
        
        return container;
    }
    
    //***********************************************************
    // 				PRIVATE
    //***********************************************************
    
    private String getFormatedString() {
        String formated = strings.stream()
            .filter(v -> !v.isEmpty())
            .collect(Collectors.joining(","));
        if( formated == null || formated.isEmpty() )
            return null;
        else
            return formated;
    }
    
    private void setValue(String value) {
        if (value == null) {
            strings.clear();
        } else {
            strings.setAll(
                Stream.of(value.split(","))
                .toArray(String[]::new)
            );
        }
    }
    
    private void hideShowBehaviour(Node node){
        node.visibleProperty().bind(enabled);
        node.managedProperty().bind(enabled);
        node.disableProperty().bind(disabled);
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
            AtomicInteger i = new AtomicInteger(0);
            while (!set.add(DEFAULT_FIELD + i.incrementAndGet())) {
            }

            listView.getItems().add(DEFAULT_FIELD + i.get());
            listView.scrollTo(newIndex);
            listView.getSelectionModel().select(newIndex);

            /* There is a strange behavior in JavaFX if you try to start editing
			 * a field on the same animation frame as another field lost focus.
			 * 
			 * Therefore, we wait one animation cycle before setting the field
			 * into the editing state 
             */
            new AnimationTimer() {
                int frameCount = 0;

                @Override
                public void handle(long now) {
                    frameCount++;
                    if (frameCount > 1) {
                        listView.edit(newIndex);
                        stop();
                    }
                }
            }.start();
        });
        return button;
    }

    //***********************************************************
    // 				PRIVATE CLASS : ENUM CELL
    //***********************************************************    
    private class EnumCell extends TextFieldListCell<String> {

        private String labelString;

        private EnumCell() {
            super();
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
                    //Avoid false positives (ie showing an error that we match ourselves)
                    if(value.equalsIgnoreCase(labelString)){
                        return value;
                    }
                    if(value.isEmpty()){
                        LOGGER.info("An enum field cannot be empty. Please remove the field instead.");
                        return labelString;
                    }
                     //Make sure this is not a douplicate entry
                    final AtomicBoolean douplicate = new AtomicBoolean(false);
                    strings.stream()
                        .filter( elem -> elem.equalsIgnoreCase(value) )
                        .forEach( elem -> douplicate.set(true) );
                    if(douplicate.get()){
                        LOGGER.info("Enum cannot contain the same constant twice");
                        return labelString;
                    //Make sure this entry contains only legal characters
                    } else if ( !value.matches("([\\w\\-\\_\\ ]+)")) {
                        LOGGER.info("Enum should only contain letters, number, underscore and/or dashes");
                        return labelString;
                    //Warn if it contains a space
                    }  else if (value.contains(" ")) {
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
