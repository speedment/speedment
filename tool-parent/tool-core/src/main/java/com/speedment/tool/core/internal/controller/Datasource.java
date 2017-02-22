package com.speedment.tool.core.internal.controller;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import com.speedment.runtime.core.internal.util.Settings;

/**
 * Created by kkumar1 on 2/22/17.
 * This is wrapper class for jdbc datasource
 */
public class Datasource {

    private  static DataSource dataSource = null;
    private static String dataSourceJNDIName = null;
    //Initializing the Datasource from the J2EE server.
    static {

        try {
            dataSourceJNDIName = Settings.inst().get("Datasource");
            dataSource = (DataSource) new InitialContext().lookup(dataSourceJNDIName);
        }
        catch (NamingException e) {
            throw new ExceptionInInitializerError("Lookup failed for the JDBC datasource:"+dataSourceJNDIName);
        }
    }

    /**
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            if(null == dataSource){
                dataSourceJNDIName = Settings.inst().get("Datasource");
                dataSource = (DataSource) new InitialContext().lookup(dataSourceJNDIName);
            }
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

}
