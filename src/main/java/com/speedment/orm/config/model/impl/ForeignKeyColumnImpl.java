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

/**
 *
 * @author pemi
 */
public class ForeignKeyColumnImpl extends AbstractOrdinalConfigEntity<ForeignKeyColumn, ForeignKey, ConfigEntity<?, ForeignKeyColumn, ?>> implements ForeignKeyColumn {

    private String foreignColumnName;
    private String foreignTableName;

    @Override
    protected void setDefaults() {
    }

    @Override
    public String getForeignColumnName() {
        return foreignColumnName;
    }

    @Override
    public ForeignKeyColumn setForeignColumnName(CharSequence foreignColumnName) {
        return run(() -> this.foreignColumnName = makeNullSafeString(foreignColumnName));
    }

    @Override
    public String getForeignTableName() {
        return foreignTableName;
    }

    @Override
    public ForeignKeyColumn setForeignTableName(CharSequence foreignTableName) {
        return run(() -> this.foreignTableName = makeNullSafeString(foreignTableName));
    }

}
