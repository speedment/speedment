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
package com.speedment.internal.ui.controller;

import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import static java.util.Objects.requireNonNull;
import org.controlsfx.glyphfont.FontAwesome;

/**
 *
 * @author Emil Forslund
 */
public final class ToolbarController implements Initializable {
    
    public final static double ICON_SIZE = 2d;
    private final UISession session;
    
    private @FXML Button buttonNew;
    private @FXML Button buttonOpen;
    private @FXML Button buttonReload;
    private @FXML Button buttonGenerate;
    private @FXML ImageView logo;
    
    private ToolbarController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonNew.setOnAction(session.newProject());
        buttonOpen.setOnAction(session.openProject());
        buttonReload.setOnAction(session.reload());
        buttonGenerate.setOnAction(session.generate());
        
        final FontAwesome fa = new FontAwesome();
        buttonNew.setGraphic(fa.create(FontAwesome.Glyph.PLUS_SQUARE).size(ICON_SIZE));
        buttonOpen.setGraphic(fa.create(FontAwesome.Glyph.FOLDER_OPEN).size(ICON_SIZE));
        buttonReload.setGraphic(fa.create(FontAwesome.Glyph.REFRESH).size(ICON_SIZE));
        buttonGenerate.setGraphic(fa.create(FontAwesome.Glyph.PLAY_CIRCLE).size(ICON_SIZE));

        logo.setOnMousePressed(session.showGithub());
    }
    
    public static Node create(UISession session) {
        return Loader.create(session, "Toolbar", ToolbarController::new);
	}
}