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
package com.speedment.generator.translator.exception;

/**
 * An specialized runtime exception thrown when something goes wrong during the
 * Speedment code generation process when a translator fails.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SpeedmentTranslatorException extends RuntimeException {

    private static final long serialVersionUID = 8374787683561187637L;

    public SpeedmentTranslatorException() {}

    public SpeedmentTranslatorException(String message) {
        super(message);
    }

    public SpeedmentTranslatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentTranslatorException(Throwable cause) {
        super(cause);
    }

    public SpeedmentTranslatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}