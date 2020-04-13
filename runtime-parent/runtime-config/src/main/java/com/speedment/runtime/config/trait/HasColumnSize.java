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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import java.util.OptionalInt;

/**
 * A trait for documents that contains column size information. The column size 
 * is the total number of characters or digits that this document can handle.
 * <p>
 * Example: The value {@code "abc de  "} has a column size of 8 and 
 * {@code 10 000.00} has a column size of 7.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface HasColumnSize extends Document {

    /**
     * Gets the column size of this {@link Document}. If no size is present, 
     * this method will return an empty {@code OptionalInt}. This should be 
     * interpreted as no decisions can be made regarding the size of the data.
     *
     * @return the column size of this {@link Document}
     */
    default OptionalInt getColumnSize() {
        return getAsInt(HasColumnSizeUtil.COLUMN_SIZE);
    }
}