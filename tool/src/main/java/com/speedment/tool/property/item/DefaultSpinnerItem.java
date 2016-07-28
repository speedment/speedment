/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import com.speedment.runtime.exception.SpeedmentException;
import static java.util.Objects.requireNonNull;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Simon
 */
public class DefaultSpinnerItem extends BaseLabelTooltipItem {

    private final ObservableIntegerValue defaultValue;
    private final ObjectProperty<Integer> value;
    private final ObjectProperty<Integer> customValue;
    private final int min, max;

    public DefaultSpinnerItem(String label, ObservableIntegerValue defaultValue, IntegerProperty value, String tooltip) {
        this(label, defaultValue, value, tooltip, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public DefaultSpinnerItem(String label, ObservableIntegerValue defaultValue, IntegerProperty value, String tooltip, int min, int max) {
        super(label, tooltip);
        this.defaultValue = requireNonNull(defaultValue);
        this.value = requireNonNull(value).asObject();
        this.customValue = new SimpleIntegerProperty().asObject();
        this.min = min;
        this.max = max;
    }

    @Override
    protected Node getEditorNode() {
        final boolean usesDefaultValue = value.getValue() == null || value.getValue().equals(defaultValue.getValue());
        
        final HBox container = new HBox();
        final CheckBox auto = new CheckBox("Auto");
        final Spinner<Integer> spinner = new Spinner<>();
        
        final IntegerSpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(svf);
        spinner.disableProperty().bind(auto.selectedProperty());
        spinner.setEditable(true);
        
        auto.setSelected(usesDefaultValue);
        attachListener(auto.selectedProperty(), (ov, o, isAuto) -> 
            setSpinnerBehaviour(svf, isAuto, defaultValue, customValue)
        );
        
        customValue.setValue(usesDefaultValue ? defaultValue.get() : value.get());
        setSpinnerBehaviour(svf, usesDefaultValue, defaultValue, customValue);
        
        final TextField editor = spinner.getEditor();
        attachListener(editor.textProperty(), (ov, oldVal, newVal) -> {
            try{
                Integer.parseInt(newVal);                
            } catch (final NumberFormatException ex){
                editor.setText(oldVal);
            }
        });
        attachListener(editor.focusedProperty(), (ov, wasFocused, isFocused) -> {
            if(wasFocused) {
                try{
                    final int editorValue = Integer.parseInt( editor.getText() );
                    if( editorValue > max){
                        editor.setText( String.valueOf(max) );
                    } else if( editorValue < min ){
                        editor.setText( String.valueOf(min) );
                    } 
                } catch (final NumberFormatException ex) {
                    throw new SpeedmentException("Unable to parse an integer from editor field", ex);
                }
            } 
        });

        svf.valueProperty().bindBidirectional( value );
        
        container.getStyleClass().add("property-editors");
        container.getChildren().addAll(auto, spinner);
        return container;
    }

    private void setSpinnerBehaviour(IntegerSpinnerValueFactory svf, boolean useDefaultValue, ObservableIntegerValue defaultValue, ObjectProperty<Integer> customValue) {
        //DefaultValue and CustomValue are unnessesary parameters.
        //However, having them as parameters makes it easier to see that they are used by this method
        if (useDefaultValue) {
            svf.valueProperty().unbindBidirectional( customValue );
            svf.setValue(defaultValue.get());
        } else {
            svf.setValue(customValue.getValue());
            svf.valueProperty().bindBidirectional( customValue );
        }
    }    
}
