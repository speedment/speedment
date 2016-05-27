/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.plugins.reactor.component;

import com.speedment.generator.StandardTranslatorKey;
import com.speedment.generator.component.CodeGenerationComponent;
import static com.speedment.plugins.reactor.component.ReactorComponentUtil.validMergingColumns;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationImplDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewTranslator;
import com.speedment.plugins.reactor.internal.translator.ReactorTranslatorKey;
import com.speedment.plugins.reactor.internal.translator.ViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.ViewTranslator;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.EventComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.component.AbstractComponent;
import com.speedment.runtime.internal.license.AbstractSoftware;
import static com.speedment.runtime.internal.license.OpenSourceLicense.APACHE_2;
import com.speedment.runtime.license.Software;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.tool.event.TreeSelectionChange;
import com.speedment.tool.property.ChoicePropertyItem;
import com.speedment.tool.util.IdentityStringConverter;
import static java.util.stream.Collectors.toList;
import javafx.scene.control.TreeItem;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class ReactorComponent extends AbstractComponent {
    
    public final static String MERGE_ON = "mergeOn";

    public ReactorComponent(Speedment speedment) {
        super(speedment);
    }

    @Override
    public void onResolve() {
        super.onResolve();
        
        final CodeGenerationComponent code = getSpeedment().get(CodeGenerationComponent.class);
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION, new GeneratedApplicationDecorator());
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION_IMPL, new GeneratedApplicationImplDecorator());
        
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW, ViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW_IMPL, ViewImplTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW, GeneratedViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW_IMPL, GeneratedViewImplTranslator::new);
        
        final UserInterfaceComponent ui = getSpeedment().get(UserInterfaceComponent.class);
        final EventComponent events = getSpeedment().getEventComponent();
        
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
                                validMergingColumns(table).stream()
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

    @Override
    public Class<? extends Component> getComponentClass() {
        return ReactorComponent.class;
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with(
            "Reactor Plugin", "1.1.0", APACHE_2
        );
    }

    @Override
    public Component defaultCopy(Speedment speedment) {
        return new ReactorComponent(speedment);
    }
}