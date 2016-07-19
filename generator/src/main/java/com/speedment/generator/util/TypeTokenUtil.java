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
package com.speedment.generator.util;

import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.generator.internal.util.InternalTypeTokenUtil;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class TypeTokenUtil {
    
    public static TypeToken tokenOf(Column col) {
        return col.findTypeMapper().getJavaType(col);
    }
    
    public static Type typeOf(Column col) {
        return InternalTypeTokenUtil.toType(tokenOf(col));
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private TypeTokenUtil() {
        instanceNotAllowed(getClass());
    }
}
