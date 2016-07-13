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
package com.speedment.plugins.reactor.internal.translator;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Interface;
import com.speedment.generator.TranslatorKey;
import com.speedment.generator.internal.TranslatorKeyImpl;
import com.speedment.runtime.config.Table;


/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public class ReactorTranslatorKey {
    public final static TranslatorKey<Table, Interface>
        ENTITY_VIEW = new TranslatorKeyImpl<>("entity-view", Interface.class),
        GENERATED_ENTITY_VIEW = new TranslatorKeyImpl<>("generated-entity-view", Interface.class);
    
    public final static TranslatorKey<Table, Class>
        ENTITY_VIEW_IMPL = new TranslatorKeyImpl<>("entity-view-impl", Class.class),
        GENERATED_ENTITY_VIEW_IMPL = new TranslatorKeyImpl<>("enerated-entity-view-impl", Class.class);
}
