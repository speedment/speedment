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
package com.speedment.plugins.reactor;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.internal.license.AbstractSoftware;
import com.speedment.runtime.license.Software;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.component.EventComponent;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.generator.translator.StandardTranslatorKey;
import com.speedment.plugins.reactor.internal.editor.MergeOnEditor;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedApplicationImplDecorator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.GeneratedViewTranslator;
import com.speedment.plugins.reactor.internal.translator.ViewImplTranslator;
import com.speedment.plugins.reactor.internal.translator.ViewTranslator;
import com.speedment.plugins.reactor.internal.util.MergingSupportImpl;
import com.speedment.plugins.reactor.translator.ReactorTranslatorKey;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import static com.speedment.runtime.license.OpenSourceLicense.APACHE_2;
import com.speedment.tool.component.PropertyEditorComponent;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.config.TableProperty;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
@InjectKey(ReactorComponent.class)
public final class ReactorComponent {

    public static InjectBundle include() {
        return InjectBundle.of(
            MergingSupportImpl.class
        );
    }    
    
    public final static String MERGE_ON = "mergeOn";

    @ExecuteBefore(RESOLVED)
    void setup(
            @WithState(RESOLVED) Injector injector,
            @WithState(RESOLVED) CodeGenerationComponent code, 
            @WithState(RESOLVED) UserInterfaceComponent ui, 
            @WithState(RESOLVED) EventComponent events,
            @WithState(RESOLVED) TypeMapperComponent typeMappers,
            @WithState(RESOLVED) PropertyEditorComponent editors
    ) {
        
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION, injector.inject(new GeneratedApplicationDecorator()));
        code.add(Project.class, StandardTranslatorKey.GENERATED_APPLICATION_IMPL, injector.inject(new GeneratedApplicationImplDecorator()));
        
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW, ViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.ENTITY_VIEW_IMPL, ViewImplTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW, GeneratedViewTranslator::new);
        code.put(Table.class, ReactorTranslatorKey.GENERATED_ENTITY_VIEW_IMPL, GeneratedViewImplTranslator::new);
               
        editors.install(TableProperty.class, MERGE_ON, MergeOnEditor::new);
    }
    
}