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
package com.speedment.tool.internal.util;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.internal.controller.AboutController;
import com.speedment.tool.internal.controller.ComponentsController;
import com.speedment.tool.internal.controller.ConnectController;
import com.speedment.tool.internal.controller.MailPromptController;
import com.speedment.tool.internal.controller.MenubarController;
import com.speedment.tool.internal.controller.NotificationAreaController;
import com.speedment.tool.internal.controller.OutputController;
import com.speedment.tool.internal.controller.ProjectTreeController;
import com.speedment.tool.internal.controller.SceneController;
import com.speedment.tool.internal.controller.ToolbarController;
import com.speedment.tool.internal.controller.WorkspaceController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static com.speedment.common.injector.State.INITIALIZED;
import static java.util.Objects.requireNonNull;
import static javafx.stage.Modality.APPLICATION_MODAL;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
@InjectorKey(InjectionLoader.class)
public final class InjectionLoader {
    
    private final static String 
        FXML_PREFIX = "/fxml/",
        FXML_SUFFIX = ".fxml";
    
    private final Map<Class<?>, Supplier<? extends Initializable>> constructors;
    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject InfoComponent infoComponent;
    private @Inject Injector injector;
    private @Inject Brand brand;
    
    private InjectionLoader() {
        constructors = new ConcurrentHashMap<>();
    }
    
    @ExecuteBefore(INITIALIZED)
    void installConstructors() {
        constructors.put(AboutController.class,            AboutController::new);
        constructors.put(ComponentsController.class,       ComponentsController::new);
        constructors.put(ConnectController.class,          ConnectController::new);
        constructors.put(MailPromptController.class,       MailPromptController::new);
        constructors.put(MenubarController.class,          MenubarController::new);
        constructors.put(NotificationAreaController.class, NotificationAreaController::new);
        constructors.put(OutputController.class,           OutputController::new);
        constructors.put(ProjectTreeController.class,      ProjectTreeController::new);
        constructors.put(SceneController.class,            SceneController::new);
        constructors.put(ToolbarController.class,          ToolbarController::new);
        constructors.put(WorkspaceController.class,        WorkspaceController::new);
    }
    
    public FXMLLoader fxmlLoader() {
        final FXMLLoader loader = new FXMLLoader(StandardCharsets.UTF_8);
        
        loader.setControllerFactory(clazz -> {
            return MapStream.of(constructors)
                .filterKey(clazz::isAssignableFrom)
                .values()
                .findFirst()
                .map(Supplier::get)
                .map(injector::inject)
                .orElseThrow(() -> new SpeedmentException(
                    "Could not find any controller class matching '" + clazz + "'."
                ));
        });
        
        return loader;
    }
    
    public Node load(String name) {
        final FXMLLoader loader = fxmlLoader();
        final String filename = FXML_PREFIX + name + FXML_SUFFIX;
        loader.setLocation(InjectionLoader.class.getResource(filename));
        
        try {
            return loader.load();
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Error! Could not find FXML-file: " + filename + ".", ex
            );
        }
    }
    
    public Parent loadAndShow(String name) {
        final Parent parent = (Parent) load(name);
        final Stage stage = requireNonNull(userInterfaceComponent.getStage());
        final Scene scene = new Scene(parent);
        
        stage.hide();
        brand.apply(stage, scene);
        stage.setScene(scene);
        stage.show();
        
        return parent;
    }
    
    public Parent loadAsModal(String name) {
        final Stage mainStage = requireNonNull(userInterfaceComponent.getStage());
        final Stage dialog    = new Stage();
        final Parent root     = (Parent) load(name);
        final Scene scene     = new Scene(root);
        
        brand.apply(dialog, scene);
        
        dialog.setTitle("About " + infoComponent.title());
        dialog.initModality(APPLICATION_MODAL);
        brand.logoSmall().map(Image::new).ifPresent(dialog.getIcons()::add);
        dialog.initOwner(mainStage);
        dialog.setScene(scene);
        dialog.show();
        
        return root;
    }
}