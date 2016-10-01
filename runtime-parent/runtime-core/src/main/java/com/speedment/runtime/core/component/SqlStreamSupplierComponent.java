package com.speedment.runtime.core.component;

import java.sql.ResultSet;

/**
 *
 * @author Per Minborg
 */
public interface SqlStreamSupplierComponent extends StreamSupplierComponent {

    public interface Support<ENTITY> {
        ENTITY from(ResultSet rs);
    }
}