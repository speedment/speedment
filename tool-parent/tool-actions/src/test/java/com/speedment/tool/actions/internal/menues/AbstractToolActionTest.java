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
package com.speedment.tool.actions.internal.menues;

import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.config.*;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
abstract class AbstractToolActionTest extends ApplicationTest {

    @BeforeAll
    static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    MockProjectTreeComponent projectTree;
    ProjectProperty project;
    DbmsProperty dbms;
    SchemaProperty schema;
    TableProperty table1;
    TableProperty table2;
    ColumnProperty table1Column1;
    ColumnProperty table1Column2;
    ColumnProperty table1Column3;
    ColumnProperty table2Column1;
    ColumnProperty table2Column2;
    ColumnProperty table2Column3;
    List<DocumentProperty> all;

    @BeforeEach
    void setUp() {
        projectTree = new MockProjectTreeComponent();

        all = Arrays.asList(
            project = createMockProject("TestProject"),
            dbms = createMockDbms(project, "TestDbms"),
            schema = createMockSchema(dbms, "TestSchema"),
            table1 = createMockTable(schema, "TestTable1"),
            table2 = createMockTable(schema, "TestTable2"),
            table1Column1 = createMockColumn(table1, "TestTable1Column1", "int"),
            table1Column2 = createMockColumn(table1, "TestTable1Column2", "java.lang.String"),
            table1Column3 = createMockColumn(table1, "TestTable1Column3", "long"),
            table2Column1 = createMockColumn(table2, "TestTable2Column1", "int"),
            table2Column2 = createMockColumn(table2, "TestTable2Column2", "java.lang.String"),
            table2Column3 = createMockColumn(table2, "TestTable2Column3", "long")
        );
    }

    ProjectProperty createMockProject(String name) {
        ProjectProperty project = new ProjectProperty();
        project.nameProperty().setValue(name);
        return project;
    }

    DbmsProperty createMockDbms(ProjectProperty project, String name) {
        DbmsProperty dbms = new DbmsProperty(project);
        project.dbmsesProperty().add(dbms);
        dbms.nameProperty().setValue(name);
        return dbms;
    }

    SchemaProperty createMockSchema(DbmsProperty dbms, String name) {
        SchemaProperty schema = new SchemaProperty(dbms);
        dbms.schemasProperty().add(schema);
        schema.nameProperty().setValue(name);
        return schema;
    }

    TableProperty createMockTable(SchemaProperty schema, String name) {
        TableProperty table = new TableProperty(schema);
        schema.tablesProperty().add(table);
        table.nameProperty().setValue(name);
        return table;
    }

    ColumnProperty createMockColumn(TableProperty table, String name, String dbType) {
        ColumnProperty column = new ColumnProperty(table);
        table.columnsProperty().add(column);
        column.nameProperty().setValue(name);
        switch (dbType) {
            case "int": case "long":
                column.typeMapperProperty().setValue(null);
                break;
            default:
                column.typeMapperProperty().setValue(TypeMapper.identity().getClass().getName());
                break;
        }
        column.stringPropertyOf("databaseType", () -> "").setValue(dbType);
        return column;
    }

    abstract AbstractToolAction newToolAction();

    final void installContextMenu() {
        final AbstractToolAction action = newToolAction();
        action.installMenuItems(projectTree);
    }

    @Test
    @DisplayName("Install menu action")
    void testInstallMenuItems() {
        installContextMenu();
        assertTrue(projectTree.hasSomethingBeenInstalled(),
            "installContextMenu() was never called");
    }

    @TestFactory
    @DisplayName("Invoke menu builder for all properties")
    Stream<DynamicTest> testInvokeMenuBuilder() {
        installContextMenu();

        return all.stream()
            .map(doc -> dynamicTest(doc.getName(), () -> {
                projectTree.buildMenu(doc).forEach(menuItem -> {
                    assertNotEquals("", menuItem.getText(),
                        "Building menu resulting in MenuItem without any text.");
                });
            }));
    }

    final void assertAnyMenuExistsFor(DocumentProperty property) {
        installContextMenu();
        assertTrue(projectTree.buildMenu(property).anyMatch(e -> true),
            () -> format("No context menu showed up for property: %s", property.getName()));
    }

    final void assertMenuExistsFor(DocumentProperty property, String buttonText) {
        installContextMenu();
        assertTrue(projectTree.buildMenu(property).anyMatch(e -> buttonText.equals(e.getText())),
            () -> format("No context menu with text '%s' showed up for property: %s",
                buttonText, property.getName()));
    }

    final void clickOnMenuButton(DocumentProperty property, String buttonText) {
        Optional<MenuItem> found = projectTree.buildMenu(property)
            .filter(mi -> buttonText.equals(mi.getText()))
            .findFirst();

        assertTrue(found.isPresent(), format("No button with text '%s' was found.", buttonText));

        found.get().getOnAction().handle(new ActionEvent());
    }

    final static class MockProjectTreeComponent
    implements ProjectTreeComponent {

        private final List<Class<?>> nodeTypes;
        private final List<ContextMenuBuilder<?>> menuBuilders;

        private MockProjectTreeComponent() {
            nodeTypes    = new ArrayList<>();
            menuBuilders = new ArrayList<>();
        }

        boolean hasSomethingBeenInstalled() {
            return !nodeTypes.isEmpty();
        }

        Stream<MenuItem> buildMenu(DocumentProperty property) {
            TreeCell<DocumentProperty> cell = new TreeCell<>();
            TreeItem<DocumentProperty> item = new TreeItem<>(property);
            cell.updateTreeItem(item);

            return IntStream.range(0, nodeTypes.size())
                .filter(i -> nodeTypes.get(i).isInstance(property))
                .mapToObj(menuBuilders::get)
                .map(builder -> {
                    @SuppressWarnings("unchecked")
                    ContextMenuBuilder<DocumentProperty> builder1 =
                        (ContextMenuBuilder<DocumentProperty>) builder;
                    return builder1;
                })
                .flatMap(builder -> builder.build(cell, property));
        }

        @Override
        public <DOC extends DocumentProperty & HasMainInterface>
        void installContextMenu(Class<? extends DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder) {
            nodeTypes.add(nodeType);
            menuBuilders.add(menuBuilder);
        }

        @Override
        public <DOC extends DocumentProperty & HasMainInterface>
        Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc) {
            throw new RuntimeException();
        }
    }
}