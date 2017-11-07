package com.speedment.tool.actions.internal.menues;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.actions.menues.ToggleTablesEnabledAction;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import javafx.scene.control.MenuItem;

import java.util.stream.Stream;

/**
 * Default implementation of the {@link ToggleTablesEnabledAction}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.16
 */
public final class ToggleTablesEnabledActionImpl
extends AbstractToolAction
implements ToggleTablesEnabledAction {

    @Override
    void installMenuItems(ProjectTreeComponent projectTree) {
        projectTree.installContextMenu(SchemaProperty.class, (treeCell, node) -> {
            final MenuItem enableTables  = new MenuItem("Enable All Tables");
            final MenuItem disableTables = new MenuItem("Disable All Tables");

            enableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> ((TableProperty) doc).enabledProperty().setValue(true))
            );

            disableTables.setOnAction(ev ->
                DocumentUtil.traverseOver(node)
                    .filter(TableProperty.class::isInstance)
                    .forEach(doc -> ((TableProperty) doc).enabledProperty().setValue(false))
            );

            return Stream.of(enableTables, disableTables);
        });
    }
}