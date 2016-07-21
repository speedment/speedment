/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.plugins.reactor.internal.component;

import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.IncludeInjectable;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.StandardTranslatorKey;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.plugins.reactor.component.ReactorComponent;
import static com.speedment.plugins.reactor.internal.util.ReactorComponentUtil.validMergingColumns;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationImplDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewTranslator;
import com.speedment.plugins.reactor.internal.util.MergingSupportImpl;
import com.speedment.plugins.reactor.translator.ReactorTranslatorKey;
import com.speedment.plugins.reactor.internal.translator.ViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.ViewTranslator;
import com.speedment.runtime.component.EventComponent;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.component.AbstractComponent;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.event.TreeSelectionChange;
import com.speedment.tool.property.ChoicePropertyItem;
import com.speedment.tool.util.IdentityStringConverter;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author  Emil Forslund
 * @since   1.1.0
 */
@IncludeInjectable(MergingSupportImpl.class)
public class ReactorComponentImpl extends AbstractComponent implements ReactorComponent {
    
    public final static String MERGE_ON = "mergeOn";

    @ExecuteBefore(RESOLVED)
    void setup(
            @WithState(RESOLVED) CodeGenerationComponent code, 
            @WithState(RESOLVED) UserInterfaceComponent ui, 
            @WithState(RESOLVED) EventComponent events,
            @WithState(RESOLVED) TypeMapperComponent typeMappers) {
        
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION, new GeneratedApplicationDecorator());
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION_IMPL, new GeneratedApplicationImplDecorator());
        
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW, ViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW_IMPL, ViewImplTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW, GeneratedViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW_IMPL, GeneratedViewImplTranslator::new);
        
        events.on(TreeSelectionChange.class, ev -> {
            ev.changeEvent()
                .getList()
                .stream()
                .map(TreeItem<DocumentProperty>::getValue)
                .findAny()
                .ifPresent(doc -> {
                    if (doc instanceof TableProperty) {
                        System.out.println("It was a table.");
                        final TableProperty table = (TableProperty) doc;

                        ui.getProperties().add(new ChoicePropertyItem<>(
                            observableList(
                                validMergingColumns(table, typeMappers)
                                    .stream()
                                    .map(Column::getJavaName)
                                    .collect(toList())
                            ),
                            table.stringPropertyOf(MERGE_ON, () -> null),
                            new IdentityStringConverter(), 
                            String.class,
                            "Merge events on", 
                            "This column will be used to merge events in a " + 
                            "materialized object view (MOV) so that only the " + 
                            "most recent revision of an entity is visible."
                        ));
                    }
                });
        });
    }
}
