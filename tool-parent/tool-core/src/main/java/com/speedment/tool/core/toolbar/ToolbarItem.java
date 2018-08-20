package com.speedment.tool.core.toolbar;

import com.speedment.common.injector.annotation.Inject;
import javafx.scene.Node;

/**
 * One control in the 'toolbar'-section of the Speedment Tool. Implementations
 * of this interface can use the {@link Inject}-annotation, but does not have a
 * lifecycle.
 *
 * @param <T>  the type of JavaFX node that this produces
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public interface ToolbarItem<T extends Node> {

    /**
     * Creates and returns a new JavaFX-node to serve as this item in the
     * toolbar.
     *
     * @return  the new item
     */
    T createNode();

    /**
     * Returns the side on which this tool will appear. The default value is
     * {@link ToolbarSide#LEFT}.
     *
     * @return  the side to appear on
     */
    default ToolbarSide getSide() {
        return ToolbarSide.LEFT;
    }
}
