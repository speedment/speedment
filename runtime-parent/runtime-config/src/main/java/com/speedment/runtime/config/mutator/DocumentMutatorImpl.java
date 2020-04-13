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
package com.speedment.runtime.config.mutator;

import com.speedment.runtime.config.Document;

import static java.util.Objects.requireNonNull;

/**
 * A package private implementation of the {@link DocumentMutator} interface.
 * 
 * @author       Per Minborg
 * @param <DOC>  the type of the document
 */
abstract class DocumentMutatorImpl<DOC extends Document> implements DocumentMutator<DOC> {

    private final DOC document;

    protected DocumentMutatorImpl(DOC document) {
        this.document = requireNonNull(document);
    }
    
    @Override
    public final DOC document() {
        return document;
    }

    @Override
    public final void put(String key, Object value) {
        document.put(key, value);
    }
}