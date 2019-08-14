package com.speedment.runtime.config.trait;

public final class HasTypeMapperUtil {

    private HasTypeMapperUtil() {}

    /**
     * The attribute under which the name of the {@code TypeMapper} is stored in
     * the configuration file.
     */
    public static final String TYPE_MAPPER = "typeMapper";
    /**
     * The attribute under which the database type is stored in the
     * configuration file. This is used initially to determine the default
     * {@code TypeMapper} to use.
     */
    public static final String DATABASE_TYPE  = "databaseType";

}
