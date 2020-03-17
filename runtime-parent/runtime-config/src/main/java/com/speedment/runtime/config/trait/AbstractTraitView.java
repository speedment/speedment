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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.provider.BaseDocument;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * An abstract base implementation for a view of a particular trait.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
abstract class AbstractTraitView
extends BaseDocument implements HasMainInterface {
        
    private final Class<? extends Document> mainInterface;

    protected AbstractTraitView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data);
        this.mainInterface = requireNonNull(mainInterface);
    }

    @Override
    public final Class<? extends Document> mainInterface() {
        return mainInterface;
    }
}