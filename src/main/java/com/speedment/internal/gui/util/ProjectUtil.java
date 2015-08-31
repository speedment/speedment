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
package com.speedment.internal.gui.util;

import com.speedment.Speedment;
import com.speedment.config.Project;
import com.speedment.internal.core.config.utils.GroovyParser;
import com.speedment.internal.util.Settings;
import com.speedment.internal.gui.controller.AlertController;
import com.speedment.internal.gui.controller.SceneController;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.io.File;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author pemi
 */
public final class ProjectUtil {
    
    private final static Logger LOGGER = LoggerManager.getLogger(ProjectUtil.class);
    
    private final static Predicate<File> OPEN_FILE_CONDITIONS = file ->
        file != null &&
        file.exists() && 
        file.isFile() && 
        file.canRead() && 
        file.getName().toLowerCase().endsWith(".groovy");
    
    private final static Predicate<File> OPEN_DIRECTORY_CONDITIONS = file ->
        file != null &&
        file.exists() && 
        file.isDirectory();

    public static EventHandler<ActionEvent> createOpenProjectHandler(Speedment speedment, Stage stage, BiConsumer<File, Project> biConsumer) {
        requireNonNulls(speedment, stage, biConsumer);
        return ev -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open .groovy File");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
            
            Optional.ofNullable(Settings.inst().get("project_location"))
                .map(File::new)
                .filter(OPEN_DIRECTORY_CONDITIONS)
                .ifPresent(fileChooser::setInitialDirectory);
            
            final File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                if (OPEN_FILE_CONDITIONS.test(file)) {
                    try {
                        final Project p = Project.newProject(speedment);
                        GroovyParser.fromGroovy(p, file.toPath());
                        biConsumer.accept(file, p);
                    } catch (Exception e) {
                        LOGGER.error(e);
                        showAlert(stage, e.getMessage());
                    }
                } else {
                    showAlert(stage, "Could not read .groovy file:\n" + file.toString());
                }
            }
        };
    }

    public static EventHandler<ActionEvent> createSaveProjectHandler(SceneController controller, Consumer<File> fileConsumer) {
        requireNonNulls(controller, fileConsumer);
        return ev -> {
            if (controller.getLastSaved() == null) {
                showSaveDialog(controller, fileConsumer);
            } else {
                saveGroovyFile(controller, controller.getLastSaved(), fileConsumer);
            }
        };
    }

    public static EventHandler<ActionEvent> createSaveAsProjectHandler(SceneController controller, Consumer<File> fileConsumer) {
        requireNonNulls(controller, fileConsumer);
        return ev -> showSaveDialog(controller, fileConsumer);
    }
    
    public static Optional<File> getDefaultLocation(File savedFile) {
        // savedFile nullable
        if (savedFile == null) {
            return Optional.ofNullable(Settings.inst().get("project_location"))
                .map(File::new)
                .filter(File::exists)
                .filter(File::isDirectory);
        } else {
            return Optional.of(savedFile.getParentFile());
        }
    }

    public static void showAlert(Stage stage, String message) {
        requireNonNulls(stage, message);
        AlertController.showAlert(stage, "Error!", message);
    }

    private static boolean showSaveDialog(SceneController controller, Consumer<File> fileConsumer) {
        requireNonNulls(controller, fileConsumer);
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Groovy File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
        getDefaultLocation(controller.getLastSaved())
            .ifPresent(fileChooser::setInitialDirectory);
        
        File file = fileChooser.showSaveDialog(controller.getStage());
        if (file != null) {
            if (!file.getName().endsWith(".groovy")) {
                file = new File(file.getAbsolutePath() + ".groovy");
            }
            
            return saveGroovyFile(controller, file, fileConsumer);
        }

        return false;
    }

    private static boolean saveGroovyFile(SceneController controller, File target, Consumer<File> fileConsumer) {
        requireNonNulls(controller, target, fileConsumer);
        final Path parent = target.toPath().getParent();

        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            final String groovy = GroovyParser.toGroovy(controller.getProject());
            Files.write(target.toPath(), groovy.getBytes(UTF_8));
            fileConsumer.accept(target);

            Settings.inst().set("project_location", parent.toFile().getAbsolutePath());
            controller.setLastSaved(target);
            
            return true;
        } catch (IOException ex) {
            showAlert(controller.getStage(), ex.getMessage());
        }
        
        return false;
    }

    private ProjectUtil() {instanceNotAllowed(getClass());}
}