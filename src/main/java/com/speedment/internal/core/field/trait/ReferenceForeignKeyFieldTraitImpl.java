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
package com.speedment.internal.core.field.trait;

import com.speedment.field.methods.Finder;
import com.speedment.field.trait.ReferenceForeignKeyFieldTrait;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <FK> the foreign entity type
 * @author pemi
 */
public class ReferenceForeignKeyFieldTraitImpl<ENTITY, D, FK> implements ReferenceForeignKeyFieldTrait<ENTITY, D, FK> {

    private final Finder<ENTITY, FK> finder;

    public ReferenceForeignKeyFieldTraitImpl(Finder<ENTITY, FK> finder) {
        this.finder = requireNonNull(finder);
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return finder;
    }

}
