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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.internal.util.Statistics;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.internal.util.InjectionLoader;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private @Inject UserInterfaceComponent ui;
    private @Inject InjectionLoader loader;
    
    private @FXML VBox top;
    private @FXML SplitPane horizontal;
    private @FXML SplitPane vertical;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runLater(() -> {
            top.getChildren().add(loader.load("Menubar"));
            top.getChildren().add(loader.load("Toolbar"));
            horizontal.getItems().add(0, loader.load("ProjectTree"));
            vertical.getItems().add(loader.load("Workspace"));
            vertical.getItems().add(loader.load("Output"));
            
            horizontal.setDividerPositions(0.2, 0.7);
            vertical.setDividerPositions(0.7, 0.3);
            
            ui.toggleOutput();
            
            Statistics.onGuiProjectLoaded();
        });
    }
}