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
     * If no TransactionException is thrown, the transaction will be
     * automatically committed after the action has been invoked. Explicitly
     * invoking {@link Transaction#rollback() } at the end of the action
     * prevents anything from being automatically committed.
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
     *     // Print out all films and languages in a sigle transaction
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
    default void createAndAccept(Consumer<? super Transaction> action) throws TransactionException {
        createAndApply(tx -> {
            action.accept(tx);
            return null;
        });
    }

    /**
     * Creates a new {@link Transaction } and returns the value of applying the
     * given function to the new transaction.
     * <p>
     * If no {@link TransactionException } is thrown, the transaction will be
     * automatically committed after the function has been applied. Explicitly
     * invoking {@link Transaction#rollback() } at the end of the function
     * prevents anything from being automatically committed.
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
     *     // in a sigle transaction.
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
    <R> R createAndApply(Function<? super Transaction, ? extends R> mapper) throws TransactionException;

//    /**
//     * Creates and returns a new transaction.
//     * 
//     * @return 
//     */
//    Transaction create();
}
