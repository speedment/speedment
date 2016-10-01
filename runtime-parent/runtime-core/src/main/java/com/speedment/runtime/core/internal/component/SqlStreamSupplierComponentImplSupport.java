package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.SqlStreamSupplierComponent;

/**
 *
 * @author Per Minborg
 */
public class SqlStreamSupplierComponentImplSupport {

    private final SqlStreamSupplierComponent.Support support;

    public SqlStreamSupplierComponentImplSupport(SqlStreamSupplierComponent.Support support) {
        this.support = support;
    }

    public String getSql() {
        return null;
    }

}
