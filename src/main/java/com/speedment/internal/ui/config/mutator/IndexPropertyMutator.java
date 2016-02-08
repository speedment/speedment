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
package com.speedment.internal.ui.config.mutator;

import com.speedment.internal.core.config.db.mutator.IndexMutator;
import com.speedment.internal.ui.config.IndexColumnProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasNamePropertyMutator;

/**
 *
 * @author Emil Forslund
 */
public final class IndexPropertyMutator extends IndexMutator<IndexProperty> implements 
        HasEnabledPropertyMutator<IndexProperty>, 
        HasNamePropertyMutator<IndexProperty> {

    IndexPropertyMutator(IndexProperty index) {
        super(index);
    }

    @Override
    public void setUnique(Boolean unique) {
        document().uniqueProperty().setValue(unique);
    }

    @Override
    public IndexColumnProperty addNewIndexColumn() {
        final IndexColumnProperty child = new IndexColumnProperty(document());
        document().indexColumnsProperty().add(child);
        return child;
    }
}