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
package com.speedment.common.injector.exception;

/**
 * An exception that is thrown if a particular class can't be used as an 
 * injectable parameter.
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class NotInjectableException extends RuntimeException {

    private static final long serialVersionUID = 7548382043527278597L;

    public NotInjectableException(Class<?> clazz) {
        super(message(clazz));
    }

    public NotInjectableException(Class<?> clazz, Throwable cause) {
        super(message(clazz), cause);
    }
    
    private static String message(Class<?> clazz) {
        return "Class '" + clazz + "' can't be injected.";
    }
}