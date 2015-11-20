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

import com.speedment.Speedment;
import com.speedment.exception.SpeedmentException;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private final Speedment speedment;
    
    private @FXML StackPane stack;
    private @FXML BorderPane borders;
    private @FXML VBox top;
    private @FXML SplitPane horizontal;
    private @FXML SplitPane vertical;

    public SceneController(Speedment speedment) {
        this.speedment = speedment;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runLater(() -> {
            top.getChildren().add(MenubarController.create(speedment));
            top.getChildren().add(ToolbarController.create(speedment));
            horizontal.getItems().add(0, ProjectTreeController.create(speedment));
            vertical.getItems().add(WorkspaceController.create(speedment));
            vertical.getItems().add(OutputController.create(speedment));
            
            horizontal.setDividerPositions(0.2, 0.8);
            vertical.setDividerPositions(0.7, 0.3);
        });
    }
    
    public static void createAndShow(Stage stage, Speedment speedment) {
        requireNonNull(speedment);
		final FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/newgui/Scene.fxml"));
		final SceneController control = new SceneController(speedment);
        loader.setController(control);

        try {
            final Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            final StackPane root = (StackPane) loader.load();
            final Scene scene    = new Scene(root);
            
            stage.hide();
            stage.setTitle("Speedment");
            stage.setMaximized(screen.getWidth() <= 1920);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new SpeedmentException(ex);
        }
	}
}