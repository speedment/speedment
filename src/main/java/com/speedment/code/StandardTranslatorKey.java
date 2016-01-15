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

import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.internal.core.code.TranslatorKeyImpl;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface StandardTranslatorKey {

    final TranslatorKey<Project> 
            SPEEDMENT_APPLICATION = new TranslatorKeyImpl<>("SpeedmentApplication"),
            SPEEDMENT_APPLICATION_METADATA = new TranslatorKeyImpl<>("SpeedmentApplicationMetadata");
    
    final TranslatorKey<Table> 
            ENTITY = new TranslatorKeyImpl<>("Entity"),
            ENTITY_IMPL = new TranslatorKeyImpl<>("EntityImpl"),
            MANAGER_IMPL = new TranslatorKeyImpl<>("ManagerImpl");

    
    static Stream<TranslatorKey<Project>> projectTranslatorKeys() {
        return Stream.of(SPEEDMENT_APPLICATION, SPEEDMENT_APPLICATION_METADATA);
    }
    
    static Stream<TranslatorKey<Table>> tableTranslatorKeys() {
        return Stream.of(ENTITY, ENTITY_IMPL, MANAGER_IMPL);
    }

}
