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

import com.speedment.Speedment;
import com.speedment.SpeedmentVersion;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.Component;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.event.UIEvent;
import com.speedment.internal.ui.resource.SpeedmentFont;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.resource.SilkIcon;
import static com.speedment.internal.util.Cast.cast;
import com.speedment.license.License;
import com.speedment.license.Software;
import com.speedment.stream.MapStream;
import static com.speedment.stream.MapStream.comparing;
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
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class ComponentsController implements Initializable {

    private final UISession session;

    private @FXML Label title;
    private @FXML Label header;
    private @FXML Button close;
    private @FXML Label info;
    private @FXML TreeView<String> tree;

    private Stage dialog;

    private ComponentsController(UISession session) {
        this.session = requireNonNull(session);
    }
    
    private class RootItem extends TreeItem<String> {
        public RootItem(Speedment speedment) {
            super(
                SpeedmentVersion.getImplementationTitle() +
                " (0x" + Integer.toHexString(System.identityHashCode(speedment)) + ")"
            );
            setExpanded(true);
            setGraphic(SilkIcon.BOX.view());
            
            title.setText(session.getSpeedment().getUserInterfaceComponent().getBrand().title());
        }
    }

    private static class ComponentItem extends TreeItem<String> {

        public ComponentItem(Component comp) {
            super(comp.asSoftware().getName() + " (" +
                comp.asSoftware().getVersion() +
                ")"
            );
            setExpanded(false);
            setGraphic(SilkIcon.BRICKS.view());
        }
    };
    
    private static class TranslatorsItem extends TreeItem<String> {

        public TranslatorsItem() {
            super("Code Generators");
            setExpanded(true);
            setGraphic(SilkIcon.BOOK_OPEN.view());
        }
    };
    
    private static class TranslatorItem extends TreeItem<String> {

        public TranslatorItem(String translatorKey) {
            super(translatorKey);
            setExpanded(true);
            setGraphic(SilkIcon.BOOK_NEXT.view());
        }
    };
    
    private static class DbmsTypesItem extends TreeItem<String> {

        public DbmsTypesItem() {
            super("Supported Databases");
            setExpanded(true);
            setGraphic(SilkIcon.DATABASE_CONNECT.view());
        }
    };
    
    private static class DbmsTypeItem extends TreeItem<String> {

        public DbmsTypeItem(DbmsType dbmsType) {
            super(dbmsType.getName());
            setExpanded(true);
            setGraphic(SilkIcon.CONNECT.view());
        }
    };
    
    private static class TypeMappersItem extends TreeItem<String> {

        public TypeMappersItem() {
            super("Installed Type Mappings");
            setExpanded(true);
            setGraphic(SilkIcon.PAGE_WHITE_CUP.view());
        }
    };
    
    private static class TypeMapperItem extends TreeItem<String> {

        public TypeMapperItem(TypeMapper<?, ?> typeMapper) {
            super(typeMapper.getLabel());
            setExpanded(true);
            setGraphic(SilkIcon.CUP_LINK.view());
        }
    };
    
    private static class LicenseItem extends TreeItem<String> {
        
        private final License license;
        
        public LicenseItem(License license) {
            super(license.getName());
            this.license = license;
            setExpanded(true);
            setGraphic(SilkIcon.TEXT_SIGNATURE.view());
        }
        
        public License getLicense() {
            return license;
        }
    }
    
    private static class DependenciesItem extends TreeItem<String> {
        
        public DependenciesItem() {
            super("External Dependencies");
            setExpanded(true);
            setGraphic(SilkIcon.SITEMAP_COLOR.view());
        }
    }
    
    private static class SoftwareItem extends TreeItem<String> {
        
        public SoftwareItem(Software software) {
            super(software.getName() + " (" + software.getVersion() + ")");
            setExpanded(true);
            setGraphic(SilkIcon.BOOK_LINK.view());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Speedment speedment = session.getSpeedment();
        title.setTextFill(Color.web("#45a6fc"));
        title.setFont(SpeedmentFont.HEADER.get());

        header.setText("Component Explorer");

        final RootItem root = new RootItem(speedment);
        root.getChildren().addAll(components());
        tree.setRoot(root);
        
        close.setOnAction(ev -> dialog.close());
    }

    private List<TreeItem<String>> components() {
        
        return MapStream.of(session.getSpeedment().components()
            .collect(Collectors.groupingBy(c -> c.asSoftware().getLicense()))
        ).sortedByKey(License.COMPARATOR)
            .map((license, components) -> {
            final LicenseItem l = new LicenseItem(license);
            
            components.stream()
                .sorted(comparing(c -> c.asSoftware().getName()))
                .map(this::treeItem)
                .forEachOrdered(l.getChildren()::add);
            
            return l;
        }).collect(toList());

    }

    private TreeItem<String> treeItem(Component comp) {
        final ComponentItem item = new ComponentItem(comp);
        
        // Show License
//        item.getChildren().add(new LicenseItem(comp.asSoftware().getLicense()));
        
        // Show Translators
        final TranslatorsItem translators = new TranslatorsItem();
        cast(comp, CodeGenerationComponent.class).ifPresent(cg -> {
            cg.stream().values()
                .flatMap(Set::stream)
                .sorted()
                .map(TranslatorItem::new)
                .forEachOrdered(translators.getChildren()::add);
        });
        if (!translators.getChildren().isEmpty()) {
            item.getChildren().add(translators);
        }
        
        // Show Dbms Types
        final DbmsTypesItem dbmsTypes = new DbmsTypesItem();
        cast(comp, DbmsHandlerComponent.class).ifPresent(dh -> {
            dh.supportedDbmsTypes()
                .sorted(DbmsType.COMPARATOR)
                .map(DbmsTypeItem::new)
                .forEachOrdered(dbmsTypes.getChildren()::add);
        });
        if (!dbmsTypes.getChildren().isEmpty()) {
            item.getChildren().add(dbmsTypes);
        }
        
        // Show Type Mappers
        final TypeMappersItem typeMappers = new TypeMappersItem();
        cast(comp, TypeMapperComponent.class).ifPresent(tm -> {
            tm.stream()
                .sorted(TypeMapper.COMPARATOR)
                .map(TypeMapperItem::new)
                .forEachOrdered(typeMappers.getChildren()::add);
        });
        if (!typeMappers.getChildren().isEmpty()) {
            item.getChildren().add(typeMappers);
        }
        
        // Show Third Party Software
        final DependenciesItem dependencies = new DependenciesItem();
        comp.asSoftware().getDependencies()
            .sorted(Software.COMPARATOR)
            .map(this::createSoftwareItem)
            .forEachOrdered(dependencies.getChildren()::add);
        if (!dependencies.getChildren().isEmpty()) {
            item.getChildren().add(dependencies);
        }

        return item;
    }
    
    private SoftwareItem createSoftwareItem(Software software) {
        final SoftwareItem item = new SoftwareItem(software);
        item.getChildren().add(new LicenseItem(software.getLicense()));
        
        final DependenciesItem dependencies = new DependenciesItem();
        software.getDependencies()
            .sorted(Software.COMPARATOR)
            .map(this::createSoftwareItem)
            .forEachOrdered(dependencies.getChildren()::add);
        
        if (!dependencies.getChildren().isEmpty()) {
            item.getChildren().add(dependencies);
        }
        
        return item;
    }

    public static void createAndShow(UISession session) {
        final Stage dialog = new Stage();

        final Parent root = Loader.create(session, "Components", ComponentsController::new, control -> {
            control.dialog = dialog;
        });
        
        final UserInterfaceComponent.Brand brand = session.getSpeedment().getUserInterfaceComponent().getBrand();

        final Scene scene = new Scene(root);
        session.getSpeedment()
            .getUserInterfaceComponent()
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);

        dialog.setTitle("Components");
        dialog.initModality(APPLICATION_MODAL);
        brand.logoSmall().map(Image::new).ifPresent(dialog.getIcons()::add);
        dialog.initOwner(session.getStage());
        dialog.setScene(scene);
        dialog.show();
        
        session.getSpeedment().getEventComponent().notify(UIEvent.OPEN_COMPONENTS_WINDOW);
    }
}
