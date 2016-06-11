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

import com.speedment.common.injector.Injector;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.internal.runtime.DefaultApplicationBuilder;
import com.speedment.runtime.internal.util.EmailUtil;
import static com.speedment.tool.component.UserInterfaceComponent.ReuseStage.USE_EXISTING_STAGE;
import com.speedment.tool.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.internal.util.ConfigFileHelper;
import com.speedment.tool.internal.util.InjectionLoader;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import static java.util.Objects.requireNonNull;
import static javafx.application.Application.launch;

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
            INJECTOR = new DefaultApplicationBuilder()
                .with(CodeGenerationComponentImpl.class)
                .with(UserInterfaceComponentImpl.class)
                .build().getOrThrow(Injector.class);
        }
        
        final UserInterfaceComponentImpl ui = INJECTOR.get(UserInterfaceComponentImpl.class);
        final ConfigFileHelper config       = INJECTOR.get(ConfigFileHelper.class);
        final InjectionLoader loader        = INJECTOR.get(InjectionLoader.class);
        
        ui.start(this, stage);
        
        final Parameters parameters = getParameters();
        final List<String> params   = parameters.getRaw();
        if (params.isEmpty()) {
            if (EmailUtil.hasEmail()) {
                loader.loadAndShow("Connect");
            } else {
                loader.loadAndShow("MailPrompt");
            }
        } else {
            final String filename   = params.get(0).trim().replace("\\", "/");
            final File file         = new File(filename);
            config.loadConfigFile(file, USE_EXISTING_STAGE);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}