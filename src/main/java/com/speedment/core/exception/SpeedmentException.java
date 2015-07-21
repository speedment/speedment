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
package com.speedment.core.exception;

import com.speedment.core.annotations.Api;

/**
 * {@inheritDoc}
 * 
 * Generic RuntimeException for Speedment. 
 * 
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.0")
public class SpeedmentException extends RuntimeException {

    static final long serialVersionUID = -623523923713561356L;

    public SpeedmentException() {
        super();
    }

    public SpeedmentException(String message) {
        super(message);
    }

    public SpeedmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentException(Throwable cause) {
        super(cause);
    }

    protected SpeedmentException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
