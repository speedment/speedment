/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.controller;

import com.speedment.SpeedmentVersion;
import com.speedment.component.Component;
import com.speedment.internal.ui.resource.SpeedmentFont;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.license.License;
import com.speedment.license.Software;
import com.speedment.stream.MapStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import static javafx.stage.Modality.APPLICATION_MODAL;
import javafx.stage.Stage;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class AboutController implements Initializable {
    
    private final UISession session;
    
    private @FXML Label title;
    private @FXML Button close;
    private @FXML Label version;
    private @FXML Label external;
    
    private Stage dialog;
    
    private AboutController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        title.setTextFill(Color.web("#45a6fc"));
        title.setFont(SpeedmentFont.HEADER.get());
        version.setText(SpeedmentVersion.getImplementationVersion());

        external.setText(
            MapStream.of(session.getSpeedment().components()
                .filter(c -> !c.isInternal())
                .map(Component::asSoftware)
                .flatMap(software -> 
                    Stream.concat(
                        Stream.of(software), 
                        software.getDependencies()
                    )
                ).collect(Collectors.groupingBy(Software::getLicense))
            ).sortedByKey(License.COMPARATOR)
             .mapValue(List::stream)
             .mapValue(softwares -> 
                 softwares.map(s -> "• " + s.getName() + " (" + s.getVersion() + ")")
                    .collect(joining("\n"))
             ).map(e -> e.getKey().getName() + ":\n" + e.getValue())
              .collect(joining("\n\n"))
        );
        
        close.setOnAction(ev -> dialog.close());
    }
    
    public static void createAndShow(UISession session) {
        final Stage dialog = new Stage();
        
        final Parent root  = Loader.create(session, "About", AboutController::new, control -> {
            control.dialog = dialog;
        });
        
        final Scene scene  = new Scene(root);
        scene.getStylesheets().add(session.getSpeedment().getUserInterfaceComponent().getStylesheetFile());
        
        dialog.setTitle("About Speedment");
        dialog.initModality(APPLICATION_MODAL);
        dialog.getIcons().add(SpeedmentIcon.SPIRE.load());
        dialog.initOwner(session.getStage());
        dialog.setScene(scene);
        dialog.show();
    }
}