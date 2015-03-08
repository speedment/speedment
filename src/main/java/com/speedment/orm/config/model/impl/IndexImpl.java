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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.*;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class IndexImpl extends AbstractNamedConfigEntity implements Index {

    private Table parent;
    private final ChildHolder<IndexColumn> children;
    private boolean unique;

    public IndexImpl() {
        this.children = new ChildHolder<>();
    }

    @Override
    protected void setDefaults() {
        setUnique(false);
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public void setParent(Table parent) {
        this.parent = parent;
    }

    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder<IndexColumn> getChildren() {
        return children;
    }
}