/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.resource.SpeedmentIcon;
import com.speedment.tool.core.util.InjectionLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Emil Forslund
 */
public final class MenubarController implements Initializable {

    private @Inject UserInterfaceComponent ui;
    private @Inject InjectionLoader loader;

    private @FXML MenuItem mbNew;
    private @FXML MenuItem mbOpen;
    private @FXML MenuItem mbSave;
    private @FXML MenuItem mbSaveAs;
    private @FXML MenuItem mbQuit;
    private @FXML MenuItem mbGenerate;
    private @FXML CheckMenuItem mbProjectTree;
    private @FXML CheckMenuItem mbWorkspace;
    private @FXML CheckMenuItem mbOutput;
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

        mbNew.setOnAction(ev -> ui.newProject());
        mbOpen.setOnAction(ev -> ui.openProject());
        mbSave.setOnAction(ev -> ui.saveProject());
        mbSaveAs.setOnAction(ev -> ui.saveProjectAs());
        mbQuit.setOnAction(ev -> ui.quit());

        mbGenerate.setOnAction(ev -> ui.generate());

        mbProjectTree.selectedProperty().bindBidirectional(ui.projectTreeVisibleProperty());
        mbWorkspace.selectedProperty().bindBidirectional(ui.workspaceVisibleProperty());
        mbOutput.selectedProperty().bindBidirectional(ui.outputVisibleProperty());

        mbGitter.setOnAction(ev -> ui.showGitter());
        mbGitHub.setOnAction(ev -> ui.showGithub());
        mbComponents.setOnAction(ev -> loader.loadAsModal("Components"));
        mbAbout.setOnAction(ev -> loader.loadAsModal("About"));
    }
}