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

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.stage.Modality.APPLICATION_MODAL;
import static java.util.Objects.requireNonNull;

/**
 * FXML Controller class for the alert messages used in the GUI.
 *
 * @author Emil Forslund
 */
public final class AlertController implements Initializable {
    
    @FXML private Label title;
    @FXML private Label message;
    @FXML private Button buttonClose;
    
    private final Stage dialog;
    private final String strTitle, strMessage;

    /**
     * Initializes this controller using the stage to display it in, a title and a text message.
     *
     * @param dialog   the dialog stage to display the message in
     * @param title    the title
     * @param message  the message
     */
    private AlertController(Stage dialog, String title, String message) {
        this.dialog     = requireNonNull(dialog);
        this.strTitle   = requireNonNull(title);
        this.strMessage = requireNonNull(message);
    }

    /**
     * Initializes the controller class.
     * 
     * @param url  the url
     * @param rb   the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
        title.setText(strTitle);
        message.setText(strMessage);
        buttonClose.setOnAction(ev -> dialog.close());
    }

    /**
     * Creates and configures a new Alert-component in the specified stage.
     *
     * @param stage    the stage to display it in
     * @param title    the title
     * @param message  the message
     */
    public static void showAlert(Stage stage, String title, String message) {
        requireNonNulls(stage, title, message);
		final FXMLLoader loader = new FXMLLoader(AlertController.class.getResource("/fxml/Alert.fxml"));
		final Stage dialog = new Stage();
		final AlertController control = new AlertController(dialog, title, message);
        loader.setController(control);

        try {
            final VBox root = (VBox) loader.load();
            final Scene scene = new Scene(root);
			dialog.setTitle(title);
			dialog.initModality(APPLICATION_MODAL);
			dialog.initOwner(stage);
			dialog.setScene(scene);
			dialog.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}
}