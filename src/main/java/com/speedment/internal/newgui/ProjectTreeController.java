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
package com.speedment.internal.newgui;

import com.speedment.Speedment;
import com.speedment.exception.SpeedmentException;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectTreeController implements Initializable {
    
    private final Speedment speedment;
    
    public ProjectTreeController(Speedment speedment) {
        this.speedment = speedment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Do nothing.
    }
    
    public static Node create(Speedment speedment) {
        requireNonNull(speedment);
		final FXMLLoader loader = new FXMLLoader(ProjectTreeController.class.getResource("/fxml/newgui/ProjectTree.fxml"));
		final ProjectTreeController control = new ProjectTreeController(speedment);
        loader.setController(control);

        try {
            final Node loaded = loader.load();
            return loaded;
        } catch (IOException ex) {
            throw new SpeedmentException(ex);
        }
	}
}