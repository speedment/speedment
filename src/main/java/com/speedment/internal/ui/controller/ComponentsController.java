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

import com.speedment.License;
import com.speedment.Speedment;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.Component;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.internal.ui.resource.SpeedmentFont;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import static com.speedment.internal.util.Cast.cast;
import static com.speedment.stream.MapStream.comparing;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
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
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Emil Forslund
 */
public final class ComponentsController implements Initializable {

    private final UISession session;

    private @FXML
    Label title;
    private @FXML
    Label header;
    private @FXML
    Button close;
    private @FXML
    Label info;
    private @FXML
    TreeView<String> tree;

    private Stage dialog;

    private ComponentsController(UISession session) {
        this.session = requireNonNull(session);
    }

    private class TI extends TreeItem<String> {

        public TI() {
            setExpanded(true);
        }

        public TI(String name) {
            super(name);
            setExpanded(true);
        }

    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Speedment speedment = session.getSpeedment();
        title.setTextFill(Color.web("#45a6fc"));
        title.setFont(SpeedmentFont.HEADER.get());

        header.setText("Components for " + speedment.getClass().getSimpleName() + " (0x" + Integer.toHexString(System.identityHashCode(speedment)) + ')');

        //info.setText(componentInfoAsString());
        final TI root = new TI("Speedment (0x" + Integer.toHexString(System.identityHashCode(speedment)) + ')');
        root.getChildren().addAll(components());
        tree.setRoot(root);

        close.setOnAction(ev -> dialog.close());
    }

    private List<TI> components() {
        return session.getSpeedment().components()
                .sorted(comparing(c -> c.getComponentClass().getSimpleName()))
                .map(this::treeItem)
                .collect(toList());

    }

    private TI treeItem(Component c) {
        final TI result = new TI(c.getComponentClass().getSimpleName());
        result.getChildren().add(decorate(new TI(c.getClass().getSimpleName() + " (" + c.getVersion() + ") "/*+c.getLicense().getName()*/), c));
        return result;
    }

    private TI decorate(TI item, Component c) {
        cast(c, CodeGenerationComponent.class).ifPresent(cg -> {
            cg.stream()
                    .map(Entry::getValue)
                    .flatMap(Set::stream)
                    .sorted()
                    .map(TI::new)
                    .forEachOrdered(item.getChildren()::add);

        });
        cast(c, DbmsHandlerComponent.class).ifPresent(dh -> {
            dh.supportedDbmsTypes()
                    .map(DbmsType::getName)
                    .sorted()
                    .map(TI::new)
                    .forEachOrdered(item.getChildren()::add);

        });
        cast(c, TypeMapperComponent.class).ifPresent(tm -> {
            tm.stream()
                    .map(TypeMapper::getLabel)
                    .sorted()
                    .map(TI::new)
                    .forEachOrdered(item.getChildren()::add);

        });

        return item;
    }

    private String componentInfoAsString() {
        final Speedment speedment = session.getSpeedment();
        final StringBuilder sb = new StringBuilder();
        speedment.components()
                .sorted(comparing(Component::getTitle))
                .map(this::componentHeader)
                .forEachOrdered(sb::append);

        return sb.toString();
    }

    private CharSequence componentHeader(Component c) {
        return new StringBuilder()
                .append(c.getLicense().isCommercial() ? "C" : "A")
                .append(" ")
                .append(c.getVersion())
                .append(" ")
                .append(c.getTitle())
                .append("\n")
                .append(formatLine(componentDetail(c)));
    }

    private StringBuilder componentDetail(Component c) {
        return new StringBuilder()
                .append(cast(c, CodeGenerationComponent.class)
                        .map(cg -> cg.stream().map(Entry::getValue).flatMap(Set::stream).sorted().collect(joining(", ")))
                        .orElse(""))
                .append(cast(c, DbmsHandlerComponent.class)
                        .map(dh -> dh.supportedDbmsTypes().map(DbmsType::getName).sorted().collect(joining(", ")))
                        .orElse(""))
                .append(cast(c, TypeMapperComponent.class)
                        .map(tm -> tm.stream().map(TypeMapper::getLabel).sorted().collect(joining(", ")))
                        .orElse(""));

    }

    private StringBuilder formatLine(StringBuilder sb) {
        if (sb.length() > 0) {
            return new StringBuilder()
                    .append("      └──>  ")
                    .append(sb)
                    .append("\n");
        }
        return sb;
    }

    public static void createAndShow(UISession session) {
        final Stage dialog = new Stage();

        final Parent root = Loader.create(session, "Components", ComponentsController::new, control -> {
            control.dialog = dialog;
        });

        final Scene scene = new Scene(root);
        scene.getStylesheets().add(session.getSpeedment().getUserInterfaceComponent().getStylesheetFile());

        dialog.setTitle("Components");
        dialog.initModality(APPLICATION_MODAL);
        dialog.getIcons().add(SpeedmentIcon.SPIRE.load());
        dialog.initOwner(session.getStage());
        dialog.setScene(scene);
        dialog.show();
    }
}
