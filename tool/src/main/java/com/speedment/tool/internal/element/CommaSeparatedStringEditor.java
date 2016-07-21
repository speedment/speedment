package com.speedment.tool.internal.element;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.property.editor.PropertyEditor;

public final class CommaSeparatedStringEditor extends VBox implements PropertyEditor<String> {

    //***********************************************************
    // 				VARIABLES
    //***********************************************************   
    private final String DEFAULT_FIELD = "ENTER_FIELD_NAME";
    private final double SPACING = 10.0;
    private final int LIST_HEIGHT = 200;

    private final ObservableList<String> strings;
    private final StringBinding binding;

    //***********************************************************
    // 				CONSTRUCTOR
    //***********************************************************	
    public CommaSeparatedStringEditor(String defaultValue) {
        this.strings = FXCollections.observableArrayList();
        this.binding = Bindings.createStringBinding(() -> getFormatedString(), strings);

        setValue(defaultValue);

        ListView<String> listView = new ListView<>(strings);
        listView.setCellFactory(TextFieldListCell.forListView());
        listView.setEditable(true);

        listView.setMaxHeight(USE_PREF_SIZE);
        listView.setPrefHeight(LIST_HEIGHT);

        final HBox controls = new HBox(SPACING);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(addButton(listView), removeButton(listView));

        setSpacing(SPACING);
        getChildren().addAll(listView, controls);
    }

    //***********************************************************
    // 				PUBLIC
    //***********************************************************
    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public StringBinding binding() {
        return binding;
    }

    public String getFormatedString() {
        return strings.stream()
            .filter(v -> !v.isEmpty())
            .collect(Collectors.joining(","));
    }

    @Override
    public Node getEditor() {
        return this;
    }

    @Override
    public String getValue() {
        return getFormatedString();
    }

    @Override
    public void setValue(String value) {
        if (value == null) {
            strings.setAll(DEFAULT_FIELD);
        } else {
            strings.setAll(
                Stream.of(value.split(","))
                .toArray(String[]::new)
            );
        }
    }

    //***********************************************************
    // 				PRIVATE
    //***********************************************************	
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

            listView.getItems().add(DEFAULT_FIELD);
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
}
