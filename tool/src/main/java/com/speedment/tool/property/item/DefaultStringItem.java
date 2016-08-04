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
import java.util.function.UnaryOperator;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
abstract class DefaultStringItem extends BaseLabelTooltipItem {

    private final ObservableStringValue defaultValue;
    private final StringProperty value;
    private final StringProperty customValue;

    /**
     * Creates a new DefaultStringItem.
     * <p>
     * While the CheckBox is checked, the TextInput will be disabled, and the
     * property will always have the default value. <br>
     * While the CheckBox is un-checked, the TextInput will be enabled, and the
     * property will always have the TextInput's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box by
     * comparing the property value to the default value. If they match, or the
     * property value is {@code null}, the CheckBox will be checked.
     *
     * @param label the label text
     * @param defaultValue the default value
     * @param value the property to be edited
     * @param tooltip the tooltip
     */
    public DefaultStringItem(String label, ObservableStringValue defaultValue, StringProperty value, String tooltip) {
        this(label, defaultValue, value, tooltip, NO_DECORATOR);
    }

    /**
     * Creates a new DefaultStringItem.
     * <p>
     * While the CheckBox is checked, the TextInput will be disabled, and the
     * property will always have the default value. <br>
     * While the CheckBox is un-checked, the TextInput will be enabled, and the
     * property will always have the TextInput's current value.
     * <p>
     * Upon construction, the editor decides whether to check the default box by
     * comparing the property value to the default value. If they match, or the
     * property value is {@code null}, the CheckBox will be checked.
     *
     * @param label the label text
     * @param defaultValue the default value
     * @param value the property to be edited
     * @param tooltip the tooltip
     * @param decorator the editor decorator
     */
    public DefaultStringItem(String label, ObservableStringValue defaultValue, StringProperty value, String tooltip, UnaryOperator<Node> decorator) {
        super(label, tooltip, decorator);
        this.defaultValue = requireNonNull(defaultValue);
        this.value = requireNonNull(value);
        this.customValue = new SimpleStringProperty();
    }

    @Override
    protected Node createUndecoratedEditor() {
        //TODO: CustomValue should be bound to defaultValue until we disable auto for the first time
        final HBox container = new HBox();
        final TextInputControl textInput = getInputControl();
        final CheckBox auto = new CheckBox("Auto");
        final boolean useDefaultValue = value.isEmpty().get() || value.get().equals(defaultValue.get());

        textInput.disableProperty().bind(auto.selectedProperty());
        if (useDefaultValue) {
            textInput.textProperty().bind(defaultValue);
        } else {
            textInput.setText(value.get());
        }
        customValue.bind(textInput.textProperty());

        attachListener(textInput.textProperty(), (ov, o, n) -> {
            if (n == null || n.isEmpty() || n.equalsIgnoreCase(defaultValue.getValue())) {
                value.setValue(null);
            } else {
                value.setValue(n);
            }
        });

        auto.selectedProperty().setValue(useDefaultValue);
        attachListener(auto.selectedProperty(), (ov, o, isAuto)
            -> setTextFieldBehaviour(textInput, isAuto, defaultValue, customValue)
        );

        HBox.setHgrow(textInput, Priority.ALWAYS);
        HBox.setHgrow(auto, Priority.NEVER);
        container.getChildren().addAll(auto, textInput);
        return container;
    }

    protected abstract TextInputControl getInputControl();

    private static void setTextFieldBehaviour(TextInputControl text, boolean useDefaultValue, ObservableStringValue defaultValue, StringProperty customValue) {
        if (useDefaultValue) {
            customValue.unbind();
            text.textProperty().bind(defaultValue);
        } else {
            text.textProperty().unbind();
            text.setText(customValue.get());
            customValue.bind(text.textProperty());
        }
    }
}
