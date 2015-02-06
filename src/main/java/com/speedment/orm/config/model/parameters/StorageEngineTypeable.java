/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.config.model.parameters;

/**
 *
 * @author pemi
 * @param <T> Setter return type.
 */
public interface StorageEngineTypeable<T> {

    StorageEngineType getStorageEngineType();

    T setFieldStorageType(StorageEngineType storageEngineType);

}
