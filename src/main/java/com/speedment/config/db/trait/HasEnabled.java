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
package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasEnabled extends Document {
    
    final String ENABLED = "enabled";
    final boolean ENABLED_DEFAULT = true;
    
    default boolean isEnabled() {
        return getAsBoolean(ENABLED).orElse(ENABLED_DEFAULT);
    }
    
    static boolean test(Document doc) {
        if (doc instanceof HasEnabled) {
            return ((HasEnabled) doc).isEnabled();
        } else return true;
    }
}