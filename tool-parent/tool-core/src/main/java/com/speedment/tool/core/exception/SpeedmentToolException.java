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
package com.speedment.tool.core.exception;

/**
 * A specialized {@code RuntimeException} used in the Speedment Tool.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SpeedmentToolException extends RuntimeException {

    private static final long serialVersionUID = 7199605290032039965L;

    public SpeedmentToolException() {}

    public SpeedmentToolException(String message) {
        super(message);
    }

    public SpeedmentToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentToolException(Throwable cause) {
        super(cause);
    }

    public SpeedmentToolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}