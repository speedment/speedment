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
package com.speedment.gui.controllers;

import com.speedment.gui.MainApp;
import com.speedment.gui.Settings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class MailPromptController implements Initializable {

    @FXML
    private Label labelHeader;
    @FXML
    private TextField fieldMail;
    @FXML
    private Label labelLicense;
    @FXML
    private Button buttonOkey;

    private final Predicate<String> isInvalidMail
            = s -> !s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    private final Stage stage;

    public MailPromptController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldMail.textProperty().addListener((ov, o, n) -> {
            buttonOkey.setDisable(isInvalidMail.test(n));
        });

        buttonOkey.setOnAction(ev -> {
            ProjectPromptController.showIn(stage);
            Settings.inst().set("user_mail", fieldMail.getText());
        });
    }

    public static void showIn(Stage stage) {
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/MailPrompt.fxml"));
        final MailPromptController control = new MailPromptController(stage);
        loader.setController(control);

        try {
            final VBox root = (VBox) loader.load();
            final Scene scene = new Scene(root);

            stage.setTitle("Please enter your email");
            stage.setScene(scene);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
