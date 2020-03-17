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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.CombinedComparator;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.method.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link CombinedComparator}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class CombinedComparatorImpl<ENTITY>
implements CombinedComparator<ENTITY> {

    private final List<FieldComparator<? super ENTITY>> comparators;

    public CombinedComparatorImpl(
            final List<FieldComparator<? super ENTITY>> comparators) {

        this.comparators = requireNonNull(comparators);
    }

    @Override
    public Stream<FieldComparator<? super ENTITY>> stream() {
        return comparators.stream();
    }

    @Override
    public int size() {
        return comparators.size();
    }

    @Override
    public int compare(ENTITY o1, ENTITY o2) {
        for (final FieldComparator<? super ENTITY> comp : comparators) {
            final int c = comp.compare(o1, o2);
            if (c != 0) return c;
        }

        return 0;
    }

    @Override
    public CombinedComparator<ENTITY> reversed() {
        return new CombinedComparatorImpl<>(comparators.stream()
            .map(FieldComparator::reversed)
            .collect(Collectors.toList())
        );
    }

    @Override
    public Comparator<ENTITY> thenComparing(Comparator<? super ENTITY> other) {
        return then(other);
    }

    @Override
    public <U> Comparator<ENTITY> thenComparing(
            final Function<? super ENTITY, ? extends U> keyExtractor,
            final Comparator<? super U> keyComparator) {

        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                return keyComparator.compare(
                    keyExtractor.apply(a),
                    keyExtractor.apply(b)
                );
            } else return c;
        };
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<ENTITY>
    thenComparing(Function<? super ENTITY, ? extends U> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final U oa = keyExtractor.apply(a);
                final U ob = keyExtractor.apply(b);
                if (oa == null && ob == null) {
                    return 0;
                } else if (oa == null) {
                    return 1;
                } else if (ob == null) {
                    return -1;
                } else {
                    return oa.compareTo(ob);
                }
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingInt(ToIntFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final int oa = keyExtractor.applyAsInt(a);
                final int ob = keyExtractor.applyAsInt(b);
                return Integer.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingLong(ToLongFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final long oa = keyExtractor.applyAsLong(a);
                final long ob = keyExtractor.applyAsLong(b);
                return Long.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingDouble(ToDoubleFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final double oa = keyExtractor.applyAsDouble(a);
                final double ob = keyExtractor.applyAsDouble(b);
                return Double.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombinedComparator)) return false;

        final CombinedComparator<?> that = (CombinedComparator<?>) o;
        final Iterator<FieldComparator<? super ENTITY>> it =
            comparators.iterator();

        return that.stream().allMatch(
            c -> it.hasNext() && it.next().equals(c)
        ) && !it.hasNext();
    }

    @Override
    public int hashCode() {
        return 1299827 * comparators.hashCode();
    }

    @Override
    public String toString() {
        return "CombinedComparatorImpl" + comparators;
    }

    private Comparator<ENTITY> then(Comparator<? super ENTITY> other) {
        if (other instanceof FieldComparator) {
            @SuppressWarnings("unchecked")
            final FieldComparator<? super ENTITY> fc =
                (FieldComparator<? super ENTITY>) other;

            final List<FieldComparator<? super ENTITY>> copy =
                new ArrayList<>(comparators);

            copy.add(fc);

            return new CombinedComparatorImpl<>(copy);
        } else if (other instanceof CombinedComparator) {
            @SuppressWarnings("unchecked")
            final CombinedComparator<? super ENTITY> cc =
                (CombinedComparator<? super ENTITY>) other;

            final List<FieldComparator<? super ENTITY>> copy =
                new ArrayList<>(comparators);

            cc.stream().forEachOrdered(copy::add);

            return new CombinedComparatorImpl<>(copy);
        } else {
            return (a, b) -> {
                final int c = compare(a, b);
                return c == 0 ? other.compare(a, b) : c;
            };
        }
    }

    private <D, V extends Comparable<? super V>>
    Optional<Comparator<ENTITY>> then(Getter<? super ENTITY> getter) {

        if (getter instanceof GetByte) {
            @SuppressWarnings("unchecked")
            final GetByte<ENTITY, D> casted = (GetByte<ENTITY, D>) getter;
            return Optional.of(then(new ByteFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetShort) {
            @SuppressWarnings("unchecked")
            final GetShort<ENTITY, D> casted = (GetShort<ENTITY, D>) getter;
            return Optional.of(then(new ShortFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetInt) {
            @SuppressWarnings("unchecked")
            final GetInt<ENTITY, D> casted = (GetInt<ENTITY, D>) getter;
            return Optional.of(then(new IntFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetLong) {
            @SuppressWarnings("unchecked")
            final GetLong<ENTITY, D> casted = (GetLong<ENTITY, D>) getter;
            return Optional.of(then(new LongFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetFloat) {
            @SuppressWarnings("unchecked")
            final GetFloat<ENTITY, D> casted = (GetFloat<ENTITY, D>) getter;
            return Optional.of(then(new FloatFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetDouble) {
            @SuppressWarnings("unchecked")
            final GetDouble<ENTITY, D> casted = (GetDouble<ENTITY, D>) getter;
            return Optional.of(then(new DoubleFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetChar) {
            @SuppressWarnings("unchecked")
            final GetChar<ENTITY, D> casted = (GetChar<ENTITY, D>) getter;
            return Optional.of(then(new CharFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetReference) {
            @SuppressWarnings("unchecked")
            final GetReference<ENTITY, D, V> casted = (GetReference<ENTITY, D, V>) getter;
            final Field<ENTITY> field = casted.getField();
            if (field instanceof ComparableField) {
                return Optional.of(then(new ReferenceFieldComparatorImpl<>(
                    (ComparableField<ENTITY, D, V>) casted.getField(),
                    NullOrder.LAST
                )));
            }
        }

        return Optional.empty();
    }
}