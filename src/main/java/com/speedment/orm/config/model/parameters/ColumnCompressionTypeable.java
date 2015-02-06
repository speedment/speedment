package com.speedment.orm.config.model.parameters;

/**
 *
 * @author pemi
 * @param <T> Setter return type.
 */
public interface ColumnCompressionTypeable<T> {

    ColumnCompressionType getColumnCompressionType();

    T setColumnCompressionType(ColumnCompressionType fieldStorageType);

}
