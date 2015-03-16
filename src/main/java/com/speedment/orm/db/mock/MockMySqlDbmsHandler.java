package com.speedment.orm.db.mock;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.PrimaryKeyColumn;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.config.model.parameters.DbmsType;
import com.speedment.orm.config.model.parameters.StandardDbmsType;
import com.speedment.orm.db.DbmsHandler;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class MockMySqlDbmsHandler implements DbmsHandler {

    @Override
    public DbmsType getDbmsType() {
        return StandardDbmsType.MYSQL;
    }

    @Override
    public Stream<Schema> readMetadata(Dbms dbms) {
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
