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

    /**
     * Attaches the current thread to this Transaction.
     * <p>
     * After this call, data operations executed by the current thread will be
     * issued within this transaction's scope.
     * <p>
     * NB: Not all databases support transaction operations carried out by by a
     * plurality of threads.
     *
     * @throws IllegalStateException if the current thread is already associated
     * with another Transaction within any transaction domain.
     */
    void attachCurrentThread();

    /**
     * Detaches the current thread from this Transaction. If the current thread
     * is not attached to this Transaction, then this method is a no-operation.
     * <p>
     * After this call, data operations executed by the current thread will be
     * issued separated from this transaction's scope.
     */
    void detachCurrentThread();
    
//    /**
//     * Closes this transaction, rolling back any uncommitted updates, detatching
//     * all attached threads and relinquishing any underlying resources.
//     */
//    void close();
}
