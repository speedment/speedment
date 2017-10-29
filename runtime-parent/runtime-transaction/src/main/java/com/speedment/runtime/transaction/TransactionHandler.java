package com.speedment.runtime.transaction;

import com.speedment.runtime.transaction.exception.TransactionException;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface TransactionHandler {

    void invoke(Consumer<Transaction> action) throws TransactionException;

}
