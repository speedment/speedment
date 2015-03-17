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
package com.speedment.orm.db.mock;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.PrimaryKeyColumn;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.db.impl.AbstractRelationalDbmsHandler;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class MockMySqlDbmsHandler extends AbstractRelationalDbmsHandler implements DbmsHandler {

    public MockMySqlDbmsHandler(Dbms dbms) {
        super(dbms);
    }

    @Override
    public Stream<Schema> readMetadata() {

        final Schema schema = Schema.newSchema();
        schema.setName("test_schema");

        final Table table = Table.newTable();
        table.setTableName("test_table");

        final Column id = Column.newColumn();
        id.setName("id");
        id.setMapping(Integer.class);

        final Column name = Column.newColumn();
        id.setName("name");
        id.setMapping(String.class);

        final PrimaryKeyColumn pkc = PrimaryKeyColumn.newPrimaryKeyColumn();
        pkc.setName("id");

        schema.add(table);
        table.add(id);
        table.add(name);
        table.add(pkc);

        return Stream.of(schema);
    }

    @Override
    public <ENTITY> long readAll(Consumer<ENTITY> consumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <PK> ResultSet read(Table table, PK primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void insert(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void update(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void delete(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
