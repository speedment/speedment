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
package com.speedment.runtime.field.exception;

/**
 * A specialization of {@code RuntimeException} that is thrown when something
 * is wrong with the bindings between fields.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SpeedmentFieldException extends RuntimeException {

    private static final long serialVersionUID = 4372472947892374L;

    public SpeedmentFieldException() {
    }

    public SpeedmentFieldException(String message) {
        super(message);
    }

    public SpeedmentFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentFieldException(Throwable cause) {
        super(cause);
    }

    public SpeedmentFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}