/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.internal.DefaultApplicationBuilder;
import com.speedment.runtime.core.internal.DefaultApplicationMetadata;
import com.speedment.runtime.core.internal.util.InternalEmailUtil;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.core.internal.util.InjectionLoader;
import com.speedment.tool.core.resource.FontAwesome;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.speedment.common.logger.Level.DEBUG;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private static final Logger LOGGER = LoggerManager.getLogger(MainApp.class);
    private static Injector INJECTOR;
    
    public static void setInjector(Injector injector) {
        INJECTOR = requireNonNull(injector);
    }

    @Override
    public void start(Stage stage) throws Exception {
        requireNonNull(stage);

        InjectorBuilder.logger().setLevel(DEBUG);
        
        if (INJECTOR == null) {
            LOGGER.warn("Creating new Speedment instance for UI session.");
            INJECTOR = new DefaultApplicationBuilder(
                    getClass().getClassLoader(),
                    DefaultApplicationMetadata.class
                )
                .withComponent(CodeGenerationComponentImpl.class)
                .withComponent(UserInterfaceComponentImpl.class)
                .build().getOrThrow(Injector.class);
        }
        
        final UserInterfaceComponentImpl ui = INJECTOR.getOrThrow(UserInterfaceComponentImpl.class);
        final ProjectComponent projects     = INJECTOR.getOrThrow(ProjectComponent.class);
        final InjectionLoader loader        = INJECTOR.getOrThrow(InjectionLoader.class);
        
        ui.start(this, stage);
        
        if (projects.getProject().dbmses().noneMatch(dbms -> true)) {
            if (InternalEmailUtil.hasEmail()) {
                loader.loadAndShow("Connect");
            } else {
                loader.loadAndShow("MailPrompt");
            }
        } else {
            loader.loadAndShow("Scene");
            ui.showNotification(
                "Metadata has been loaded from an offline file. Click here to reload from database.",
                FontAwesome.REFRESH,
                Palette.INFO,
                ui::reload
            );
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}