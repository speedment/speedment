package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.exception.SpeedmentException;
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
import javafx.beans.binding.Bindings;

/**
 * An editor for editing a StringProperty via an IntegerSpinner, which has a default value. The user
 * may opt to use the default value or not, by checking or un-checking a CheckBox. The property
 * will bind to Spinner.valueProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public class DefaultSpinnerItem extends BaseLabelTooltipItem {

    private final ObservableIntegerValue defaultValue;
    private final ObjectProperty<Integer> value;        //Output value
    private final ObjectProperty<Integer> customValue;  
    private final int min, max;

    /**
     * Creates a new DefaultSpinnerItem. 
     * <p>
     * While the CheckBox is checked, the Spinner will be disabled, 
     * and the property will always have the default value. <br>
     * While the CheckBox is un-checked, the Spinner will be enabled, 
     * and the property will always have the Spinner's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box
     * by comparing the property value to the default value. If they match, or
     * the property value is {@code null}, the CheckBox will be checked.
     * 
     * @param label         the label text
     * @param defaultValue  the default value 
     * @param value         the property to be edited
     * @param tooltip       the tooltip
     */
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
    public Node getEditorNode() {
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
        container.getChildren().addAll(auto, spinner);
        return container;
    }

    private static void setSpinnerBehaviour(IntegerSpinnerValueFactory svf, boolean useDefaultValue, ObservableIntegerValue defaultValue, ObjectProperty<Integer> customValue) {
        if (useDefaultValue) {
            svf.valueProperty().unbindBidirectional( customValue );
            svf.setValue(defaultValue.get());
        } else {
            svf.setValue(customValue.getValue());
            svf.valueProperty().bindBidirectional( customValue );
        }
    }    
}
