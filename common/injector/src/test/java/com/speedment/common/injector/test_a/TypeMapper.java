package com.speedment.common.injector.test_a;

/**
 *
 * @param <DB_TYPE>    the type used in the JDBC driver
 * @param <JAVA_TYPE>  the type used to represent it in code
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {
    
    DB_TYPE toDatabase(JAVA_TYPE value);
    JAVA_TYPE toJava(DB_TYPE value);
    
}
