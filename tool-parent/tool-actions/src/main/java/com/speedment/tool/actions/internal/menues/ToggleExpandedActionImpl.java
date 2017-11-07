package com.speedment.tool.actions.internal.menues;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.actions.internal.resources.ProjectTreeIcon;
import com.speedment.tool.actions.menues.ToggleExpandedAction;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

import java.util.stream.Stream;

/**
 * Default implementation of {@link ToggleExpandedAction}.
 *
 * @author Emil Forslund
 * @since  3.0.16
 */
public final class ToggleExpandedActionImpl
extends AbstractToolAction
implements ToggleExpandedAction {

    @Override
    void installMenuItems(ProjectTreeComponent projectTree) {
        projectTree.installContextMenu(DbmsProperty.class, this::installForDocumentType);
        projectTree.installContextMenu(SchemaProperty.class, this::installForDocumentType);
        projectTree.installContextMenu(TableProperty.class, this::installForDocumentType);
    }

    private <DOC extends DocumentProperty> Stream<MenuItem>
    installForDocumentType(TreeCell<DocumentProperty> treeCell, DOC node) {
        final MenuItem expandAll   = new MenuItem("Expand All", ProjectTreeIcon.BOOK_OPEN.view());
        final MenuItem collapseAll = new MenuItem("Collapse All", ProjectTreeIcon.BOOK.view());

        expandAll.setOnAction(ev ->
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(true))
        );

        collapseAll.setOnAction(ev ->
            DocumentUtil.traverseOver(node)
                .filter(HasExpandedProperty.class::isInstance)
                .forEach(doc -> ((HasExpandedProperty) doc).expandedProperty().setValue(false))
        );

        return Stream.of(expandAll, collapseAll);
    }
}