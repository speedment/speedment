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
package com.speedment.maven;

import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.Component;
import java.io.File;
import java.util.function.Supplier;
import org.apache.maven.plugin.logging.Log;
import com.speedment.runtime.component.ComponentConstructor;
import com.speedment.runtime.internal.runtime.DefaultSpeedmentApplicationLifecycle;
import com.speedment.tool.component.DocumentPropertyComponentImpl;
import com.speedment.tool.component.UserInterfaceComponentImpl;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
final class SpeedmentInitializer {
    
    private final Log log;
    private final File configFile;
    private final Supplier<ComponentConstructor<? extends Component>[]> componentBuilders;
    
    public SpeedmentInitializer(Log log, File configFile, Supplier<ComponentConstructor<?>[]> componentBuilders) {
        this.log               = requireNonNull(log);
        this.configFile        = configFile; // Can be null.
        this.componentBuilders = requireNonNull(componentBuilders);
    }
    
    public Speedment build() {
        final DefaultSpeedmentApplicationLifecycle lifecycle = new DefaultSpeedmentApplicationLifecycle(configFile)
            .with(CodeGenerationComponentImpl::new)
            .with(UserInterfaceComponentImpl::new)
            .with(DocumentPropertyComponentImpl::new);
        
        final ComponentConstructor<? extends Component>[] constructors = componentBuilders.get();
        
        if (constructors != null) {
            for (final ComponentConstructor<? extends Component> constructor : constructors) {
                if (constructor != null) {
                    lifecycle.with(constructor);
                } else {
                    log.warn("Specified ComponentConstructor is null.");
                }
            }
        } else {
            log.info("Component constructors container is not defined.");
        }

        return lifecycle.build();
    }
}