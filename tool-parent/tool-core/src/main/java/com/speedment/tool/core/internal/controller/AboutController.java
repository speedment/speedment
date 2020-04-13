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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.tool.core.brand.Brand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.speedment.tool.core.internal.util.CloseUtil.newCloseHandler;

/**
 *
 * @author Emil Forslund
 */
public final class AboutController implements Initializable {
    
    public @Inject InfoComponent infoComponent;
    public @Inject Brand brand;
    
    private @FXML ImageView titleImage;
    private @FXML Button close;
    private @FXML Label version;
    private @FXML Label license;
    private @FXML Label external;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        brand.logoLarge().map(Image::new).ifPresent(titleImage::setImage);
        license.setText(license.getText().replace("{title}", infoComponent.getTitle()));
        version.setText(infoComponent.getImplementationVersion());
        external.setText(infoComponent.getLicenseName());
        
        close.setOnAction(newCloseHandler());
    }
}