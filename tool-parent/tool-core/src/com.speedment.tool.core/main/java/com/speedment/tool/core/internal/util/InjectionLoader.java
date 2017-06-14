/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.util;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.exception.SpeedmentToolException;
import com.speedment.tool.core.internal.controller.*;
import com.speedment.tool.core.util.BrandUtil;
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
@InjectKey(InjectionLoader.class)
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
        constructors.put(ProjectProblemController.class,   ProjectProblemController::new);
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
                .orElseThrow(() -> new SpeedmentToolException(
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
            throw new SpeedmentToolException(
                "Error! Could not find FXML-file: " + filename + ".", ex
            );
        }
    }
    
    public Parent loadAndShow(String name) {
        final Parent parent = (Parent) load(name);
        final Stage stage = requireNonNull(userInterfaceComponent.getStage());
        final Scene scene = new Scene(parent);
        
        stage.hide();
        BrandUtil.applyBrand(injector, stage, scene);
        stage.setScene(scene);
        WindowSettingUtil.applySaveOnCloseMethod(stage, name);
        WindowSettingUtil.applyStoredDisplaySettings(stage, name);
        stage.show();
        
        return parent;
    }
    
    public Parent loadAsModal(String name) {
        final Stage mainStage = requireNonNull(userInterfaceComponent.getStage());
        final Stage dialog    = new Stage();
        final Parent root     = (Parent) load(name);
        final Scene scene     = new Scene(root);
        
        BrandUtil.applyBrandToScene(injector, scene);
        
        dialog.setTitle("About " + infoComponent.getTitle());
        dialog.initModality(APPLICATION_MODAL);
        brand.logoSmall().map(Image::new).ifPresent(dialog.getIcons()::add);
        dialog.initOwner(mainStage);
        dialog.setScene(scene);
        dialog.show();
        
        return root;
    }
}
