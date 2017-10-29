package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.exception.TransactionException;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface TransactionHandler {

    void invoke(Consumer<Transaction> action) throws TransactionException;

}
