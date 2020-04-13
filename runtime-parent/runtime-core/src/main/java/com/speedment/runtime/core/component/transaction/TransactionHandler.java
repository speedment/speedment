/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.exception.TransactionException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public interface TransactionHandler {

    /**
     * Sets the {@link Isolation} level for subsequent transactions created by
     * this transaction handler.
     *
     * @param level the new Isolation level to use
     * @throws NullPointerException if the provided isolation level is null
     */
    void setIsolation(Isolation level);

    /**
     * Returns the current isolation level used for new transactions.
     *
     * @return the current isolation level used for new transactions.
     */
    Isolation getIsolation();

    /**
     * Creates a new {@link Transaction} and invokes the provided action with
     * the new transaction.
     * <p>
     * Data that has not been explicitly committed will be automatically rolled
     * back after the action has been invoked. Explicitly invoking {@link Transaction#rollback()
     * } at the end of the action is a redundant operation.
     * <p>
     * If a {@link TransactionException } is thrown, parts of the transaction
     * that has not been previously explicitly committed using {@link Transaction#commit()
     * } will be automatically rolled back.
     * <p>
     * NB: The transaction is only valid within the scope of the call and only
     * for the current Thread.
     * <p>
     * EXAMPLE:
     * <pre>
     * {@code
     *     // Print out all films and languages in a single transaction
     *     txHandler.createAndAccept(tx -> {
     *         films.stream().forEach(System.out::println);
     *         languages.stream().forEach(System.out::println);
     *     });
     * }
     * </pre>
     *
     * @param action to be performed on the new transaction
     * @throws TransactionException if the action throws an exception
     * @throws NullPointerException if the provided mapper is null
     */
    default void createAndAccept(Consumer<? super Transaction> action) {
        createAndApply(tx -> {
            action.accept(tx);
            return null;
        });
    }

    /**
     * Creates a new {@link Transaction } and returns the value of applying the
     * given function to the new transaction.
     * <p>
     * Data that has not been explicitly committed will be automatically rolled
     * back after the action has been invoked. Explicitly invoking {@link Transaction#rollback()
     * } at the end of the action is a redundant operation.
     * <p>
     * If a {@link TransactionException } is thrown, parts of the transaction
     * that has not been previously explicitly committed using {@link Transaction#commit()
     * } will be automatically rolled back.
     * <p>
     * NB: The transaction is only valid within the scope of the call and only
     * for the current Thread. EXAMPLE:
     * <pre>
     * {@code
     *     // Retrieve a list of all films in the English language
     *     // in a single transaction.
     *     List<Film> filmsInEnglish = txHandler.createAndAccept(tx ->
     *         languages.stream()
     *             .filter(Language.NAME.equal("English"))
     *             .flatMap(films.finderBackwardsBy(Film.LANGUAGE_ID))
     *             .collect(Collectors.toList())
     *     );
     * }
     * </pre>
     *
     * @param <R> Return type of the provided mapper
     * @param mapper to apply on the new transaction
     * @return the result of the mapper
     * @throws TransactionException if the action throws an exception
     * @throws NullPointerException if the provided mapper is null
     */
    <R> R createAndApply(Function<? super Transaction, ? extends R> mapper);

}
