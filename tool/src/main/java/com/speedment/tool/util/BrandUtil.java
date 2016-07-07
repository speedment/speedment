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
package com.speedment.tool.util;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.component.UserInterfaceComponent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version="3.0")
public final class BrandUtil {
    
    public static void applyBrand(Injector injector, Stage stage) {
        applyBrand(injector, stage, stage.getScene());
    }
    
    public static void applyBrand(Injector injector, Stage stage, Scene scene) {
        applyBrandToStage(injector, stage);
        
        final Brand brand = injector.getOrThrow(Brand.class);
        brand.apply(stage, scene);
    }
    
    public static void applyBrandToStage(Injector injector, Stage stage) {
        final InfoComponent info = injector.getOrThrow(InfoComponent.class);
        final Brand brand = injector.getOrThrow(Brand.class);
        
        stage.setTitle(info.title());
        brand.logoSmall()
            .map(Image::new)
            .ifPresent(stage.getIcons()::add);
    }
    
    public static void applyBrandToScene(Injector injector, Scene scene) {
        final Brand brand = injector.getOrThrow(Brand.class);
        final UserInterfaceComponent ui = injector.getOrThrow(UserInterfaceComponent.class);
        
        final Stage stage = scene.getWindow() == null 
            ? ui.getStage() 
            : (Stage) scene.getWindow();
        
        brand.apply(stage, scene);
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private BrandUtil() {
        instanceNotAllowed(getClass());
    }
}