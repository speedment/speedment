package com.speedment.runtime.test_support;

import com.speedment.runtime.core.db.ConnectionUrlGenerator;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.internal.db.AbstractDbmsType;
import com.speedment.runtime.core.internal.manager.sql.MySqlSpeedmentPredicateView;

/**
 *
 * @author Per Minborg
 */
public class MockDbmsType extends AbstractDbmsType implements DbmsType {

    @Override
    public String getName() {
        return "MockDb";
    }

    @Override
    public String getDriverManagerName() {
        return "MockDB JDBC Driver";
    }

    @Override
    public int getDefaultPort() {
        return 42;
    }

    @Override
    public String getDbmsNameMeaning() {
        return "mock";
    }

    @Override
    public String getDriverName() {
        return "MockDb";
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return dbms -> "jdbc:mockdb://" + dbms.getIpAddress().orElse("") + ":" + dbms.getPort().orElse(0);
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return new MySqlSpeedmentPredicateView();
    }

}
