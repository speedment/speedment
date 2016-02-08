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

import com.speedment.internal.core.config.db.mutator.ForeignKeyMutator;
import com.speedment.internal.ui.config.ForeignKeyColumnProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasNamePropertyMutator;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyPropertyMutator extends ForeignKeyMutator<ForeignKeyProperty> implements 
        HasEnabledPropertyMutator<ForeignKeyProperty>, 
        HasNamePropertyMutator<ForeignKeyProperty> {

    ForeignKeyPropertyMutator(ForeignKeyProperty foreignKey) {
        super(foreignKey);
    }

    @Override
    public ForeignKeyColumnProperty addNewForeignKeyColumn() {
        final ForeignKeyColumnProperty child = new ForeignKeyColumnProperty(document());
        document().foreignKeyColumnsProperty().add(child);
        return child;
    }
}