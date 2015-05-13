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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class AboutController/* implements Initializable*/ {
	
//	@FXML private Button close;
//	
//	private final Popup popup;
//	
//	public AboutController(Popup popup) {
//		this.popup = popup;
//	}
//
//	/**
//	 * Initializes the controller class.
//	 * @param url
//	 * @param rb
//	 */
//	@Override
//	public void initialize(URL url, ResourceBundle rb) {
//		close.setOnAction(ev -> popup.hide());
//	}
//	
//	public static void showIn(Stage stage) {
//		final FXMLLoader loader = new FXMLLoader(AboutController.class.getResource("/fxml/About.fxml"));
//        
//		
//		
//		final AboutController control = new AboutController(stage);
//        loader.setController(control);
//
//        try {
//            final VBox root = (VBox) loader.load();
//            final Scene scene = new Scene(root);
//
//            stage.setTitle("Please enter your email");
//            stage.setScene(scene);
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//	}
}