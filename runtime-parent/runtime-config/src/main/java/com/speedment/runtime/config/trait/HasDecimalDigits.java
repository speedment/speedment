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
 * A trait for documents that contains decimal digits information. The decimal 
 * digits is the total number of decimals that this document can handle.
 * <p>
 * Example: The value {@code 10 000.00} has 2 decimal digits.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface HasDecimalDigits extends Document {

    /**
     * Gets the decimal digits of this {@link Document}. If no decimal digits 
     * value is present, this method will return an empty {@code OptionalInt}. 
     * This should be interpreted as no decisions can be made regarding the 
     * precision of the data.
     *
     * @return the decimal digits of this {@link Document}
     */
    default OptionalInt getDecimalDigits() {
        return getAsInt(HasDecimalDigitsUtil.DECIMAL_DIGITS);
    }
}