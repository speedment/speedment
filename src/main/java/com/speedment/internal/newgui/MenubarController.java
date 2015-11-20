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
package com.speedment.internal.newgui;

import com.speedment.Speedment;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.gui.icon.SilkIcon;
import com.speedment.internal.gui.icon.SpeedmentIcon;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Emil Forslund
 */
public final class MenubarController implements Initializable {
    
    private final Speedment speedment;
    
    private @FXML MenuItem mbNew;
    private @FXML MenuItem mbOpen;
    private @FXML MenuItem mbSave;
    private @FXML MenuItem mbSaveAs;
    private @FXML MenuItem mbQuit;
    private @FXML MenuItem mbGenerate;
    private @FXML MenuItem mbGitHub;
    private @FXML MenuItem mbAbout;
    
    public MenubarController(Speedment speedment) {
        this.speedment = speedment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        mbNew.setGraphic(SpeedmentIcon.NEW_PROJECT.view());
        mbOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT.view());
        mbSave.setGraphic(SilkIcon.DISK.view());
        mbSaveAs.setGraphic(SilkIcon.DISK_MULTIPLE.view());
        mbQuit.setGraphic(SilkIcon.DOOR_IN.view());
        mbGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT.view());
        mbGitHub.setGraphic(SilkIcon.USER_COMMENT.view());
        mbAbout.setGraphic(SilkIcon.INFORMATION.view());
        
        mbNew.setOnAction(ev -> {
            
        });
    }
    
    public static Node create(Speedment speedment) {
        requireNonNull(speedment);
		final FXMLLoader loader = new FXMLLoader(MenubarController.class.getResource("/fxml/newgui/Menubar.fxml"));
		final MenubarController control = new MenubarController(speedment);
        loader.setController(control);

        try {
            final MenuBar loaded = (MenuBar) loader.load();
            return loaded;
        } catch (IOException ex) {
            throw new SpeedmentException(ex);
        }
	}
}