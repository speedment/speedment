/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
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