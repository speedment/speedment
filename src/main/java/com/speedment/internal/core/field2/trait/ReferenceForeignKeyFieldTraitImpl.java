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
package com.speedment.internal.core.field2.trait;

import com.speedment.field2.methods.Finder;
import com.speedment.field2.trait.ReferenceForeignKeyFieldTrait;

/**
 * @param <ENTITY> the entity type
 * @param <FK> the foreign entity type
 * @author pemi
 */
public class ReferenceForeignKeyFieldTraitImpl<ENTITY, FK> implements ReferenceForeignKeyFieldTrait<ENTITY, FK> {

    private final Finder<ENTITY, FK> finder;

    public ReferenceForeignKeyFieldTraitImpl(Finder<ENTITY, FK> finder) {
        this.finder = finder;
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return finder;
    }

}
