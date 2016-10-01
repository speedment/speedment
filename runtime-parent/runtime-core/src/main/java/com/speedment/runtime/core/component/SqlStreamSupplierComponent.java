/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.component;

import java.sql.ResultSet;

/**
 *
 * @author Per Minborg
 */
public interface SqlStreamSupplierComponent extends StreamSupplierComponent {

    public interface Support<ENTITY> {

        String getSql();
        
        ENTITY from(ResultSet rs);

    }

}
