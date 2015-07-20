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
package com.speedment.core.config.model.impl;

import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.Table;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.util.Cast;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class ForeignKeyImpl extends AbstractNamedConfigEntity implements ForeignKey {

    private Table parent;
    private final ChildHolder children;
    
    public ForeignKeyImpl() {
        children = new ChildHolder();
    }
    
    @Override
    protected void setDefaults() {}

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.orFail(parent, Table.class);
    }

    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }
}