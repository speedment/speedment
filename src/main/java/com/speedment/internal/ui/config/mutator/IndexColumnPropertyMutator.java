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

import com.speedment.config.db.mutator.IndexColumnMutator;
import com.speedment.internal.ui.config.IndexColumnProperty;
import com.speedment.internal.ui.config.mutator.trait.HasNamePropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasOrderTypePropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasOrdinalPositionPropertyMutator;

/**
 *
 * @author Emil Forslund
 */
public final class IndexColumnPropertyMutator extends IndexColumnMutator<IndexColumnProperty> implements 
        HasNamePropertyMutator<IndexColumnProperty>,
        HasOrdinalPositionPropertyMutator<IndexColumnProperty>, 
        HasOrderTypePropertyMutator<IndexColumnProperty> {

    IndexColumnPropertyMutator(IndexColumnProperty indexColumn) {
        super(indexColumn);
    }
}