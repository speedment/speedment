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
package com.speedment.tool.controller;

import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.UISession;
import com.speedment.tool.util.Loader;
import com.speedment.runtime.internal.util.Trees;
import com.speedment.runtime.license.License;
import com.speedment.runtime.license.Software;
import com.speedment.fika.mapstream.MapStream;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.event.UIEvent;
import java.net.URL;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.stage.Modality.APPLICATION_MODAL;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class AboutController implements Initializable {
    
    private final UISession session;
    
    private @FXML ImageView titleImage;
    private @FXML Button close;
    private @FXML Label version;
    private @FXML Label external;
    private @FXML Label license;
    
    private Stage dialog;
    
    private AboutController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final InfoComponent info = session.getSpeedment().getInfoComponent();
        final Brand brand = session.getSpeedment().get(UserInterfaceComponent.class).getBrand();
        brand.logoLarge().map(Image::new).ifPresent(titleImage::setImage);
        
        license.setText(license.getText().replace("{title}", info.title()));
        version.setText(info.version());
        
        external.setText(
            MapStream.of(session.getSpeedment().components()
                .map(Component::asSoftware)
                .flatMap(software -> 
                    Trees.traverse(software, 
                        Software::getDependencies, 
                        Trees.TraversalOrder.DEPTH_FIRST_POST
                    ).filter(s -> !s.isInternal())
                     .distinct()
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
        
        final Parent root = Loader.create(session, "About", control -> {
            ((AboutController) control).dialog = dialog;
        });
        
        final InfoComponent info = session.getSpeedment().getInfoComponent();
        final Brand brand = session.getSpeedment().get(UserInterfaceComponent.class).getBrand();
        
        final Scene scene  = new Scene(root);
        session.getSpeedment()
            .get(UserInterfaceComponent.class)
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);
        
        dialog.setTitle("About " + info.title());
        dialog.initModality(APPLICATION_MODAL);
        brand.logoSmall().map(Image::new).ifPresent(dialog.getIcons()::add);
        dialog.initOwner(session.getStage());
        dialog.setScene(scene);
        dialog.show();
        
        session.getSpeedment().getEventComponent().notify(UIEvent.OPEN_ABOUT_WINDOW);
    }
}