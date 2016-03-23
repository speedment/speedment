/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.db.metadata;

/**
 *
 * @author Per Minborg
 */
public interface Element<T> {

    String getKey();

    T get();

}
