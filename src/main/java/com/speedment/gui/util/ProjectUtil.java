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
package com.speedment.gui.util;

import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.impl.utils.GroovyParser;
import com.speedment.gui.controllers.AlertController;
import com.speedment.gui.controllers.SceneController;
import java.io.File;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author pemi
 */
public final class ProjectUtil {

    private ProjectUtil() {
    }

    public static EventHandler<ActionEvent> createOpenProjectHandler(Stage stage, BiConsumer<File, Project> biConsumer) {
        return ev -> {
            System.out.println("Load project");
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Groovy File");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                if (file.exists() && file.isFile() && file.canRead() && file.getName().toLowerCase().endsWith(".groovy")) {
                    try {
                        final Project p = Project.newProject();
                        GroovyParser.fromGroovy(p, file.toPath());
                        biConsumer.accept(file, p);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(stage, e.getMessage());
                    }
                } else {
                    showAlert(stage, "Could not read .groovy file:\n" + file.toString());
                }
            }
        };
    }

    public static EventHandler<ActionEvent> createSaveProjectHandler(SceneController controller, Consumer<File> fileConsumer) {
        return ev -> {
            if (controller.getLastSaved() == null) {
                showSaveDialog(controller, fileConsumer);
            } else {
                saveGroovyFile(controller, controller.getLastSaved(), fileConsumer);
            }
        };
    }

    public static EventHandler<ActionEvent> createSaveAsProjectHandler(SceneController controller, Consumer<File> fileConsumer) {
        return ev -> showSaveDialog(controller, fileConsumer);
    }

    private static boolean showSaveDialog(SceneController controller, Consumer<File> fileConsumer) {
        System.out.println("Save project");
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Groovy File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
        File file = fileChooser.showSaveDialog(controller.getStage());
        System.out.println("fileChooser: " + file);

        if (file != null) {
            saveGroovyFile(controller, file, fileConsumer);
            return true;
        }

        return false;
    }

    private static void saveGroovyFile(SceneController controller, File target, Consumer<File> fileConsumer) {
        final Path parent = target.toPath().getParent();

        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            final String groovy = GroovyParser.toGroovy(controller.getProject());
            Files.write(target.toPath(), groovy.getBytes(UTF_8));
            fileConsumer.accept(target);

        } catch (IOException ex) {
            showAlert(controller.getStage(), ex.getMessage());
        }
    }

    public static void showAlert(Stage stage, String message) {
//        final Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
//        alert.showAndWait();
        AlertController.showAlert(stage, "Error!", message);
    }
}
