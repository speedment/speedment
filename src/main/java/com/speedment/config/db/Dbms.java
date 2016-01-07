package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Dbms extends Document, HasParent<Project>, HasEnabled, HasName {
    
    final String
        DBMS_TYPE  = "dbmsType",
        IP_ADDRESS = "ipAddress",
        PORT       = "port",
        USERNAME   = "username",
        SCHEMAS    = "schemas";
    
    default String getTypeName() {
        return (String) get(DBMS_TYPE).get();
    }
    
    /**
     * Returns the address of the database host if it is specified. The address
     * could be an ip-address or a hostname. If no address is specified,
     * {@code empty} will be returned.
     *
     * @return the address of the host or {@code empty}
     */
    default Optional<String> getIpAddress() {
        return getAsString(IP_ADDRESS);
    }
    
    /**
     * Returns the port number of the database on the database host. If no port
     * is specified, {@code empty} is returned.
     *
     * @return the port of the database or {@code empty}
     */
    default OptionalInt getPort() {
        return getAsInt(PORT);
    }
    
    /**
     * Returns the database username to use when connecting to the dbms. If no
     * username is specified, {@code empty} is returned.
     *
     * @return the database username or {@code empty}
     */
    default Optional<String> getUsername() {
        return getAsString(USERNAME);
    }
    
    default Stream<Schema> schemas() {
        return children(SCHEMAS, schemaConstructor());
    }
    
    default Schema newSchema() {
        return schemaConstructor().apply(this, newDocument(this, SCHEMAS));
    }
    
    BiFunction<Dbms, Map<String, Object>, Schema> schemaConstructor();
}