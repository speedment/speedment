/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.ui.config;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author          Emil Forslund
 * @param <PARENT>  the parent type
 */
public abstract class AbstractChildDocumentProperty<PARENT extends Document> extends AbstractDocumentProperty implements HasParent<PARENT> {
    
    private final PARENT parent;
    
    public AbstractChildDocumentProperty(PARENT parent, Map<String, Object> data) {
        super(data);
        this.parent = requireNonNull(parent);
    }

    @Override
    public final Optional<PARENT> getParent() {
        return Optional.of(parent);
    }
}