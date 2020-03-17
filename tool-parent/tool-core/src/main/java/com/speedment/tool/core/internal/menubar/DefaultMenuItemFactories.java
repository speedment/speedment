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
package com.speedment.tool.core.internal.menubar;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.menubar.MenuBarComponent;
import com.speedment.tool.core.menubar.MenuBarTab;
import com.speedment.tool.core.resource.SpeedmentIcon;
import com.speedment.tool.core.util.InjectionLoader;
import javafx.scene.control.CheckMenuItem;

/**
 * Installs the default menu item factories used by the Speedment Tool.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
@InjectKey(DefaultMenuItemFactories.class)
public final class DefaultMenuItemFactories {

    @ExecuteBefore(State.INITIALIZED)
    public void install(@WithState(State.INITIALIZED) MenuBarComponent menuBar,
                 @WithState(State.INITIALIZED) UserInterfaceComponent ui,
                 @WithState(State.INITIALIZED) InjectionLoader loader) {
        menuBar.forTab(MenuBarTab.FILE)
            .addMenuItem("_New", SpeedmentIcon.NEW_PROJECT, ev -> ui.newProject())
            .addMenuItem("_Open", SpeedmentIcon.OPEN_PROJECT, ev -> ui.openProject())
            .addSeparator()
            .addMenuItem("_Save", SpeedmentIcon.DISK, ev -> ui.saveProject())
            .addMenuItem("Save _As", SpeedmentIcon.DISK_MULTIPLE, ev -> ui.saveProjectAs())
            .addSeparator()
            .addMenuItem("_Quit", SpeedmentIcon.DOOR_IN, ev -> ui.quit());

        menuBar.forTab(MenuBarTab.EDIT)
            .addMenuItem("_Generate", SpeedmentIcon.RUN_PROJECT, ev -> ui.generate());

        menuBar.forTab(MenuBarTab.WINDOW)
            .set("project-tree", () -> {
                final CheckMenuItem mi = new CheckMenuItem("_Project Tree");
                mi.setGraphic(SpeedmentIcon.APPLICATION_SIDE_TREE.view());
                mi.selectedProperty().bindBidirectional(ui.projectTreeVisibleProperty());
                return mi;
            })
            .set("workspace", () -> {
                final CheckMenuItem mi = new CheckMenuItem("_Workspace");
                mi.setGraphic(SpeedmentIcon.APPLICATION_FORM.view());
                mi.selectedProperty().bindBidirectional(ui.workspaceVisibleProperty());
                return mi;
            })
            .set("output", () -> {
                final CheckMenuItem mi = new CheckMenuItem("_Output");
                mi.setGraphic(SpeedmentIcon.APPLICATION_XP_TERMINAL.view());
                mi.selectedProperty().bindBidirectional(ui.outputVisibleProperty());
                return mi;
            });

        menuBar.forTab(MenuBarTab.HELP)
            .addMenuItem("Online _Manual", SpeedmentIcon.BOOK, ev -> ui.showManual())
            .addMenuItem("Online Help Chat on _Gitter", SpeedmentIcon.USER_COMMENT, ev -> ui.showGitter())
            .addMenuItem("Report an _Issue", SpeedmentIcon.USER_COMMENT, ev -> ui.reportIssue())
            .addMenuItem("Open the Git_Hub page", SpeedmentIcon.USER_COMMENT, ev -> ui.showGithub())
            .addSeparator()
            .addMenuItem("_Components", SpeedmentIcon.BRICKS, ev -> loader.loadAsModal("Components"))
            .addSeparator()
            .addMenuItem("_About", SpeedmentIcon.INFORMATION, ev -> loader.loadAsModal("About"));
    }
}