package com.speedment.tool.actions.internal;

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.config.DocumentProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeCell;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.stream.Collectors.toList;

/**
 * Default implementation of the {@link ProjectTreeComponent}-interface. This
 * implementation is concurrent.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public final class ProjectTreeComponentImpl implements ProjectTreeComponent {

    private final Map<Class<?>, List<ContextMenuBuilder<?>>> contextMenuBuilders;

    /**
     * This class is intended to be dependency injected. This constructor should
     * therefore not be invoked directly.
     */
    ProjectTreeComponentImpl() {
        contextMenuBuilders = new ConcurrentHashMap<>();
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface>
    void installContextMenu(Class<? extends DOC> nodeType,
                            ContextMenuBuilder<DOC> menuBuilder) {

        contextMenuBuilders.computeIfAbsent(nodeType,
            k -> new CopyOnWriteArrayList<>()
        ).add(menuBuilder);
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface>
    Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell,
                                            DOC doc) {
        requireNonNulls(treeCell, doc);

        @SuppressWarnings("unchecked")
        final List<ContextMenuBuilder<DOC>> builders =
            MapStream.of(contextMenuBuilders)
                .filterKey(clazz -> clazz.isAssignableFrom(doc.getClass()))
                .values()
                .flatMap(List::stream)
                .map(builder -> (ContextMenuBuilder<DOC>) builder)
                .collect(toList());

        final ContextMenu menu = new ContextMenu();
        for (int i = 0; i < builders.size(); i++) {
            final List<MenuItem> items = builders.get(i)
                .build(treeCell, doc)
                .collect(toList());

            if (i > 0 && !items.isEmpty()) {
                menu.getItems().add(new SeparatorMenuItem());
            }

            items.forEach(menu.getItems()::add);
        }

        if (menu.getItems().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(menu);
        }
    }
}
