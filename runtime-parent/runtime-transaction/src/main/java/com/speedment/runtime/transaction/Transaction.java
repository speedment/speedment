package com.speedment.runtime.transaction;

import com.speedment.runtime.transaction.exception.TransactionException;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface Transaction {

    void commit() throws TransactionException;

    void rollback() throws TransactionException;

}
