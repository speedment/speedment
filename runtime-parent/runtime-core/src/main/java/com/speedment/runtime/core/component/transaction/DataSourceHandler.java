package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.internal.component.transaction.DataSourceHandlerImpl;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 * @param <D> DataSource type (e.g. Dbms)
 * @param <T> Transaction aware object (e.g. Connection)
 * @since 3.0.17
 */
public interface DataSourceHandler<D, T> {

    Function<? super D, ? extends T> extractor();

    Consumer<? super T> beginner();

    Consumer<? super T> rollbacker();

    Consumer<? super T> committer();

    Consumer<? super T> closer();

    static <D, T> DataSourceHandler<D, T> of(
        final Function<D, T> extractor,
        final Consumer<? super T> beginner,
        final Consumer<? super T> committer,
        final Consumer<? super T> rollbacker,
        final Consumer<? super T> closer
    ) {
        return new DataSourceHandlerImpl<>(extractor, beginner, committer, rollbacker, closer);
    }

}
