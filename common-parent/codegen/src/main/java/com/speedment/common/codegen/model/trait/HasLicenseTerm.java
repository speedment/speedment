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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.LicenseTerm;

import java.util.Optional;

/**
 * A trait for models that contain {@link LicenseTerm} components.
 *
 * @param <T> the extending type
 *
 * @author Per Minborg
 * @since  3.1.19
 */
public interface HasLicenseTerm<T extends HasLicenseTerm<T>> {

    /**
     * Sets the {@link LicenseTerm} of this model.
     *
     * @param doc  the javadoc
     * @return     a reference to this
     */
    T set(final LicenseTerm doc);

    /**
     * Sets the {@link LicenseTerm} of this model. This method is a synonym for
     * {@link #set(LicenseTerm)}.
     *
     * @param text  the licenseterm
     * @return     a reference to this
     *
     * @since 3.1.18
     */
    default T licenseTerm(final String text) {
        return set(LicenseTerm.of(text));
    }

    /**
     * Returns the documentation of this model if such exists, else
     * <code>empty</code>.
     *
     * @return  the documentation or <code>empty</code>
     */
    Optional<LicenseTerm> getLicenseTerm();

}
