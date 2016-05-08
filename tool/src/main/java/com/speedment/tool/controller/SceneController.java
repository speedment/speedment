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

import com.speedment.tool.brand.Brand;
import com.speedment.tool.UISession;
import com.speedment.tool.util.Loader;
import com.speedment.runtime.internal.util.Statistics;
import com.speedment.tool.event.UIEvent;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private final UISession session;
    
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
            vertical.getItems().add(WorkspaceController.create(session));
            vertical.getItems().add(OutputController.create(session));
            
            horizontal.setDividerPositions(0.2, 0.7);
            vertical.setDividerPositions(0.7, 0.3);
            
            session.toggleOutput();
            
            Statistics.onGuiProjectLoaded();
        });
    }
    
    public static void createAndShow(UISession session) {
        final Parent root           = Loader.create(session, "Scene");
        final Scene scene           = new Scene(root);
        final Stage stage           = session.getStage();
        final Rectangle2D screen    = Screen.getPrimary().getVisualBounds();
        final boolean screenIsSmall = screen.getWidth() <= 1600; // TODO Save maximized setting.
        
        stage.hide();
        Brand.apply(session, scene);
        stage.setMaximized(screenIsSmall);
        stage.setScene(scene);
        stage.show();
        
        session.getSpeedment().getEventComponent().notify(UIEvent.OPEN_MAIN_WINDOW);
	}
}