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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.internal.model.LicenseTermImpl;
import com.speedment.common.codegen.model.trait.HasCall;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.trait.HasLicenseTerm;
import com.speedment.common.codegen.model.trait.HasParent;

/**
 * A model that represents a block of licenseterm in code.
 * 
 * @author  Per Minborg
 * @since   3.1.18
 */
public interface LicenseTerm
extends HasParent<HasLicenseTerm<?>, LicenseTerm>,
        HasCopy<LicenseTerm>,
        HasCall<LicenseTerm> {
    
    /**
     * Sets the body text shown in the licenseterm.
     * 
     * @param text  the text
     * @return      a reference to this model
     */
    LicenseTerm setText(String text);

    /**
     * Returns the body text shown in the licenseterm.
     * 
     * @return  the body documentation text
     */
    String getText();

    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.

     * @return  the new instance
     */
    static LicenseTerm of() {
        return new LicenseTermImpl();
    }
    
    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     * 
     * @param text  the documentation
     * @return      the new instance
     */
    static LicenseTerm of(String text) {
        return of().setText(text);
    }
}