package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.exception.TransactionException;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface Transaction {

    void commit() throws TransactionException;

    void rollback() throws TransactionException;

}
