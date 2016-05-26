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

import com.speedment.runtime.Speedment;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.internal.runtime.DefaultApplicationBuilder;
import static com.speedment.tool.UISession.ReuseStage.USE_EXISTING_STAGE;
import com.speedment.tool.internal.controller.ConnectController;
import com.speedment.tool.internal.controller.MailPromptController;
import com.speedment.runtime.internal.util.EmailUtil;
import com.speedment.runtime.internal.util.Statistics;
import com.speedment.tool.event.UIEvent;
import java.io.File;
import java.util.List;
import static java.util.Objects.requireNonNull;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private final static Logger LOGGER = LoggerManager.getLogger(MainApp.class);
    private static Speedment SPEEDMENT;
    
    public static void setSpeedment(Speedment speedment) {
        SPEEDMENT = requireNonNull(speedment);
    }

    @Override
    public void start(Stage stage) throws Exception {
        requireNonNull(stage);
        
        if (SPEEDMENT == null) {
            LOGGER.warn("Creating new Speedment instance for UI session.");
            SPEEDMENT = new DefaultApplicationBuilder().build();
        }
        
        final Parameters parameters = getParameters();
        final List<String> params   = parameters.getRaw();
        if (params.isEmpty()) {
            final UISession session = createSession(stage, UISession.DEFAULT_CONFIG_LOCATION);
            
            if (EmailUtil.hasEmail()) {
                ConnectController.createAndShow(session);
            } else {
                MailPromptController.createAndShow(session);
            }
        } else {
            final String filename   = params.get(0).trim().replace("\\", "/");
            final UISession session = createSession(stage, filename);
            final File file         = new File(filename);
            session.loadConfigFile(file, USE_EXISTING_STAGE);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private UISession createSession(Stage stage, String configLocation) {
        final UISession session = new UISession(SPEEDMENT, this, stage, configLocation);
        Statistics.onGuiStarted();
        SPEEDMENT.getEventComponent().notify(UIEvent.STARTED);
        return session;
    }
}