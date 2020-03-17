/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.tool.core.resource.SpeedmentIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

import static com.speedment.common.mapstream.MapStream.comparing;
import static com.speedment.tool.core.internal.util.CloseUtil.newCloseHandler;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class ComponentsController implements Initializable {

    public @Inject InfoComponent infoComponent;
    public @Inject Speedment speedment;
    public @Inject Injector injector;

    private @FXML Label title;
    private @FXML Label header;
    private @FXML Button close;
    private @FXML TreeView<String> tree;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setTextFill(Color.web("#45a6fc")); // TODO Use styleClass instead
        header.setText("Component Explorer");

        final RootItem root = new RootItem(speedment);
        root.getChildren().addAll(components());
        tree.setRoot(root);

        close.setOnAction(newCloseHandler());
    }

    private List<TreeItem<String>> components() {
        return injector.injectables()
            .map(this::treeItem)
            .sorted(comparing(TreeItem::getValue))
            .collect(toList());
    }
    
    private class RootItem extends TreeItem<String> {

        private RootItem(Speedment speedment) {
            super(
                infoComponent.getTitle() + " (0x" + Integer.toHexString(System.identityHashCode(speedment)) + ")"
            );
            setExpanded(true);
            setGraphic(SpeedmentIcon.BOX.view());

            title.setText(infoComponent.getTitle());
        }
    }

    private static class ComponentItem extends TreeItem<String> {

        private ComponentItem(Class<?> comp) {
            super(injectorKeyValue(comp).map(Class::getSimpleName).orElse(comp.getSimpleName()) + " ("
                + comp.getSimpleName()
                + ")"
            );
            setExpanded(false);
            setGraphic(SpeedmentIcon.BRICKS.view());
        }

        private static Optional<? extends Class<?>> injectorKeyValue(Class<?> clazz) {
            return traverseAncestors(clazz)
                .map(c -> c.getAnnotation(InjectKey.class))
                .filter(Objects::nonNull)
                .map(InjectKey::value)
                .findFirst();
        }
    }

    public static Stream<Class<?>> traverseAncestors(Class<?> clazz) {
        if (clazz.getSuperclass() == null) { // We have reached Object.class
            return Stream.of(clazz);
        } else {
            return Stream.concat(
                Stream.of(clazz),
                Stream.concat(traverseAncestors(clazz.getSuperclass()), Stream.of(clazz.getInterfaces()))
            ).distinct();
        }
    }

    private static class TranslatorsItem extends TreeItem<String> {

        private TranslatorsItem() {
            super("Code Generators");
            setExpanded(true);
            setGraphic(SpeedmentIcon.BOOK_OPEN.view());
        }
    }

    private static class TranslatorItem extends TreeItem<String> {

        private TranslatorItem(String translatorKey) {
            super(translatorKey);
            setExpanded(true);
            setGraphic(SpeedmentIcon.BOOK_NEXT.view());
        }
    }

    private static class DbmsTypesItem extends TreeItem<String> {

        private DbmsTypesItem() {
            super("Supported Databases");
            setExpanded(true);
            setGraphic(SpeedmentIcon.DATABASE_CONNECT.view());
        }
    }

    private static class DbmsTypeItem extends TreeItem<String> {

        private DbmsTypeItem(DbmsType dbmsType) {
            super(dbmsType.getName());
            setExpanded(true);
            setGraphic(SpeedmentIcon.DATABASE.view());
        }
    }

    private static class TypeMappersItem extends TreeItem<String> {

        private TypeMappersItem() {
            super("Installed Type Mappings");
            setExpanded(true);
            setGraphic(SpeedmentIcon.PAGE_WHITE_CUP.view());
        }
    }

    private static class TypeMapperItem extends TreeItem<String> {

        private TypeMapperItem(TypeMapper<?, ?> typeMapper) {
            super(typeMapper.getLabel());
            setExpanded(true);
            setGraphic(SpeedmentIcon.CUP.view());
        }
    }

    private TreeItem<String> treeItem(Class<?> comp) {
        final ComponentItem item = new ComponentItem(comp);

        // Show Translators
        if (CodeGenerationComponent.class.isAssignableFrom(comp)) {
            final TranslatorsItem translators = new TranslatorsItem();
            injector.get(CodeGenerationComponent.class).ifPresent(
                cg ->
                    cg.translatorKeys()
                    .sorted()
                    .map(TranslatorItem::new)
                    .forEachOrdered(translators.getChildren()::add)
                );

            if (!translators.getChildren().isEmpty()) {
                item.getChildren().add(translators);
            }
        }
        // Show Dbms Types
        if (DbmsHandlerComponent.class.isAssignableFrom(comp)) {
            final DbmsTypesItem dbmsTypes = new DbmsTypesItem();

            injector.get(DbmsHandlerComponent.class).ifPresent(dh ->
                dh.supportedDbmsTypes()
                    .sorted(Comparator.comparing(DbmsType::getName))
                    .map(DbmsTypeItem::new)
                    .forEachOrdered(dbmsTypes.getChildren()::add)
            );
            if (!dbmsTypes.getChildren().isEmpty()) {
                item.getChildren().add(dbmsTypes);
            }
        }

        // Show Type Mappers
        if (TypeMapperComponent.class.isAssignableFrom(comp)) {
            final TypeMappersItem typeMappers = new TypeMappersItem();
            injector.get(TypeMapperComponent.class).ifPresent(tm ->
                tm.stream()
                    .sorted(TypeMapper.standardComparator())
                    .map(TypeMapperItem::new)
                    .forEachOrdered(typeMappers.getChildren()::add)
            );
            if (!typeMappers.getChildren().isEmpty()) {
                item.getChildren().add(typeMappers);
            }
        }

        return item;
    }
}
