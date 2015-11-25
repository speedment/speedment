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
package com.speedment.internal.newgui;

import com.speedment.internal.gui.resource.SpeedmentIcon;
import com.speedment.internal.newgui.util.UILoader;
import com.speedment.internal.newgui.util.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class ToolbarController implements Initializable {
    
    private final UISession session;
    
    private @FXML Button buttonNew;
    private @FXML Button buttonOpen;
    private @FXML Button buttonGenerate;
    private @FXML ImageView logo;
    
    private ToolbarController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonNew.setOnAction(session.newProject());
        buttonOpen.setOnAction(session.openProject());
        buttonGenerate.setOnAction(session.generate());

        buttonNew.setGraphic(SpeedmentIcon.NEW_PROJECT_24.view());
        buttonOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT_24.view());
        buttonGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT_24.view());
        logo.setOnMousePressed(session.showGithub());
    }
    
    public static Node create(UISession session) {
        return UILoader.create(session, "Toolbar", ToolbarController::new);
	}
}