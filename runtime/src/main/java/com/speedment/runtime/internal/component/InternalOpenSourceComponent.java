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
package com.speedment.runtime.internal.component;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentVersion;
import com.speedment.runtime.internal.license.AbstractSoftware;
import com.speedment.runtime.internal.license.OpenSourceLicense;
import com.speedment.runtime.license.License;
import com.speedment.runtime.license.Software;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public abstract class InternalOpenSourceComponent extends AbstractComponent {

    public InternalOpenSourceComponent(Speedment speedment) {
        super(speedment);
    }
    
    @Override
    public final boolean isInternal() {
        return true;
    }
    
    @Override
    public final Software asSoftware() {
        return info;
    }
    
    protected String getTitle() {
        return getComponentClass().getSimpleName();
    }
    
    protected abstract String getDescription();
    
    protected String getVersion() {
        return SpeedmentVersion.getImplementationVersion();
    }
    
    protected License getLicense() {
        return OpenSourceLicense.APACHE_2;
    }
    
    protected Stream<Software> getDependencies() {
        return Stream.empty();
    }
    
    private final transient Software info = AbstractSoftware.with(
        getTitle(),
        getVersion(),
        getDescription(),
        getLicense(),
        isInternal(),
        getDependencies().toArray(Software[]::new)
    );
}