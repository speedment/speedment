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
package com.speedment.tool.internal.controller;

import com.speedment.internal.common.injector.annotation.Inject;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.internal.util.InjectionLoader;
import com.speedment.tool.resource.SilkIcon;
import com.speedment.tool.resource.SpeedmentIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Emil Forslund
 */
public final class MenubarController implements Initializable {

    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject InjectionLoader loader;

    private @FXML MenuItem mbNew;
    private @FXML MenuItem mbOpen;
    private @FXML MenuItem mbSave;
    private @FXML MenuItem mbSaveAs;
    private @FXML MenuItem mbQuit;
    private @FXML MenuItem mbGenerate;
    private @FXML MenuItem mbProjectTree;
    private @FXML MenuItem mbWorkspace;
    private @FXML MenuItem mbOutput;
    private @FXML MenuItem mbGitter;
    private @FXML MenuItem mbGitHub;
    private @FXML MenuItem mbComponents;
    private @FXML MenuItem mbAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mbNew.setGraphic(SpeedmentIcon.NEW_PROJECT.view());
        mbOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT.view());
        mbSave.setGraphic(SpeedmentIcon.DISK.view());
        mbSaveAs.setGraphic(SpeedmentIcon.DISK_MULTIPLE.view());
        mbQuit.setGraphic(SpeedmentIcon.DOOR_IN.view());

        mbGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT.view());

        mbProjectTree.setGraphic(SpeedmentIcon.APPLICATION_SIDE_TREE.view());
        mbWorkspace.setGraphic(SpeedmentIcon.APPLICATION_FORM.view());
        mbOutput.setGraphic(SpeedmentIcon.APPLICATION_XP_TERMINAL.view());

        mbGitter.setGraphic(SpeedmentIcon.USER_COMMENT.view());
        mbGitHub.setGraphic(SpeedmentIcon.USER_COMMENT.view());
        mbComponents.setGraphic(SpeedmentIcon.BRICKS.view());
        mbAbout.setGraphic(SpeedmentIcon.INFORMATION.view());

        mbNew.setOnAction(ev -> userInterfaceComponent.newProject());
        mbOpen.setOnAction(ev -> userInterfaceComponent.openProject());
        mbSave.setOnAction(ev -> userInterfaceComponent.saveProject());
        mbSaveAs.setOnAction(ev -> userInterfaceComponent.saveProjectAs());
        mbQuit.setOnAction(ev -> userInterfaceComponent.quit());

        mbGenerate.setOnAction(ev -> userInterfaceComponent.generate());

        mbProjectTree.setOnAction(ev -> userInterfaceComponent.toggleProjectTree());
        mbWorkspace.setOnAction(ev -> userInterfaceComponent.toggleWorkspace());
        mbOutput.setOnAction(ev -> userInterfaceComponent.toggleOutput());

        mbGitter.setOnAction(ev -> userInterfaceComponent.showGitter());
        mbGitHub.setOnAction(ev -> userInterfaceComponent.showGithub());
        mbComponents.setOnAction(ev -> loader.loadAsModal("Components"));
        mbAbout.setOnAction(ev -> loader.loadAsModal("About"));
    }
}