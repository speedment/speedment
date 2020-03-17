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
package com.speedment.runtime.config.mutator;


import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mutator.trait.HasAliasMutator;
import com.speedment.runtime.config.mutator.trait.HasColumnSizeMutator;
import com.speedment.runtime.config.mutator.trait.HasDecimalDigitsMutator;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;
import com.speedment.runtime.config.mutator.trait.HasOrdinalPositionMutator;
import com.speedment.runtime.config.trait.HasEnumConstantsUtil;
import com.speedment.runtime.config.trait.HasTypeMapperUtil;
import com.speedment.runtime.config.util.ClassUtil;

import static com.speedment.runtime.config.ColumnUtil.AUTO_INCREMENT;
import static com.speedment.runtime.config.trait.HasNullableUtil.NULLABLE;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public class ColumnMutator<DOC extends Column> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>, 
        HasIdMutator<DOC>,
        HasNameMutator<DOC>, 
        HasAliasMutator<DOC>,
        HasColumnSizeMutator<DOC>,
        HasDecimalDigitsMutator<DOC>,
        HasOrdinalPositionMutator<DOC> {

    public ColumnMutator(DOC column) {
        super(column);
    }

    public void setNullable(Boolean nullable) {
        put(NULLABLE, nullable);
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        put(AUTO_INCREMENT, autoIncrement);
    }

    public void setTypeMapper(Class<?> typeMapperClass) {
        put(HasTypeMapperUtil.TYPE_MAPPER, ClassUtil.classToString(typeMapperClass));
    }

    public void setDatabaseType(Class<?> databaseType) {
        put(HasTypeMapperUtil.DATABASE_TYPE, ClassUtil.classToString(databaseType));
    }
    
    public void setEnumConstants(String enumConstants) {
        put(HasEnumConstantsUtil.ENUM_CONSTANTS, enumConstants);
    }
}