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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.predicate.SpeedmentPredicate;

/**
 * A representation of an Entity field that is a {@code String} type. String
 * fields have additional methods that makes it easier to create string-related
 * predicates.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.2.0
 */
public interface HasStringOperators<ENTITY> {

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
    SpeedmentPredicate<ENTITY> equalIgnoreCase(String value);

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
    default SpeedmentPredicate<ENTITY> notEqualIgnoreCase(String value) {
        return equalIgnoreCase(value).negate();
    }

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
    SpeedmentPredicate<ENTITY> startsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not starts with</em> the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not starts with</em> the given value
     *
     * @see String#startsWith(java.lang.String)
     */
    default SpeedmentPredicate<ENTITY> notStartsWith(String value) {
        return startsWith(value).negate();
    }

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
    SpeedmentPredicate<ENTITY> endsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not ends with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not ends with</em> the given value
     *
     * @see String#endsWith(java.lang.String)
     */
    default SpeedmentPredicate<ENTITY> notEndsWith(String value) {
        return endsWith(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value
     *
     * @see String#contains(java.lang.CharSequence)
     */
    SpeedmentPredicate<ENTITY> contains(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not contains</em> the given value
     *
     * @see String#contains(java.lang.CharSequence)
     */
    default SpeedmentPredicate<ENTITY> notContains(String value) {
        return contains(value).negate();
    }

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
    SpeedmentPredicate<ENTITY> isEmpty();

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
    default SpeedmentPredicate<ENTITY> isNotEmpty() {
        return isEmpty().negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>starts with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>starts with</em> the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#startsWith(java.lang.String)
     */
    SpeedmentPredicate<ENTITY> startsWithIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not starts with</em> the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not starts with</em> the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#startsWith(java.lang.String)
     */
    default SpeedmentPredicate<ENTITY> notStartsWithIgnoreCase(String value) {
        return startsWithIgnoreCase(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>ends with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>ends with</em> the given value while ignoring the case of
     * the Strings that are compared
     *
     * @see String#endsWith(java.lang.String)
     */
    SpeedmentPredicate<ENTITY> endsWithIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not ends with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not ends with</em> the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#startsWith(java.lang.String)
     */
    default SpeedmentPredicate<ENTITY> notEndsWithIgnoreCase(String value) {
        return endsWithIgnoreCase(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given value
     * while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value while ignoring the case of
     * the Strings that are compared
     *
     * @see String#contains(java.lang.CharSequence)
     */
    SpeedmentPredicate<ENTITY> containsIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>does not contain</em> the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>does not contain</em> the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#contains(java.lang.CharSequence)
     */
    default SpeedmentPredicate<ENTITY> notContainsIgnoreCase(String value) {
        return containsIgnoreCase(value).negate();
    }

}
