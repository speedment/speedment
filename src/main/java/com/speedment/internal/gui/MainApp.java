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
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.util.Settings;
import com.speedment.internal.gui.controller.MailPromptController;
import com.speedment.internal.gui.controller.ProjectPromptController;
import com.speedment.internal.gui.icon.SpeedmentIcon;
import com.speedment.internal.util.analytics.AnalyticsUtil;
import static com.speedment.internal.util.analytics.FocusPoint.GUI_STARTED;
import static java.util.Objects.requireNonNull;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private static MainApp app;

    private final Speedment speedment;

    public MainApp() {
        speedment = SpeedmentFactory.newSpeedmentInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        requireNonNull(stage);
        app = this;

        stage.getIcons().add(SpeedmentIcon.SPEEDMENT_LOGO.load());

        AnalyticsUtil.notify(GUI_STARTED);

        if (Settings.inst().has("user_mail")) {
            ProjectPromptController.showIn(speedment, stage);
        } else {
            MailPromptController.showIn(speedment, stage);
        }

        stage.show();

    }

    public static void showWebsite(String url) {
        requireNonNull(url);
        app.getHostServices().showDocument(url);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
