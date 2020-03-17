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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.internal.component.InfoComponentImpl;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateInfoComponent implements InfoComponent {

    private final InfoComponent inner;

    public DelegateInfoComponent() {
        inner = new InfoComponentImpl();
    }

    @Override
    public String getVendor() {
        return inner.getVendor();
    }

    @Override
    public String getTitle() {
        return inner.getTitle();
    }

    @Override
    public String getSubtitle() {
        return inner.getSubtitle();
    }

    @Override
    public String getRepository() {
        return inner.getRepository();
    }

    @Override
    public String getImplementationVersion() {
        return inner.getImplementationVersion();
    }

    @Override
    public String getSpecificationVersion() {
        return inner.getSpecificationVersion();
    }

    @Override
    public String getSpecificationNickname() {
        return inner.getSpecificationNickname();
    }

    @Override
    public boolean isProductionMode() {
        return inner.isProductionMode();
    }

    @Override
    public String getLicenseName() {
        return inner.getLicenseName();
    }

    @Override
    public String getEditionAndVersionString() {
        return inner.getEditionAndVersionString();
    }

    @Override
    public String getBanner() {
        return inner.getBanner();
    }
}