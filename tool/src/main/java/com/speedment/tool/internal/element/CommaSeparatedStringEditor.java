package com.speedment.tool.internal.element;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.property.editor.PropertyEditor;

public final class CommaSeparatedStringEditor extends VBox implements PropertyEditor<String>{
    //***********************************************************
    // 				VARIABLES
    //***********************************************************

    private final VBox innerBox;
    private final SimpleObservable observable;
    private final StringBinding binding;

    //***********************************************************
    // 				CONSTRUCTOR
    //***********************************************************	
    public CommaSeparatedStringEditor(String defaultValue) {
        this.observable = new SimpleObservable();
        this.binding = Bindings.createStringBinding(() -> getFormatedString(), observable);

        //Styling
        setSpacing(5.0);

        //Add inner VBox, which will hold all rows
        innerBox = new VBox();
        innerBox.setSpacing(2.0);
        getChildren().add(innerBox);

        //Add the text generating rows
        final Row firstRow = new Row();
        innerBox.getChildren().add(firstRow);

        //Add the button for adding new rows
        final Button addRowButton = new Button("+");
        addRowButton.setPrefWidth(50);
        addRowButton.setOnAction(v -> {
            final Row row = new Row();
            innerBox.getChildren().add(row);
            observable.notifyListeners();
        });
        getChildren().add(addRowButton);
        
        setValue(defaultValue);
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
       return innerBox.getChildren().stream()
            .filter(Row.class::isInstance)
            .map(Row.class::cast)
            .map(Row::getText)
            .filter( v -> v.length() > 0 )
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
        if( value == null ){
            innerBox.getChildren().clear();
            innerBox.getChildren().add( new Row());
            observable.notifyListeners();
        } else {
            innerBox.getChildren().setAll(
                Stream.of(value.split(","))
                    .map(Row::new)
                    .toArray(Row[]::new)
            );
            observable.notifyListeners();
        }
    }

    //***********************************************************
    // 				PRIVATE
    //***********************************************************	
    private final class Row extends HBox {

        private final Button removeButton;
        private final TextField textField;

        Row(){
            this(null);
        }
        
        Row(String value) {
            this.textField = value == null ? new TextField() : new TextField(value);
            HBox.setHgrow(textField, Priority.ALWAYS);
            this.removeButton = new Button("-");
            this.removeButton.setPrefWidth(50);

            final Row r = this;
            removeButton.setOnAction(v -> {
                if (innerBox.getChildren().size() > 1) {
                    innerBox.getChildren().remove(r);
                    observable.notifyListeners();
                }
            });
            textField.textProperty().addListener((op, o, n) -> observable.notifyListeners());

            setSpacing(8.0);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(removeButton, textField);
        }
        

        String getText() {
            return textField.getText();
        }

        @Override
        public ObservableList<Node> getChildren() {
            return super.getChildren();
        }
    }

    private class SimpleObservable implements Observable {

        private final Set<InvalidationListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

        @Override
        public void addListener(InvalidationListener listener) {
            listeners.add(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            listeners.remove(listener);
        }

        public void notifyListeners() {
            listeners.forEach(il -> il.invalidated(this));
        }
    }
}
