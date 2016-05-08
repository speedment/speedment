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
package com.speedment.tool.controller;

import com.speedment.tool.UISession;
import com.speedment.tool.resource.SilkIcon;
import com.speedment.tool.resource.SpeedmentIcon;
import com.speedment.tool.util.Loader;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

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
    private @FXML MenuItem mbProjectTree;
    private @FXML MenuItem mbWorkspace;
    private @FXML MenuItem mbOutput;
//    private @FXML MenuItem mbPreview;
    private @FXML MenuItem mbGitter;
    private @FXML MenuItem mbGitHub;
    private @FXML MenuItem mbComponents;
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

        mbProjectTree.setGraphic(SilkIcon.APPLICATION_SIDE_TREE.view());
        mbWorkspace.setGraphic(SilkIcon.APPLICATION_FORM.view());
        mbOutput.setGraphic(SilkIcon.APPLICATION_XP_TERMINAL.view());
//        mbPreview.setGraphic(SilkIcon.PAGE_WHITE_CUP.view());

        mbGitter.setGraphic(SilkIcon.USER_COMMENT.view());
        mbGitHub.setGraphic(SilkIcon.USER_COMMENT.view());
        mbComponents.setGraphic(SilkIcon.BRICKS.view());
        mbAbout.setGraphic(SilkIcon.INFORMATION.view());

        mbNew.setOnAction(session.newProject());
        mbOpen.setOnAction(session.openProject());
        mbSave.setOnAction(session.saveProject());
        mbSaveAs.setOnAction(session.saveProjectAs());
        mbQuit.setOnAction(session.quit());

        mbGenerate.setOnAction(session.generate());

        mbProjectTree.setOnAction(session.toggleProjectTree());
        mbWorkspace.setOnAction(session.toggleWorkspace());
        mbOutput.setOnAction(session.toggleOutput());
//        mbPreview.setOnAction(session.togglePreview());

        mbGitter.setOnAction(session.showGitter());
        mbGitHub.setOnAction(session.showGithub());
        mbComponents.setOnAction(ev -> ComponentsController.createAndShow(session));
        mbAbout.setOnAction(ev -> AboutController.createAndShow(session));
    }

    public static Node create(UISession session) {
        return Loader.create(session, "Menubar");
    }
}
