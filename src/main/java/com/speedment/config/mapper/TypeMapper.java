package com.speedment.config.mapper;

import com.speedment.annotation.Api;

/**
 *
 * @author Emil Forslund
 * @param <DB_TYPE>    the type as it is represented in the JDBC driver
 * @param <JAVA_TYPE>  the type as it should be represented in generated code
 * @since 2.2
 */
@Api(version="2.2")
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {
    
    /**
     * Returns the label for this mapper that should appear to the end user.
     * 
     * @return  the label
     */
    default String getLabel() {
        return getClass().getSimpleName();
    }
    
    /**
     * Returns the type as it should be represented in generated code.
     * @return  the type
     */
    Class<JAVA_TYPE> getJavaType();
    
    /**
     * Returns the type as it is represented in the JDBC driver.
     * @return  the type
     */
    Class<DB_TYPE> getDatabaseType();
    
    /**
     * Converts a value from the database domain to the java domain.
     * 
     * @param value  the value to convert
     * @return       the converted value
     */
    JAVA_TYPE toJavaType(DB_TYPE value);
    
    /**
     * Converts a value from the java domain to the database domain.
     * 
     * @param value  the value to convert
     * @return       the converted value
     */
    DB_TYPE toDatabaseType(JAVA_TYPE value);
}