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
package com.speedment.tool.core.internal.util;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import javafx.stage.Stage;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Utility class for method related to window settings, such as size, position 
 * and whether the window was maximized or not.
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
final class WindowSettingUtil {

    private static final Logger LOGGER = LoggerManager.getLogger(WindowSettingUtil.class);
    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(WindowSettingUtil.class);

    private static final String WINDOW_WIDTH = "windowWidth";
    private static final String WINDOW_HEIGHT = "windowHeight";
    private static final String WINDOW_X_POS = "windowXPos";
    private static final String WINDOW_Y_POS = "windowYPos";
    private static final String WINDOW_MAXIMIZED = "windowMaximized";


    private static final double DEFUALT_WIDTH  = 1280;
    private static final double DEFUALT_HEIHGT = 720;
    private static final double DEFUALT_X = 0;
    private static final double DEFUALT_Y = 0;

    private WindowSettingUtil() {}

    /**
     * Retrieves data about he window settings from the previous session and applies 
     * them to the stage. These settings include window size, position and if
     * it was maximized or not.
     * 
     * @param stage  the stage to apply these settings to
     * @param name   the name under which we stored the settings
     */
    static void applyStoredDisplaySettings(Stage stage, String name){
        try {
            if( PREFERENCES.nodeExists(name) ){
                Preferences stagePreferences = PREFERENCES.node(name);
                boolean wasMaximized = stagePreferences.getBoolean(WINDOW_MAXIMIZED, false);
                if( wasMaximized ){
                    stage.setMaximized(true);
                } else {
                    stage.setX( stagePreferences.getDouble(WINDOW_X_POS, DEFUALT_X));
                    stage.setY( stagePreferences.getDouble(WINDOW_Y_POS, DEFUALT_Y));
                    stage.setWidth( stagePreferences.getDouble(WINDOW_WIDTH,  DEFUALT_WIDTH));
                    stage.setHeight(stagePreferences.getDouble(WINDOW_HEIGHT, DEFUALT_HEIHGT));
                }                
            }
        } catch (BackingStoreException ex) {
            LOGGER.error(ex, "Could not access preferences for window " + name);
        }
    }
    
    /**
     * Adds an OnCloseRequest handle to the window which will store the position, 
     * size and maximized status of the window. 
     * 
     * @param stage  the stage to read settings from
     * @param name   the name under which we store the settings
     */
    static void applySaveOnCloseMethod(Stage stage, String name){
        stage.setOnCloseRequest( ev -> {
            try {
                Preferences stagePreferences = PREFERENCES.node(name);
                if( stage.isMaximized() ){
                    stagePreferences.putBoolean(WINDOW_MAXIMIZED, true);
                } else {
                    stagePreferences.putBoolean(WINDOW_MAXIMIZED, false);
                    stagePreferences.putDouble(WINDOW_X_POS, stage.getX());
                    stagePreferences.putDouble(WINDOW_Y_POS, stage.getY());
                    stagePreferences.putDouble(WINDOW_WIDTH, stage.getWidth());
                    stagePreferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
                }
                stagePreferences.flush();
            } catch (final BackingStoreException ex) {
                LOGGER.error(ex, "Could not flush preferences for window " + name);
            } 
        });
    }

}
