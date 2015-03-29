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
package com.speedment.orm.core.manager.sql;

import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.manager.Manager;
import java.sql.ResultSet;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <PK> PrimaryKey type for this SqlManager
 * @param <ENTITY> Entity type for this SqlManager
 * @param <BUILDER> Builder type for this SqlManager
 */
public interface SqlManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> extends Manager<PK, ENTITY, BUILDER> {

    Function<ResultSet, ENTITY> getSqlEntityMapper();

    void setSqlEntityMapper(Function<ResultSet, ENTITY> sqlEntityMapper);


}
