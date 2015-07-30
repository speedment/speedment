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

import com.speedment.util.version.SpeedmentVersion;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import static javafx.stage.Modality.APPLICATION_MODAL;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class AboutController implements Initializable {
	
	@FXML private Button close;
	@FXML private Label title;
	@FXML private Label version;
	@FXML private Label license;
	@FXML private Label external;
	private final Stage popup;
	
	public AboutController(Stage popup) {
		this.popup = popup;
	}

	/**
	 * Initializes the controller class.
	 * @param url The url
	 * @param rb The resource bundle.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		close.setOnAction(ev -> popup.close());
		version.setText(SpeedmentVersion.getImplementationVersion());
		external.setText(
			"It includes software licensed as follows:\n\n" +
			"Apache 2:\n" +
			"groovy-all (2.4.0), log4j-api (2.1), log4j-core (2.1).\n" +
			"\n" +
			"Creative Commons 2.5:\n" +
			"silk (1.3)\n" +
			"\n" 
		);
	}
	
	public static void showIn(Stage stage) {
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