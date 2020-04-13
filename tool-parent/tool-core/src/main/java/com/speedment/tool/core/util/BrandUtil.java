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
package com.speedment.tool.core.util;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.component.UserInterfaceComponent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class BrandUtil {

    private BrandUtil() {}

    public static void applyBrand(Injector injector, Stage stage) {
        applyBrand(injector, stage, stage.getScene());
    }
    
    public static void applyBrand(Injector injector, Stage stage, Scene scene) {
        applyBrandToStage(injector, stage);
        
        final Brand brand        = injector.getOrThrow(Brand.class);
        final InfoComponent info = injector.getOrThrow(InfoComponent.class);
        
        apply(brand, info, stage, scene);
    }
    
    public static void applyBrandToStage(Injector injector, Stage stage) {
        final InfoComponent info = injector.getOrThrow(InfoComponent.class);
        final Brand brand = injector.getOrThrow(Brand.class);
        
        stage.setTitle(info.getTitle());
        brand.logoSmall()
            .map(Image::new)
            .ifPresent(stage.getIcons()::add);
    }
    
    public static void applyBrandToScene(Injector injector, Scene scene) {
        final Brand brand               = injector.getOrThrow(Brand.class);
        final UserInterfaceComponent ui = injector.getOrThrow(UserInterfaceComponent.class);
        final InfoComponent info        = injector.getOrThrow(InfoComponent.class);
        
        final Stage stage = scene.getWindow() == null 
            ? ui.getStage() 
            : (Stage) scene.getWindow();
        
        apply(brand, info, stage, scene);
    }
    
    private static void apply(Brand brand, InfoComponent info, Stage stage, Scene scene) {
        if (stage != null) {
            stage.setTitle(info.getTitle());
        }
        
        brand.logoSmall()
            .map(Image::new)
            .ifPresent(icon -> {
                if (stage != null) {
                    stage.getIcons().add(icon);
                }

                @SuppressWarnings("unchecked")
                final Stage dialogStage = (Stage) scene.getWindow();
                if (dialogStage != null) {
                    dialogStage.getIcons().add(icon);
                }
            });

        brand.stylesheets().forEachOrdered(scene.getStylesheets()::add);
    }

}