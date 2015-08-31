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
package com.speedment.internal.codegen.lang.models.modifiers;

import com.speedment.internal.codegen.lang.interfaces.HasModifiers;
import static com.speedment.internal.codegen.lang.models.modifiers.Modifier.*;

/**
 *
 * @author Emil Forslund
 */
public interface Keyword {
    interface public_<T extends public_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T public_() {
            getModifiers().add(PUBLIC);
            return (T) this;
        }
    }
    
    interface protected_<T extends protected_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T protected_() {
            getModifiers().add(PROTECTED);
            return (T) this;
        }
    }
    
    interface private_<T extends private_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T private_() {
            getModifiers().add(PRIVATE);
            return (T) this;
        }
    }
    
    interface static_<T extends static_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T static_() {
            getModifiers().add(STATIC);
            return (T) this;
        }
    }
    
    interface final_<T extends final_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T final_() {
            getModifiers().add(FINAL);
            return (T) this;
        }
    }
    
    interface abstract_<T extends abstract_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T abstract_() {
            getModifiers().add(ABSTRACT);
            return (T) this;
        }
    }
    
    interface strictfp_<T extends strictfp_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T strictfp_() {
            getModifiers().add(STRICTFP);
            return (T) this;
        }
    }
    
    interface synchronized_<T extends synchronized_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T synchronized_() {
            getModifiers().add(SYNCHRONIZED);
            return (T) this;
        }
    }
    
    interface transient_<T extends transient_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T transient_() {
            getModifiers().add(TRANSIENT);
            return (T) this;
        }
    }
    
    interface volatile_<T extends volatile_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T volatile_() {
            getModifiers().add(VOLATILE);
            return (T) this;
        }
    }
    
    interface native_<T extends native_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T native_() {
            getModifiers().add(NATIVE);
            return (T) this;
        }
    }
	
    interface default_<T extends default_<T>> extends HasModifiers<T> {
        @SuppressWarnings("unchecked")
        default T default_() {
            getModifiers().add(DEFAULT);
            return (T) this;
        }
    }
}