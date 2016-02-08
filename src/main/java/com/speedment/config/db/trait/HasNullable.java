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
import com.speedment.internal.util.document.TraitUtil.AbstractTraitView;
import static com.speedment.internal.util.document.TraitUtil.viewOf;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasNullable extends Document {
    
    final String NULLABLE = "nullable";
    
    /**
     * Returns whether or not this column can hold <code>null</code> values.
     *
     * @return  <code>true</code> if null values are tolerated, else
     * <code>false</code>
     */
    default boolean isNullable() {
        return getAsBoolean(NULLABLE).orElse(true);
    }
    
    static HasNullable of(Document document) {
        return viewOf(document, HasNullable.class, HasNullableView::new);
    }
}

class HasNullableView extends AbstractTraitView implements HasNullable {

    HasNullableView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}