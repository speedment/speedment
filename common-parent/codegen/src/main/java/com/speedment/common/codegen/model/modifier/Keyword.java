/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model.modifier;

import com.speedment.common.codegen.model.trait.HasModifiers;

import static com.speedment.common.codegen.model.modifier.Modifier.*;

/**
 *
 * @author Emil Forslund
 * @since  2.0.0
 */
public interface Keyword {
    interface Public<T extends Public<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T public_() {
            getModifiers().add(PUBLIC);
            return (T) this;
        }
    }
    
    interface Protected<T extends Protected<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T protected_() {
            getModifiers().add(PROTECTED);
            return (T) this;
        }
    }
    
    interface Private<T extends Private<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T private_() {
            getModifiers().add(PRIVATE);
            return (T) this;
        }
    }
    
    interface Static<T extends Static<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T static_() {
            getModifiers().add(STATIC);
            return (T) this;
        }
    }
    
    interface Final<T extends Final<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T final_() {
            getModifiers().add(FINAL);
            return (T) this;
        }
    }
    
    interface Abstract<T extends Abstract<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T abstract_() {
            getModifiers().add(ABSTRACT);
            return (T) this;
        }
    }
    
    interface Strictfp<T extends Strictfp<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T strictfp_() {
            getModifiers().add(STRICTFP);
            return (T) this;
        }
    }
    
    interface Synchronized<T extends Synchronized<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T synchronized_() {
            getModifiers().add(SYNCHRONIZED);
            return (T) this;
        }
    }
    
    interface Transient<T extends Transient<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T transient_() {
            getModifiers().add(TRANSIENT);
            return (T) this;
        }
    }
    
    interface Volatile<T extends Volatile<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T volatile_() {
            getModifiers().add(VOLATILE);
            return (T) this;
        }
    }
    
    interface Native<T extends Native<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T native_() {
            getModifiers().add(NATIVE);
            return (T) this;
        }
    }
	
    interface Default<T extends Default<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T default_() {
            getModifiers().add(DEFAULT);
            return (T) this;
        }
    }
}