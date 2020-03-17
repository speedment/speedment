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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.LicenseTerm;
import com.speedment.common.codegen.model.trait.HasLicenseTerm;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link LicenseTerm} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link LicenseTerm#of()} method to get an instance. In that way, you can later
 * change the implementing class without modifying the using code.
 * 
 * @author Per Minborg
 * @see    LicenseTerm
 */
public final class LicenseTermImpl implements LicenseTerm {

    private HasLicenseTerm<?> parent;
    private String text;

    /**
     * Initializes this licenseterm block.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link LicenseTerm#of()} method!
     */
    public LicenseTermImpl() {
        this("");
    }

    /**
     * Initializes this licenseterm block using a text. The text may have multiple
     * lines separated by new-line characters.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link LicenseTerm#of(String)} method!
     *
     * @param text  the text
     */
    public LicenseTermImpl(final String text) {
        this.text    = requireNonNull(text);
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected LicenseTermImpl(final LicenseTerm prototype) {
        text    = requireNonNull(prototype).getText();
    }

    @Override
    public LicenseTerm setParent(HasLicenseTerm<?> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Optional<HasLicenseTerm<?>> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public LicenseTerm setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public LicenseTermImpl copy() {
        return new LicenseTermImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + HashUtil.identityHashForParent(this);
        hash = 29 * hash + Objects.hashCode(this.text);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LicenseTermImpl other = (LicenseTermImpl) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        return Objects.equals(this.text, other.text);
    }


}
