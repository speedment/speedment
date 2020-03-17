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
package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.ContentElement;

import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public final class ContentElementImpl implements ContentElement {

    private String value;
    private boolean escape;

    public ContentElementImpl(String value) {
        this.value = value;
        this.escape = true;
    }

    @Override
    public ContentElement setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean isEscape() {
        return escape;
    }

    @Override
    public ContentElement setEscape(boolean escape) {
        this.escape = escape;
        return this;
    }

}
