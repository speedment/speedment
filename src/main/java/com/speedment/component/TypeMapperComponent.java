package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.config.mapper.TypeMapper;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version="2.2")
public interface TypeMapperComponent extends Component {
    
    /**
     * Installs the specified type mapper in this component.
     * 
     * @param <DB_TYPE>    the database type
     * @param <JAVA_TYPE>  the java type
     * @param typeMapper   the mapper to install
     */
    <DB_TYPE, JAVA_TYPE> void install(TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper);
    
    /**
     * Streams over all the type mappers installed in this component.
     * 
     * @param <DB_TYPE>    the database type
     * @param <JAVA_TYPE>  the java type
     * @return             all mappers
     */
    <DB_TYPE, JAVA_TYPE> Stream<TypeMapper<DB_TYPE, JAVA_TYPE>> stream();
}