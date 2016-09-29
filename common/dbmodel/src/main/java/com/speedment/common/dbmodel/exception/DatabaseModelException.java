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
package com.speedment.common.dbmodel.exception;

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