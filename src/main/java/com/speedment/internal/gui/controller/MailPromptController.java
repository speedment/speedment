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
package com.speedment.internal.gui.controller;

import com.speedment.Speedment;
import com.speedment.internal.gui.MainApp;
import com.speedment.internal.util.Settings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.speedment.internal.gui.controller.AlertController.showAlert;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 * FXML Controller class for the mail prompt that is shown the first time the
 * user launches the GUI.
 *
 * @author Emil Forslund
 */
public final class MailPromptController implements Initializable {

    @FXML private TextField fieldMail;
    @FXML private Button buttonOkey;

    private final Stage stage;
    private final Speedment speedment;

    private final static Predicate<String> IS_INVALID_MAIL
        = s -> !s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    /**
     * Initializes the prompt using the stage to display it in.
     *
     * @param stage the stage
     */
    private MailPromptController(Speedment speedment, Stage stage) {
        this.stage = requireNonNull(stage);
        this.speedment = requireNonNull(speedment);
    }

    /**
     * Initializes the controller class.
     *
     * @param url the URL to use
     * @param rb the ResourceBundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
        fieldMail.textProperty().addListener((ov, o, n) -> {
            buttonOkey.setDisable(IS_INVALID_MAIL.test(n));
        });

        buttonOkey.setOnAction(ev -> {
            ProjectPromptController.showIn(speedment, stage);

            try {
                Settings.inst().set("user_mail", fieldMail.getText());
            } catch (RuntimeException ex) {
                showAlert(stage, "Error!",
                    "Could not store the settings in the properties file."
                );
                throw ex;
            }
        });
    }

    /**
     * Creates and configures a new Mail Prompt-component in the specified
     * stage.
     *
     * @param speedment instance to use
     * @param stage the stage
     */
    public static void showIn(Speedment speedment, Stage stage) {
        requireNonNulls(speedment, stage);
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/MailPrompt.fxml"));
        final MailPromptController control = new MailPromptController(speedment, stage);
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
