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
 * A runtime exception that is thrown if a class specified does
 * not have a default constructor.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class NoDefaultConstructorException extends RuntimeException {

    private static final long serialVersionUID = 6432725938889199455L;

    public NoDefaultConstructorException(String msg) {
        super(msg);
    }
    
    public NoDefaultConstructorException(Throwable thrw) {
        super(thrw);
    }
    
    public NoDefaultConstructorException(String msg, Throwable thrw) {
        super(msg, thrw);
    }
}