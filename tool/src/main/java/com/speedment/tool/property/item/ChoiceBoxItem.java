package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

/**
 * An editor for editing a property via a ChoiceBox. 
 * The property will bind to the ChoiceBox.valueProperty().
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version = "3.0")
public class ChoiceBoxItem<T> extends BaseLabelTooltipItem {

    private final Property<T> currentValue;
    private final ObservableList<T> alternatives;

    /**
     * Creates a new ChoiceBoxItem.
     * <p>
     * The default choice will either be the first choice alternative, or the
     * alternative matching the current value of the property (if such as
     * alternative exists). Alternatives will get their name by calling toString()
     * on the objects in the list of alternatives
     * <p>
     * The property will bind to the ChoiceBox.valueProperty().
     *
     * @param label         the label text
     * @param value         the property
     * @param alternatives  list of alternatives in the choice box
     * @param tooltip       the tooltip
     */
    public ChoiceBoxItem(String label, Property<T> value, ObservableList<T> alternatives, String tooltip) {
        super(label, tooltip);
        this.currentValue = value;
        this.alternatives = FXCollections.unmodifiableObservableList(alternatives);
    }

    @Override
    public Node getEditor() {
        final ChoiceBox<T> box = new ChoiceBox<>(alternatives);
        final T val = currentValue.getValue();

        if (alternatives.contains(val)) {
            box.setValue(val);
        } else {
            box.setValue(alternatives.get(0));
        }

        currentValue.bind(box.valueProperty());
        return box;
    }

}
