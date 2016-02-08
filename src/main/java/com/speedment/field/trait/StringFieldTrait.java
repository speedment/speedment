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
package com.speedment.field.trait;

import com.speedment.annotation.Api;
import com.speedment.field.predicate.StringSpeedmentPredicate;

/**
 *
 * @author pemi, Emil Forslund
 * @param <ENTITY> the entity type
 */
@Api(version = "2.2")
public interface StringFieldTrait<ENTITY> {

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(java.lang.String)
     */
    StringSpeedmentPredicate<ENTITY> equalIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(java.lang.String)
     */
    StringSpeedmentPredicate<ENTITY> notEqualIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>starts with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>starts with</em> the given value
     *
     * @see String#startsWith(java.lang.String)
     */
    StringSpeedmentPredicate<ENTITY> startsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>ends with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>ends with</em> the given value
     *
     * @see String#endsWith(java.lang.String)
     */
    StringSpeedmentPredicate<ENTITY> endsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value
     *
     * @see String#endsWith(java.lang.String)
     */
    StringSpeedmentPredicate<ENTITY> contains(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>is empty</em>. An empty Field
     * contains a String with length zero.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>is empty</em>
     *
     * @see String#isEmpty()
     */
    StringSpeedmentPredicate<ENTITY> isEmpty();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>is not empty</em>. An empty
     * Field contains a String with length zero.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>is not empty</em>
     *
     * @see String#isEmpty()
     */
    StringSpeedmentPredicate<ENTITY> isNotEmpty();
}
