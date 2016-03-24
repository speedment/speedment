/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.ui.util;

import com.speedment.component.UserInterfaceComponent;
import com.speedment.internal.ui.UISession;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class BrandUtil {
    
    public static void applyBrand(UISession session) {
        applyBrand(session, session.getStage().getScene());
    }
    
    public static void applyBrand(UISession session, Scene scene) {
        final Stage stage = session.getStage();
        
        final UserInterfaceComponent.Brand brand = session
            .getSpeedment()
            .getUserInterfaceComponent()
            .getBrand();

        stage.setTitle(brand.title());
        brand.logoSmall()
            .map(Image::new)
            .ifPresent(stage.getIcons()::add);

        session.getSpeedment()
            .getUserInterfaceComponent()
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private BrandUtil() {
        instanceNotAllowed(getClass());
    }
}