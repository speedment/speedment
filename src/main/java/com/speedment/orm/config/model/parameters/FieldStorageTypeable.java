package com.speedment.orm.config.model.parameters;

/**
 *
 * @author pemi
 * @param <T> Setter return type.
 */
public interface FieldStorageTypeable<T> {

    FieldStorageType getFieldStorageType();

    T setFieldStorageType(FieldStorageType fieldStorageType);

}
