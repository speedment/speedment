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

import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.PrimaryKeyColumnPropertyMutator;
import com.speedment.tool.config.trait.*;

import java.util.List;

import static com.speedment.tool.config.internal.util.ImmutableListUtil.concat;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class PrimaryKeyColumnProperty
extends AbstractChildDocumentProperty<Table, PrimaryKeyColumnProperty>
implements PrimaryKeyColumn,
        HasExpandedProperty,
        HasIdProperty,        
        HasNameProperty,
        HasEnabledProperty,
        HasOrdinalPositionProperty,
        HasColumnProperty,
        HasNameProtectedProperty {

    public PrimaryKeyColumnProperty(Table parent) {
        super(parent);
    }

    @Override
    public boolean isNameProtectedByDefault() {
        return false;
    }

    @Override
    public PrimaryKeyColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponentUtil.PRIMARY_KEY_COLUMNS, key);
    }
}