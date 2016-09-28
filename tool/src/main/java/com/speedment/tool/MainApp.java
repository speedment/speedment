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
package com.speedment.tool;

import com.speedment.generator.internal.component.CodeGenerationComponentImpl;
import com.speedment.common.injector.Injector;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.internal.DefaultApplicationBuilder;
import com.speedment.runtime.internal.DefaultApplicationMetadata;
import com.speedment.runtime.internal.util.EmailUtil;
import com.speedment.tool.brand.Palette;
import com.speedment.tool.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.internal.util.InjectionLoader;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private final static Logger LOGGER = LoggerManager.getLogger(MainApp.class);
    private static Injector INJECTOR;
    
    public static void setInjector(Injector injector) {
        INJECTOR = requireNonNull(injector);
    }

    @Override
    public void start(Stage stage) throws Exception {
        requireNonNull(stage);
        
        if (INJECTOR == null) {
            LOGGER.warn("Creating new Speedment instance for UI session.");
            INJECTOR = new DefaultApplicationBuilder(DefaultApplicationMetadata.class)
                .withComponent(CodeGenerationComponentImpl.class)
                .withComponent(UserInterfaceComponentImpl.class)
                .build().getOrThrow(Injector.class);
        }
        
        final UserInterfaceComponentImpl ui = INJECTOR.getOrThrow(UserInterfaceComponentImpl.class);
        final ProjectComponent projects     = INJECTOR.getOrThrow(ProjectComponent.class);
        final InjectionLoader loader        = INJECTOR.getOrThrow(InjectionLoader.class);
        
        ui.start(this, stage);
        
        if (projects.getProject().dbmses().noneMatch(dbms -> true)) {
            if (EmailUtil.hasEmail()) {
                loader.loadAndShow("Connect");
            } else {
                loader.loadAndShow("MailPrompt");
            }
        } else {
            loader.loadAndShow("Scene");
            ui.showNotification(
                "Metadata has been loaded from an offline file. Click here to reload from database.",
                FontAwesomeIcon.REFRESH,
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