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

import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private final UISession session;
    
    private @FXML StackPane stack;
    private @FXML VBox top;
    private @FXML SplitPane horizontal;
    private @FXML SplitPane vertical;

    private SceneController(UISession session) {
        this.session = requireNonNull(session);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runLater(() -> {
            top.getChildren().add(MenubarController.create(session));
            top.getChildren().add(ToolbarController.create(session));
            horizontal.getItems().add(0, ProjectTreeController.create(session));
//            horizontal.getItems().add(2, PreviewController.create(session));
            vertical.getItems().add(WorkspaceController.create(session));
            vertical.getItems().add(OutputController.create(session));
            
            horizontal.setDividerPositions(0.2, 0.7);
            vertical.setDividerPositions(0.7, 0.3);
        });
    }
    
    public static void createAndShow(UISession session) {
        final Parent root           = Loader.create(session, "Scene", SceneController::new);
        final Scene scene           = new Scene(root);
        final Stage stage           = session.getStage();
        final Rectangle2D screen    = Screen.getPrimary().getVisualBounds();
        final boolean screenIsSmall = screen.getWidth() <= 1920;

        stage.hide();
        stage.setTitle("Speedment");
        stage.setMaximized(screenIsSmall);
        stage.getIcons().add(SpeedmentIcon.SPIRE.load());
        stage.setScene(scene);
        stage.show();
        
        scene.getStylesheets().add(session.getSpeedment().getUserInterfaceComponent().getStylesheetFile());
	}
}