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
package com.speedment.plugins.reactor.util;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import java.lang.reflect.Type;

/**
 * Utility methods that are used by several translators in this package but that
 * doesn't necessary need to be shared with others.
 * 
 * @author Emil Forslund
 */
@InjectKey(MergingSupport.class)
public interface MergingSupport {
    
    Column mergingColumn(Table table);
    
    String mergingColumnField(Table table);
    
    Type mergingColumnType(Table table);
}
