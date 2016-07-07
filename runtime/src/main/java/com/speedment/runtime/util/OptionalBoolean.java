/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.util;

import com.speedment.runtime.annotation.Api;

import java.util.function.Supplier;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "3.0")
public enum OptionalBoolean {
    
    FALSE, TRUE, EMPTY;
    
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
    
    public OptionalBoolean filter(BooleanPredicate predicate) {
        if (this == EMPTY || predicate.test(get())) {
            return this;
        } else {
            return EMPTY;
        }
    }
    
    public boolean get() {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default :
                throw new NullPointerException(
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
    
    public <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default    : throw thrower.get();
        }
    }
    
    public boolean isPresent() {
        return this != EMPTY;
    }
    
    public void ifPresent(BooleanConsumer consumer) {
        switch (this) {
            case FALSE : { consumer.accept(false); break;}
            case TRUE  : { consumer.accept(true); break;}
            default: // do nothing
        }
    }
}