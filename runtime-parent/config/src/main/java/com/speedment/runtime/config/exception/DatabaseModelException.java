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
package com.speedment.runtime.config.exception;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DatabaseModelException extends RuntimeException {

    private static final long serialVersionUID = 6178114258122472403L;

    public DatabaseModelException() {
    }

    public DatabaseModelException(String message) {
        super(message);
    }

    public DatabaseModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseModelException(Throwable cause) {
        super(cause);
    }

    public DatabaseModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}