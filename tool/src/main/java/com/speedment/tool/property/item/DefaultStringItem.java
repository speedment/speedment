package com.speedment.tool.property.item;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextInputControl;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Simon
 */
abstract class DefaultStringItem  extends BaseLabelTooltipItem {

    private final ObservableStringValue defaultValue;
    private final StringProperty value;
    private final StringProperty customValue;

    public DefaultStringItem(String label, ObservableStringValue defaultValue, StringProperty value, String tooltip) {
        super(label, tooltip);
        this.defaultValue = requireNonNull(defaultValue);
        this.value        = requireNonNull(value);
        this.customValue = new SimpleStringProperty();
    }
    
    @Override
    protected Node getEditorNode() {
        final HBox container = new HBox();
        final TextInputControl textInput = getInputControl();
        final CheckBox auto = new CheckBox("Auto");
        boolean usesDefaultValue = value.isEmpty().get() || value.get().equals(defaultValue.get());

        customValue.setValue(usesDefaultValue ? defaultValue.get() : value.get());     
       
        setTextFieldBehaviour(textInput, usesDefaultValue, defaultValue, customValue);
        textInput.disableProperty().bind(auto.selectedProperty());
        
        value.bind(textInput.textProperty());
        
        auto.setSelected(usesDefaultValue);
        attachListener( auto.selectedProperty(), (ov, o, isAuto) -> 
            setTextFieldBehaviour(textInput, isAuto, defaultValue, customValue)
        );
        
        HBox.setHgrow(textInput, Priority.ALWAYS);        
        HBox.setHgrow(auto, Priority.NEVER);
        container.getStyleClass().add("property-editors");
        container.getChildren().addAll(auto, textInput);
        return container;
    }
    
    protected abstract TextInputControl getInputControl();
    
    private static void setTextFieldBehaviour(TextInputControl text, boolean useDefaultValue, ObservableStringValue defaultValue, StringProperty customValue){
        if (useDefaultValue) {
            customValue.unbind();
            text.textProperty().bind( defaultValue );
        } else {
            text.textProperty().unbind();
            text.setText(customValue.get());
            customValue.bind(text.textProperty());
        }
    }
}
