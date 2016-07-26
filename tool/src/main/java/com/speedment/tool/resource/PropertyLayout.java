package com.speedment.tool.resource;

import com.speedment.tool.property.PropertyEditor;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 *
 * @author Simon
 */
final class PropertyLayout extends GridPane{
    private final AtomicInteger index;

    PropertyLayout(ObservableList<PropertyEditor.Item> properties){
        this.index = new AtomicInteger(0);

        getColumnConstraints().add(0, new ColumnConstraints(USE_PREF_SIZE, USE_COMPUTED_SIZE, USE_PREF_SIZE, Priority.NEVER, HPos.LEFT, true));
        getColumnConstraints().add(1, new ColumnConstraints(USE_PREF_SIZE, USE_PREF_SIZE, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
        getStyleClass().add("properties-layout");

        properties.stream().forEachOrdered( i -> addItem(i) );

    }

    void addItem(PropertyEditor.Item item){
        addRow(index.getAndIncrement(), item.getLabel(), item.getEditor());
    }
}
