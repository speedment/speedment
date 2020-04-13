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
import com.speedment.tool.core.toolbar.ToolbarComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Emil Forslund
 */
public final class ToolbarController implements Initializable {

    public @Inject ToolbarComponent toolbar;
    private @FXML HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toolbar.populate(hBox);
    }
}