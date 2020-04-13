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
package com.speedment.common.function;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * An {@code Optional} specialized for primitive boolean values.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public enum OptionalBoolean {
    
    FALSE {
        @Override
        public <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T {
            return false;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public void ifPresent(BooleanConsumer consumer) {
            consumer.accept(false);
        }
    }, TRUE {

        @Override
        public <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T {
            return true;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public void ifPresent(BooleanConsumer consumer) {
            consumer.accept(true);
        }
    }, EMPTY{
        @Override
        public <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T {
            throw thrower.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public void ifPresent(BooleanConsumer consumer) {
            // Do nothing
        }
    };
    
    public static OptionalBoolean empty() {
        return EMPTY;
    }
    
    public static OptionalBoolean of(boolean bool) {
        return bool ? TRUE : FALSE;
    }
    
    public static OptionalBoolean ofNullable(Boolean bool) {
        if (bool == null) {
            return empty();
        } else {
            return of(bool);
        }
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return a {@code BooleanOptional} describing the value, otherwise return an
     * empty {@code BooleanOptional}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return a {@code BooleanOptional} describing the value of this {@code BooleanOptional}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code BooleanOptional}
     * @throws NullPointerException if the predicate is null
     */
    public OptionalBoolean filter(BooleanPredicate predicate) {
        requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(this == TRUE) ? this : empty();
    }
    
    public boolean getAsBoolean() {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default :
                throw new NoSuchElementException(
                    "Attempted to get value from empty OptionalBoolean."
                );
        }
    }
    
    public boolean orElse(boolean ifEmpty) {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default    : return ifEmpty;
        }
    }
    
    public abstract <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T;

    public abstract boolean isPresent();

    public abstract void ifPresent(BooleanConsumer consumer);

}