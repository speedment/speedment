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
package com.speedment.tool.internal.brand;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.component.UserInterfaceComponent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
public abstract class AbstractBrand implements Brand {
    
    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject InfoComponent infoComponent;
    
    @Override
    public void apply(Scene scene) {
        final Stage stage = scene.getWindow() == null 
            ? userInterfaceComponent.getStage() 
            : (Stage) scene.getWindow();

        stage.setTitle(infoComponent.title());
        logoSmall()
            .map(Image::new)
            .ifPresent(icon -> {
                stage.getIcons().add(icon);

                @SuppressWarnings("unchecked")
                final Stage dialogStage = (Stage) scene.getWindow();
                if (dialogStage != null) {
                    dialogStage.getIcons().add(icon);
                }
            });

        stylesheets().forEachOrdered(scene.getStylesheets()::add);
    }
}