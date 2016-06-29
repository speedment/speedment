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
package com.speedment.tool.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.tool.brand.Brand;
import static com.speedment.tool.internal.util.CloseUtil.newCloseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Emil Forslund
 */
public final class AboutController implements Initializable {
    
    private @Inject InfoComponent infoComponent;
    private @Inject Brand brand;
    
    private @FXML ImageView titleImage;
    private @FXML Button close;
    private @FXML Label version;
    private @FXML Label external;
    private @FXML Label license;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        brand.logoLarge().map(Image::new).ifPresent(titleImage::setImage);
        license.setText(license.getText().replace("{title}", infoComponent.title()));
        version.setText(infoComponent.version());
        
        close.setOnAction(newCloseHandler());
        
//        external.setText(
//            MapStream.of(session.getSpeedment().components()
//                .map(Component::asSoftware)
//                .flatMap(software -> 
//                    Trees.traverse(software, 
//                        Software::getDependencies, 
//                        Trees.TraversalOrder.DEPTH_FIRST_POST
//                    ).filter(s -> !s.isInternal())
//                     .distinct()
//                ).collect(Collectors.groupingBy(Software::getLicense))
//            ).sortedByKey(License.COMPARATOR)
//             .mapValue(List::stream)
//             .mapValue(softwares -> 
//                 softwares.map(s -> "• " + s.getName() + " (" + s.getVersion() + ")")
//                    .collect(joining("\n"))
//             ).map(e -> e.getKey().getName() + ":\n" + e.getValue())
//              .collect(joining("\n\n"))
//        );
    }
//    
//    public static void createAndShow(UISession session) {
//        final Stage dialog = new Stage();
//        
//        final Parent root = loader.loadAndShow("About", control -> {
//            ((AboutController) control).dialog = dialog;
//        });
//        
//        final Brand brand = session.getSpeedment().getOrThrow(UserInterfaceComponent.class).getBrand();
//        
//        final Scene scene  = new Scene(root);
//        session.getSpeedment()
//            .getOrThrow(UserInterfaceComponent.class)
//            .stylesheetFiles()
//            .forEachOrdered(scene.getStylesheets()::add);
//        
//        dialog.setTitle("About " + infoComponent.title());
//        dialog.initModality(APPLICATION_MODAL);
//        brand.logoSmall().map(Image::new).ifPresent(dialog.getIcons()::add);
//        dialog.initOwner(session.getStage());
//        dialog.setScene(scene);
//        dialog.show();
//        
//        session.getSpeedment().getEventComponent().notify(UIEvent.OPEN_ABOUT_WINDOW);
//    }
}