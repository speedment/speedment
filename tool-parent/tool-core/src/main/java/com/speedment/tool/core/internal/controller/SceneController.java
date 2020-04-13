/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.runtime.core.util.Statistics.Event.GUI_PROJECT_LOADED;
import static javafx.application.Platform.runLater;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.util.Statistics;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.component.VersionComponent;
import com.speedment.tool.core.internal.util.SemanticVersionComparator;
import com.speedment.tool.core.util.InjectionLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private static final Logger LOGGER = LoggerManager.getLogger(SceneController.class);
    private static final SemanticVersionComparator SEMANTIC_VERSION =
        new SemanticVersionComparator();
    
    public @Inject UserInterfaceComponent ui;
    public @Inject InfoComponent info;
    public @Inject InjectionLoader loader;
    public @Inject VersionComponent version;
    public @Inject ProjectComponent projects;

    private @FXML VBox top;
    private @FXML SplitPane horizontal;
    private @FXML SplitPane vertical;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        top.getChildren().add(loader.load("Menubar"));
        top.getChildren().add(loader.load("Toolbar"));

        final Node projectTree = loader.load("ProjectTree");
        final Node workspace   = loader.load("Workspace");
        final Node output      = loader.load("Output");

        horizontal.getItems().add(0, projectTree);
        vertical.getItems().add(workspace);
        vertical.getItems().add(output);

        ui.prepareProjectTree(horizontal, projectTree);
        ui.prepareWorkspace(vertical, workspace);
        ui.prepareOutput(vertical, output);

        horizontal.setDividerPositions(0.3, 0.7);
        vertical.setDividerPositions(0.7, 0.3);

        Statistics.report(info, projects, GUI_PROJECT_LOADED);

        CompletableFuture.runAsync(() -> {
            try {
                version.latestVersion()
                    .thenAcceptAsync(release -> runLater(() -> {
                        final int compare = SEMANTIC_VERSION.compare(
                            release, info.getImplementationVersion()
                        );

                        if (compare == 0) {
                            ui.showNotification(
                                "Your version of " + info.getTitle() + " is up to date."
                            );
                        } else if (compare > 0) {
                            ui.showNotification(
                                "A new version " + release +
                                " of " + info.getTitle() + " is available."
                            );
                        } else {
                            ui.showNotification(
                                "Your version " + info.getImplementationVersion() +
                                " of " + info.getTitle() + " is newer than the released " +
                                release + "."
                            );
                        }
                    })).get(3, TimeUnit.SECONDS);
            } catch (final ExecutionException ex) {
                LOGGER.debug(ex, "Error loading last released version.");
            } catch (final InterruptedException ex) {
                LOGGER.debug(ex, "Error loading last released version.");
                Thread.currentThread().interrupt();
            } catch (final TimeoutException ex) {
                LOGGER.debug(ex, "Request for latest released version timed out.");
            }
        });
    }
}
