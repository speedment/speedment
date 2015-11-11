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
package com.speedment.internal.gui;

import com.speedment.Speedment;
import com.speedment.config.Project;
import com.speedment.internal.core.config.utils.GroovyParser;
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.util.Settings;
import com.speedment.internal.gui.controller.MailPromptController;
import com.speedment.internal.gui.controller.ProjectPromptController;
import com.speedment.internal.gui.controller.SceneController;
import com.speedment.internal.gui.icon.SpeedmentIcon;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.Statistics;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private final static Logger LOGGER = LoggerManager.getLogger(MainApp.class);
    private static Speedment SPEEDMENT;
    private static MainApp APP;
    
    public static void setSpeedment(Speedment speedment) {
        MainApp.SPEEDMENT = requireNonNull(speedment);
    }

    @Override
    public void start(Stage stage) throws Exception {
        requireNonNull(stage);
        
        if (SPEEDMENT == null) {
            LOGGER.info("Creating new Speedment instance for GUI session.");
            SPEEDMENT = SpeedmentFactory.newSpeedmentInstance();
        }
        
        APP = this;

        //stage.getIcons().add(SpeedmentIcon.SPEEDMENT_LOGO.load());
        stage.getIcons().add(SpeedmentIcon.SPIRE.load());

        Statistics.onGuiStarted();

        final Parameters parameters = getParameters();
        final List<String> params = parameters.getRaw();
        if (params.isEmpty()) {
            if (Settings.inst().has("user_mail")) {
                ProjectPromptController.showIn(SPEEDMENT, stage);
            } else {
                MailPromptController.showIn(SPEEDMENT, stage);
            }
        } else {
            final Project project = Project.newProject(SPEEDMENT);
            final String filename = params.get(0).trim().replace("\\", "/");
            final File file = new File(filename);
            GroovyParser.fromGroovy(project, file.toPath());
            SceneController.showIn(SPEEDMENT, stage, project, file);
        }

        stage.show();

    }

    public static void showWebsite(String url) {
        requireNonNull(url);
        APP.getHostServices().showDocument(url);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
