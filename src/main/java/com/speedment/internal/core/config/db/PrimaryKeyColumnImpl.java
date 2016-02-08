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
package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Table;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class PrimaryKeyColumnImpl extends AbstractChildDocument<Table> implements PrimaryKeyColumn {

    public PrimaryKeyColumnImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
}