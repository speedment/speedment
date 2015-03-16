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
package com.speedment.orm.db;

import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.config.model.parameters.DbmsType;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface DbmsHandler {

    DbmsType getDbmsType();

    Stream<Schema> readMetadata(Dbms dbms);

    <ENTITY> long readAll(Consumer<ENTITY> consumer);

    <PK> ResultSet read(Table table, PK primaryKey);

    <ENTITY> void insert(Table table, ENTITY entity);

    <ENTITY> void update(Table table, ENTITY entity);

    <ENTITY> void delete(Table table, ENTITY entity);

}
