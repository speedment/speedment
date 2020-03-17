/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.internal;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.provider.BaseDocument;
import com.speedment.runtime.config.trait.HasParent;
import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the parent interface
 */
public abstract class AbstractChildDocument<PARENT extends Document> 
extends BaseDocument implements HasParent<PARENT> {

    AbstractChildDocument(PARENT parent, Map<String, Object> data) {
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