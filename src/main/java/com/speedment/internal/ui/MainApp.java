/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui;

import com.speedment.Speedment;
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.ui.resource.SpeedmentFont;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.ui.controller.ConnectController;
import com.speedment.internal.ui.controller.MailPromptController;
import com.speedment.internal.util.Settings;
import com.speedment.internal.util.Statistics;
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
            LOGGER.info("Creating new Speedment instance for GUI session.");
            SPEEDMENT = SpeedmentFactory.newSpeedmentInstance();
        }
        
        final UISession session = new UISession(SPEEDMENT, this, stage);
        SpeedmentFont.loadAll();
        Statistics.onGuiStarted();
        
        if (Settings.inst().has(MailPromptController.MAIL_FIELD)) {
            ConnectController.createAndShow(session);
        } else {
            MailPromptController.createAndShow(session);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}