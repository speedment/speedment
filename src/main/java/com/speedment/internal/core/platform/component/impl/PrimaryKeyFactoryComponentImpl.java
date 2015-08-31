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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.internal.core.platform.component.PrimaryKeyFactoryComponent;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 *
 * @author pemi
 */
public final class PrimaryKeyFactoryComponentImpl implements PrimaryKeyFactoryComponent {

    @Override
    public <K0, K1> List<?> make(K0 k0, K1 k1) {
        // keys nullable
        return makeHelper(k0, k1);
    }

    @Override
    public <K0, K1, K2> List<?> make(K0 k0, K1 k1, K2 k2) {
        // keys nullable
        return makeHelper(k0, k1, k2);
    }

    @Override
    public <K0, K1, K2, K3> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3) {
        // keys nullable
        return makeHelper(k0, k1, k2, k3);
    }

    @Override
    public <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4) {
        // keys nullable
        return makeHelper(k0, k1, k2, k3, k4);
    }

    @Override
    public <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4, Object... restOfTheKeys) {
        requireNonNull(restOfTheKeys);
        // keys them selves nullable
        final Object[] keys = new Object[5 + restOfTheKeys.length];
        keys[0] = k0;
        keys[1] = k1;
        keys[2] = k2;
        keys[3] = k3;
        keys[4] = k4;
        System.arraycopy(restOfTheKeys, 0, keys, 5, restOfTheKeys.length);
        return makeHelper(keys);
    }

    @Override
    public Class<PrimaryKeyFactoryComponent> getComponentClass() {
        return PrimaryKeyFactoryComponent.class;
    }

    private List<?> makeHelper(Object... keys) {
        return new UnmodifiableArrayList<>(keys);
    }

    /**
     * @serial include
     */
    protected static class UnmodifiableArrayList<E> extends AbstractList<E> implements RandomAccess, java.io.Serializable {

        private static final long serialVersionUID = -2376123511671171717L;
        private final E[] a;

        UnmodifiableArrayList(E[] array) {
            a = Objects.requireNonNull(array);
        }

        @Override
        public int size() {
            return a.length;
        }

        @Override
        public Object[] toArray() {
            return a.clone();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            int size = size();
            if (a.length < size) {
                return Arrays.copyOf(this.a, size,
                    (Class<? extends T[]>) a.getClass());
            }
            System.arraycopy(this.a, 0, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }

        @Override
        public E get(int index) {
            return a[index];
        }

        @Override
        public E set(int index, E element) {
            return throwUnmodifiableException();
        }

        @Override
        public boolean add(E e) {
            throwUnmodifiableException();
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throwUnmodifiableException();
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throwUnmodifiableException();
            return false;
        }

        @Override
        public boolean remove(Object o) {
            throwUnmodifiableException();
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throwUnmodifiableException();
            return false;
        }

        @Override
        public int indexOf(Object o) {
            E[] a = this.a;
            if (o == null) {
                for (int i = 0; i < a.length; i++) {
                    if (a[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = 0; i < a.length; i++) {
                    if (o.equals(a[i])) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public boolean contains(Object o) {
            return indexOf(o) != -1;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(a, Spliterator.ORDERED);
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (E e : a) {
                action.accept(e);
            }
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            throwUnmodifiableException();
        }

        @Override
        public void sort(Comparator<? super E> c) {
            throwUnmodifiableException();
        }

        // TODO: Override listIterator() and listIterator(final int index)?
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return Collections.unmodifiableList(super.subList(fromIndex, toIndex));
        }

        private E throwUnmodifiableException() {
            throw new UnsupportedOperationException("a primary key List is unmodifiable");
        }

    }

}
