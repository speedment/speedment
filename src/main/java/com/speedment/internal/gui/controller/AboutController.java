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

import com.speedment.SpeedmentVersion;
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
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;

import static javafx.stage.Modality.APPLICATION_MODAL;

/**
 * FXML Controller class for the about dialog that can be opened from within the
 * GUI. This class important as it is the main point where external libraries is
 * credited.
 *
 * @author Emil Forslund
 */
public final class AboutController implements Initializable {

    @FXML
    private Button close;
    @FXML
    private Label version;
    @FXML
    private Label external;
    private final Stage popup;

    /**
     * Initializes this controller by specifying the window it will be visible
     * in. This is required to allow the popup to close itself.
     *
     * @param popup the popup stage
     */
    public AboutController(Stage popup) {
        this.popup = requireNonNull(popup);
    }

    /**
     * Initializes the controller class.
     *
     * @param url the url
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
        close.setOnAction(ev -> popup.close());
        version.setText(SpeedmentVersion.getImplementationVersion());
        external.setText(
            "It includes software licensed as follows:\n\n"
            + "Apache 2:\n"
            + "groovy-all (2.4.0).\n"
            + "\n"
            + "GPL 2 with FOSS exception:\n"
            + "mysql-connector-java (5.1.34)\n"
            + "\n"
            + "Creative Commons 2.5:\n"
            + "silk (1.3)\n"
            + "\n"
        );
    }

    /**
     * Creates and configures a new About-component in the specified stage.
     *
     * @param stage the stage
     */
    public static void showIn(Stage stage) {
        requireNonNull(stage);
        final FXMLLoader loader = new FXMLLoader(AboutController.class.getResource("/fxml/About.fxml"));
        final Stage dialog = new Stage();
        final AboutController control = new AboutController(dialog);
        loader.setController(control);

        try {
            final VBox root = (VBox) loader.load();
            final Scene scene = new Scene(root);
            dialog.setTitle("About Speedment");
            dialog.initModality(APPLICATION_MODAL);
            dialog.initOwner(stage);
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
