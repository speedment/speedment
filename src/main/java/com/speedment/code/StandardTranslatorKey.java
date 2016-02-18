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
package com.speedment.code;

import com.speedment.annotation.Api;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.internal.core.code.TranslatorKeyImpl;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.codegen.lang.models.Interface;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 2.3
 */
@Api(version = "2.3")
public final class StandardTranslatorKey {

    public final static TranslatorKey<Project, Class> 
        SPEEDMENT_APPLICATION = new TranslatorKeyImpl<>("SpeedmentApplication", Class.class),
        SPEEDMENT_APPLICATION_METADATA = new TranslatorKeyImpl<>("SpeedmentApplicationMetadata", Class.class);
    
    public final static TranslatorKey<Table, Interface> 
        ENTITY = new TranslatorKeyImpl<>("Entity", Interface.class),
        MANAGER = new TranslatorKeyImpl<>("Manager", Interface.class),
        GENERATED_ENTITY = new TranslatorKeyImpl<>("GeneratedEntity", Interface.class),
        GENERATED_MANAGER = new TranslatorKeyImpl<>("GeneratedManager", Interface.class);
    
    public final static TranslatorKey<Table, Class>
        ENTITY_IMPL = new TranslatorKeyImpl<>("EntityImpl", Class.class),
        MANAGER_IMPL = new TranslatorKeyImpl<>("ManagerImpl", Class.class),
        GENERATED_ENTITY_IMPL = new TranslatorKeyImpl<>("GeneratedEntityImpl", Class.class),
        GENERATED_MANAGER_IMPL = new TranslatorKeyImpl<>("GeneratedManagerImpl", Class.class);

    public static Stream<TranslatorKey<Project, Class>> projectTranslatorKeys() {
        return Stream.of(SPEEDMENT_APPLICATION, SPEEDMENT_APPLICATION_METADATA);
    }
    
    public static Stream<TranslatorKey<Table, ? extends ClassOrInterface<?>>> tableTranslatorKeys() {
        return Stream.of(
            ENTITY, ENTITY_IMPL, 
            MANAGER, MANAGER_IMPL,
            GENERATED_ENTITY, GENERATED_ENTITY_IMPL, 
            GENERATED_MANAGER, GENERATED_MANAGER_IMPL
        );
    }

    private StandardTranslatorKey() {}
}