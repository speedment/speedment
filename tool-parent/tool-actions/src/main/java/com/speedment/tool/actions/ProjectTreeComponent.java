package com.speedment.tool.actions;

import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.tool.config.DocumentProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Component that is responsible for creating the context menu that appears when
 * the user right-clicks any node in the Project Tree of the Speedment Tool.
 * Plugins can install their own context menus in this component.
 *
 * @author Emil Forslund
 * @since  3.0.16
 */
public interface ProjectTreeComponent {

    /**
     * Installs the specified menu builder for the specified node type.
     * This can be used to add additional options to the context menu
     * when the user right-clicks on various nodes in the Project Tree
     * section of the user interface.
     *
     * @param <DOC>        the node type
     * @param nodeType     the node type to attach to items to
     * @param menuBuilder  menu builder for that type of node
     */
    <DOC extends DocumentProperty & HasMainInterface>
    void installContextMenu(Class<? extends DOC> nodeType,
                            ContextMenuBuilder<DOC> menuBuilder);

    /**
     * Creates a context menu and fills it with all the appropriate
     * menu items for the specified tree cell and document. This will
     * invoke any previously installed context menu builders.
     * <p>
     * If no menu items should be visible for the specified cell and
     * document, an empty {@code Optional} is returned.
     *
     * @param <DOC>     the document type
     * @param treeCell  the tree cell
     * @param doc       the document
     * @return          the constructed context menu or empty
     */
    <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu>
    createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc);

    /**
     * Builder that can construct a Context Menu for a particular
     * tree cell and document.
     *
     * @param <DOC>  the document type
     */
    @FunctionalInterface
    interface ContextMenuBuilder<DOC extends DocumentProperty> {
        Stream<MenuItem> build(TreeCell<DocumentProperty> tc, DOC doc);
    }
}