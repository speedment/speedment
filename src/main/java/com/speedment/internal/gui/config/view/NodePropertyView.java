package com.speedment.internal.gui.config.view;

import com.speedment.config.Node;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;

/**
 *
 * @author Emil Forslund
 * @param <NODE>  the node type to build UI for
 */
public interface NodePropertyView<NODE extends AbstractNodeProperty> {
    
    /**
     * Creates and configures a new tree cell to represent the
     * specified node object.
     * 
     * @param node  the observable config node to represent
     * @return      the created tree cell
     */
    TreeCell<NODE> newTreeCell(NODE node);

}