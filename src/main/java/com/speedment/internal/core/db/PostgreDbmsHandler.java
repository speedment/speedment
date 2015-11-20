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

package com.speedment.internal.core.db;

import com.speedment.Speedment;
import com.speedment.config.Dbms;

/**
 * Created by fdirlikl on 11/15/2015.
 */
public class PostgreDbmsHandler extends AbstractRelationalDbmsHandler {
    private static final String RESULTSET_TABLE_SCHEMA = "TABLE_SCHEM";

    public PostgreDbmsHandler(Speedment speedment, final Dbms dbms) {
        super(speedment, dbms);
    }
}
