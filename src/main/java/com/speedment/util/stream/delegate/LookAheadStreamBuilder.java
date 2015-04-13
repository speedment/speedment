/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util.stream.delegate;

import com.speedment.util.stream.delegate.action.Action;
import com.speedment.util.stream.delegate.action.FilterAction;
import com.speedment.util.stream.delegate.action.DistinctAction;
import com.speedment.util.stream.delegate.action.FlatMapAction;
import com.speedment.util.stream.delegate.action.LimitAction;
import com.speedment.util.stream.delegate.action.MapAction;
import com.speedment.util.stream.delegate.action.OnCloseAction;
import com.speedment.util.stream.delegate.action.ParallelAction;
import com.speedment.util.stream.delegate.action.PeekAction;
import com.speedment.util.stream.delegate.action.SequentialAction;
import com.speedment.util.stream.delegate.action.SkipAction;
import com.speedment.util.stream.delegate.action.SortedAction;
import com.speedment.util.stream.delegate.action.SortedWithComparatorAction;
import com.speedment.util.stream.delegate.action.UnorderedAction;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> Stream type (Stream&lt;T&gt;)
 */
public abstract class LookAheadStreamBuilder<T> implements Stream<T> {

    private LookAheadStreamBuilder parent;
    private LookAheadStreamBuilder child;
    private final LinkedList<Action> actionsLocal;
    private boolean parallel;

    public LookAheadStreamBuilder(LookAheadStreamBuilder parent) {
        this.parent = parent;
        actionsLocal = new LinkedList<>();
        parallel = isParallelByDefault();
    }

    public LookAheadStreamBuilder() {
        this(null);
    }

    protected abstract boolean isParallelByDefault();

    /**
     * Creates and return an initial Stream using the provided look ahead
     * pipeline. The method may modify the given pipeline upon optimization of
     * the Stream.
     *
     * @return an initial Stream using the provided look ahead pipeline
     */
    protected abstract Stream<T> create();

    /**
     * Builds and returns a Stream corresponding to the look ahead pipeline.
     * parent
     *
     * @param terminatingAction the action that terminates this Stream
     * @return a Stream corresponding to the look ahead pipeline
     */
    protected Stream<T> build(TerminatingAction terminatingAction) {
        Stream<T> stream = create();
        for (final Action action : getActionsLocal()) {
            stream = action.apply(stream);
        }
        return stream;
    }

    protected Stream<Action> actionsLocal() {
        return getActionsLocal().stream();
    }

    protected Stream<Action> actionsGlobal() {
        // For the root node, the global is the local...
        return getActionsLocal().stream();
    }

    /////////////
    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        getActionsLocal().add(new FilterAction(predicate));
        return this;
    }

    @Override
    public <R> Stream<R> map(final Function<? super T, ? extends R> mapper) {
        return child = new MapLookAheadStreamBuilder<>(this, new MapAction(mapper));
        //return new MapLookAheadStream<>(this, mapper);
        //return build().map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return child = new FlatMapLookAheadStreamBuilder(this, new FlatMapAction(mapper));
        //return build(ToBeWrittenTerminatingAction.TBW).flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return build(ToBeWrittenTerminatingAction.TBW).flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        getActionsLocal().add(new DistinctAction());
        return this;
    }

    @Override
    public Stream<T> sorted() {
        getActionsLocal().add(new SortedAction());
        return this;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        getActionsLocal().add(new SortedWithComparatorAction(comparator));
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        getActionsLocal().add(new PeekAction(action)); // Todo: Make sure this is handled correctly
        return this;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        getActionsLocal().add(new LimitAction(maxSize));
        return this;
    }

    @Override
    public Stream<T> skip(long n) {
        getActionsLocal().add(new SkipAction(n));
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        build(StandardTerminatingAction.FOR_EACH).forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        build(StandardTerminatingAction.FOR_EACH_ORDERED).forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return build(StandardTerminatingAction.TO_ARRAY).toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return build(StandardTerminatingAction.TO_ARRAY_GENERATOR).toArray(generator);
    }

    @Override

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return build(StandardTerminatingAction.REDUCE_IDENTITY_ACCUMULATOR).reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return build(StandardTerminatingAction.REDUCE_ACCUMULATOR).reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return build(StandardTerminatingAction.REDUCE_IDENTITY_ACCUMULATOR_COMBINER).reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return build(StandardTerminatingAction.COLLECT_SUPPLIER_ACCUMULATOR_COMBINER).collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return build(StandardTerminatingAction.COLLECT_COLLECTOR).collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return build(StandardTerminatingAction.MIN).min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return build(StandardTerminatingAction.MAX).max(comparator);
    }

    @Override
    public long count() {
        return build(StandardTerminatingAction.COUNT).count(); // Todo: Potential for optimization!
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return build(StandardTerminatingAction.ANY_MATCH).anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return build(StandardTerminatingAction.ALL_MATCH).allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return build(StandardTerminatingAction.NONE_MATCH).noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return build(StandardTerminatingAction.FIND_FIRST).findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return build(StandardTerminatingAction.FIND_ANY).findAny();
    }

    ////////////////////
    @Override
    public Iterator<T> iterator() {
        return build(StandardTerminatingAction.ITERATOR).iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return build(StandardTerminatingAction.SPLITERATOR).spliterator();
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public Stream<T> sequential() {
        getActionsLocal().add(new SequentialAction());
        parallel = false;
        return this;
    }

    @Override
    public Stream<T> parallel() {
        getActionsLocal().add(new ParallelAction());
        parallel = true;
        return this;
    }

    @Override
    public Stream<T> unordered() {
        getActionsLocal().add(new UnorderedAction());
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        getActionsLocal().add(new OnCloseAction(closeHandler));
        return this;
    }

    @Override
    public void close() {
        throw new IllegalStateException("It is illegal to call close() directly on a " + LookAheadStreamBuilder.class.getSimpleName());
    }

    public LookAheadStreamBuilder<?> root() {
        return root(this);
    }

    private LookAheadStreamBuilder<?> root(LookAheadStreamBuilder<?> current) {
        if (!current.getParent().isPresent()) {
            return current;
        }
        return root(current.getParent().get());
    }

    public LookAheadStreamBuilder<?> leaf() {
        return leaf(this);
    }

    private LookAheadStreamBuilder<?> leaf(LookAheadStreamBuilder<?> current) {
        if (!current.getChild().isPresent()) {
            return current;
        }
        return leaf(current.getChild().get());
    }

    public Optional<LookAheadStreamBuilder> getParent() {
        return Optional.ofNullable(parent);
    }

    protected LinkedList<Action> getActionsLocal() {
        return actionsLocal;
    }

    public Optional<LookAheadStreamBuilder> getChild() {
        return Optional.ofNullable(child);
    }

    public void setChild(LookAheadStreamBuilder child) {
        this.child = child;
    }

    public void setParent(LookAheadStreamBuilder parent) {
        this.parent = parent;
    }

    ////////////////////
}
