/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractRootDocumentProperty extends AbstractDocumentProperty {
    
    public AbstractRootDocumentProperty(Map<String, Object> data) {
        super(data);
    }

    @Override
    public final Optional<? extends Document> getParent() {
        return Optional.empty();
    }

    @Override
    public final Stream<Document> ancestors() {
        return Stream.empty();
    }
}