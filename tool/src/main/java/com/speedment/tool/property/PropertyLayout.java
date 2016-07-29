package com.speedment.tool.property;

import com.speedment.runtime.annotation.Api;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * GridPane which takes responsibility for the layout of editor items.
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
final class PropertyLayout extends GridPane{
    private final static int MIN_LABEL_WIDTH = 100;
    private final AtomicInteger index;
    private final Set<PropertyEditor.Item> items;

    /**
     * Creates a new GridPane, with proper column layout, and adds the editor items
     * in order
     * 
     * @param properties  the properties which should be rendered
     */
    PropertyLayout(ObservableList<PropertyEditor.Item> properties){
        this.index = new AtomicInteger(0);
        this.items = new HashSet<>();

        getColumnConstraints().add(0, new ColumnConstraints(MIN_LABEL_WIDTH, USE_COMPUTED_SIZE, USE_PREF_SIZE,    Priority.NEVER,  HPos.LEFT, true));
        getColumnConstraints().add(1, new ColumnConstraints(USE_PREF_SIZE,   USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
        getStyleClass().add("properties-layout");

        properties.stream().forEachOrdered( i -> addItem(i) );

    }

    /**
     * Adds a new editor item to the layout. New items are added at the
     * bottom.
     * 
     * @param item  the editor
     */
    void addItem(PropertyEditor.Item item){       
        addRow(index.getAndIncrement(), item.getLabel(), item.getEditor());
        items.add(item);
    }

    void remove() {
        items.stream().forEach( PropertyEditor.Item::onRemove );
        getChildren().clear();
    }
}
