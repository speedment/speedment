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
package com.speedment.common.json;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A special form of exception that is thrown when parsing of a json file fails.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class JsonSyntaxException extends RuntimeException {

    private static final long serialVersionUID = -4342465797407497924L;
    
    private final long row;
    private final long col;
    
    public JsonSyntaxException() {
        this.row = -1;
        this.col = -1;
    }
    
    public JsonSyntaxException(String message) {
        super(message);
        this.row = -1;
        this.col = -1;
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col) {
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, Throwable cause) {
        super(cause);
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, String message) {
        super(message);
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, String message, Throwable cause) {
        super(message, cause);
        this.row = row.get();
        this.col = col.get();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " on row " + row + " col " + col + ".";
    }
}