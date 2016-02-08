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
package com.speedment.internal.core.config;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasParent;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the parent interface
 */
public abstract class AbstractChildDocument<PARENT extends Document> extends BaseDocument implements HasParent<PARENT> {

    public AbstractChildDocument(PARENT parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Optional<PARENT> getParent() {
        @SuppressWarnings("unchecked")
        final Optional<PARENT> parent = (Optional<PARENT>) super.getParent();
        return parent;
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
}