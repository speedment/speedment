package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.exception.TransactionException;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface Transaction {

    /**
     * Makes all changes made since the previous commit/rollback permanent and
     * releases any transaction domain locks currently affected by this
     * <code>Transaction</code> object.
     *
     * @throws TransactionException if an exception is thrown by the underlying
     * transaction aware object (e.g. an SqlException is thrown)
     */
    void commit() throws TransactionException;

    /**
     * Undoes all changes made in the current transaction and releases any
     * transaction domain locks currently affected by this
     * <code>Transaction</code> object.
     *
     * @throws TransactionException if an exception is thrown by the underlying
     * transaction aware object (e.g. an SqlException is thrown)
     */
    void rollback() throws TransactionException;

}
