package com.speedment.runtime.transaction.internal;

import com.speedment.runtime.transaction.DataSourceHandler;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 */
public class DataSourceHandlerImpl<D, T> implements DataSourceHandler<D, T> {

    private final Function<? super D, ? extends T> extractor;
    private final Consumer<? super T> beginner;
    private final Consumer<? super T> committer;
    private final Consumer<? super T> rollbacker;
    private final Consumer<? super T> closer;

    public DataSourceHandlerImpl(
        final Function<? super D, ? extends T> extractor,
        final Consumer<? super T> beginner,
        final Consumer<? super T> committer,
        final Consumer<? super T> rollbacker,
        final Consumer<? super T> closer
    ) {
        this.extractor = requireNonNull(extractor);
        this.beginner = requireNonNull(beginner);
        this.committer = requireNonNull(committer);
        this.rollbacker = requireNonNull(rollbacker);
        this.closer = requireNonNull(closer);
    }

    @Override
    public Function<? super D, ? extends T> extractor() {
        return extractor;
    }

    @Override
    public Consumer<? super T> beginner() {
        return beginner;
    }

    @Override
    public Consumer<? super T> committer() {
        return committer;
    }

    @Override
    public Consumer<? super T> rollbacker() {
        return rollbacker;
    }

    @Override
    public Consumer<? super T> closer() {
        return closer;
    }

}
