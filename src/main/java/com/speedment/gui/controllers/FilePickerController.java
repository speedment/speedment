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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class FilePickerController implements Initializable {
	
	@FXML private ComboBox<File> picker;

	/**
	 * Initializes the controller class.
	 * @param url the URL to use
	 * @param rb the ResourceBundle to use
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("Set showing.");
		
		picker.setOnShown(l -> {
			System.out.println("Showing");
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select generation target.");
			fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home"))
			);
			
			final File file = fileChooser.showSaveDialog(picker.getScene().getWindow());
			
			if (file != null) {
				picker.setValue(file);
			}
			
			picker.hide();
		});
	}
}