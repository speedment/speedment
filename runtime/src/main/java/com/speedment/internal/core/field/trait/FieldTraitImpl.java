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

import com.speedment.Speedment;
import com.speedment.config.db.Column;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.trait.FieldTrait;
import com.speedment.internal.util.document.DocumentDbUtil;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class FieldTraitImpl implements FieldTrait {

    private final FieldIdentifier<?> identifier;
    private final boolean unique;

    public FieldTraitImpl(FieldIdentifier<?> identifier, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.unique = unique;
    }

    @Override
    public FieldIdentifier<?> getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {columnName: '" + getIdentifier().columnName() + "'}";
    }

    @Override
    public Optional<Column> findColumn(Speedment speedment) {
        return Optional.of(DocumentDbUtil.referencedColumn(speedment, identifier));
    }
}