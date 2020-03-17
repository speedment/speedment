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
package com.speedment.tool.config;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasParent;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 * @param <PARENT>  the parent type
 * @param <THIS>    the type of this class
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public abstract class AbstractChildDocumentProperty
    <PARENT extends Document, THIS extends AbstractChildDocumentProperty<? super PARENT, ? super THIS>> 
    extends AbstractDocumentProperty<THIS> implements HasParent<PARENT> {
    
    private final PARENT parent;
    
    public AbstractChildDocumentProperty(PARENT parent) {
        this.parent = requireNonNull(parent);
    }

    @Override
    public final Optional<PARENT> getParent() {
        return Optional.of(parent);
    }
}