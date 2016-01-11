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
package com.speedment.internal.ui.controller;

import com.speedment.internal.ui.resource.SilkIcon;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class MenubarController implements Initializable {
    
    private final UISession session;
    
    private @FXML MenuItem mbNew;
    private @FXML MenuItem mbOpen;
    private @FXML MenuItem mbSave;
    private @FXML MenuItem mbSaveAs;
    private @FXML MenuItem mbQuit;
    private @FXML MenuItem mbGenerate;
    private @FXML MenuItem mbGitHub;
    private @FXML MenuItem mbAbout;
    
    private MenubarController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        mbNew.setGraphic(SpeedmentIcon.NEW_PROJECT.view());
        mbOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT.view());
        mbSave.setGraphic(SilkIcon.DISK.view());
        mbSaveAs.setGraphic(SilkIcon.DISK_MULTIPLE.view());
        mbQuit.setGraphic(SilkIcon.DOOR_IN.view());
        mbGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT.view());
        mbGitHub.setGraphic(SilkIcon.USER_COMMENT.view());
        mbAbout.setGraphic(SilkIcon.INFORMATION.view());
        
        mbNew.setOnAction(session.newProject());
        mbOpen.setOnAction(session.openProject());
        mbSave.setOnAction(session.saveProject());
        mbSaveAs.setOnAction(session.saveProjectAs());
        mbQuit.setOnAction(session.quit());
        mbGenerate.setOnAction(session.generate());
        mbGitHub.setOnAction(session.showGithub());
        mbAbout.setOnAction(ev -> {
            AboutController.createAndShow(session);
        });
    }
    
    public static Node create(UISession session) {
        return Loader.create(session, "Menubar", MenubarController::new);
	}
}