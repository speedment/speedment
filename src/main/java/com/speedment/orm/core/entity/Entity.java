package com.speedment.orm.core.entity;

/**
 *
 * @author Emil Forslund
 */
public interface Entity<PK> {
    
    PK getPrimaryKey();
}
