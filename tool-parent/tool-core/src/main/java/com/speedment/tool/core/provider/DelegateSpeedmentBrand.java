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
package com.speedment.tool.core.provider;

import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.internal.brand.SpeedmentBrand;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A default implementation of the {@link Brand} interface.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DelegateSpeedmentBrand implements Brand {

    private final Brand inner;

    public DelegateSpeedmentBrand() {
        inner = new SpeedmentBrand();
    }

    @Override
    public String website() {
        return inner.website();
    }

    @Override
    public Optional<String> logoSmall() {
        return inner.logoSmall();
    }

    @Override
    public Optional<String> logoLarge() {
        return inner.logoLarge();
    }

    @Override
    public Stream<String> stylesheets() {
        return inner.stylesheets();
    }
}