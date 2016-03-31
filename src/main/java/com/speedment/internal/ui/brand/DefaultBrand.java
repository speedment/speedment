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
package com.speedment.internal.ui.brand;

import com.speedment.component.UserInterfaceComponent;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class DefaultBrand implements UserInterfaceComponent.Brand {
        
    private final String title, subtitle, version, website, logoSmall, logoLarge;

    public DefaultBrand(String title, String subtitle, String version, String website, String logoSmall, String logoLarge) {
        this.title     = requireNonNull(title);
        this.subtitle  = requireNonNull(subtitle);
        this.version   = requireNonNull(version);
        this.website   = requireNonNull(website);
        this.logoSmall = logoSmall; // Can be null.
        this.logoLarge = logoLarge; // Can be null.
    }

    @Override
    public final String title() {
        return title;
    }

    @Override
    public final String subtitle() {
        return subtitle;
    }

    @Override
    public final String version() {
        return version;
    }

    @Override
    public final String website() {
        return website;
    }

    @Override
    public final Optional<String> logoSmall() {
        return Optional.ofNullable(logoSmall);
    }

    @Override
    public final Optional<String> logoLarge() {
        return Optional.ofNullable(logoLarge);
    }
}