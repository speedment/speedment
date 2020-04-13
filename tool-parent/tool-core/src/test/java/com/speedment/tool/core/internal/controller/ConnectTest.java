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
import com.speedment.generator.core.GeneratorBundle;
import com.speedment.runtime.application.ApplicationBuilders;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.util.EmailUtil;
import com.speedment.tool.actions.ToolActionsBundle;
import com.speedment.tool.config.provider.DelegateDocumentPropertyComponent;
import com.speedment.tool.core.InjectorHolder;
import com.speedment.tool.core.MainApp;
import com.speedment.tool.core.TestBundle;
import com.speedment.tool.core.internal.component.IssueComponentImpl;
import com.speedment.tool.core.internal.component.RuleComponentImpl;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.core.internal.component.VersionComponentImpl;
import com.speedment.tool.core.internal.menubar.DefaultMenuItemFactories;
import com.speedment.tool.core.internal.menubar.MenuBarComponentImpl;
import com.speedment.tool.core.internal.toolbar.DefaultToolbarItems;
import com.speedment.tool.core.internal.toolbar.ToolbarComponentImpl;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.internal.util.InjectionLoaderImpl;
import com.speedment.tool.core.provider.DelegateSpeedmentBrand;
import com.speedment.tool.core.util.InjectionLoader;
import com.speedment.tool.propertyeditor.provider.DelegatePropertyEditorComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
@Disabled
class ConnectTest extends ApplicationTest {

    @BeforeAll
    static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        //registerPrimaryStage();
    }

    @BeforeEach
    void setUp() {}

    @Override
    public void start(Stage stage) throws Exception {
        Speedment speedment = ApplicationBuilders.empty()
            .withBundle(TestBundle.class)
            .withBundle(GeneratorBundle.class)
            .withBundle(ToolActionsBundle.class)
            .withComponent(VersionComponentImpl.class)
            .withComponent(RuleComponentImpl.class)
            .withComponent(ConfigFileHelper.class)
            .withComponent(InjectionLoaderImpl.class)
            .withComponent(ToolbarComponentImpl.class)
            .withComponent(DefaultToolbarItems.class)
            .withComponent(DefaultMenuItemFactories.class)
            .withComponent(MenuBarComponentImpl.class)
            .withComponent(DelegateDocumentPropertyComponent.class)
            .withComponent(DelegateSpeedmentBrand.class)
            .withComponent(InjectionLoaderImpl.class)
            .withComponent(ConfigFileHelper.class)
            .withComponent(DelegatePropertyEditorComponent.class)
            .withComponent(RuleComponentImpl.class)
            .withComponent(IssueComponentImpl.class)
            .withComponent(MockDbmsHandlerComponent.class)
            .build();

        InjectorHolder.INSTANCE.setInjector(speedment.getOrThrow(Injector.class));

        MainApp mainApp = new MainApp();

        UserInterfaceComponentImpl ui = speedment.getOrThrow(UserInterfaceComponentImpl.class);
        ui.start(mainApp, stage);

        final InjectionLoader loader = speedment.getOrThrow(InjectionLoader.class);
        loader.loadAndShow("Connect");
    }

    @Test
    void test() {
        clickOn("#fieldHost");

        eraseText("127.0.0.1".length());
        write("localhost");

        clickOn("#fieldPort");
        eraseText(5);
        write("1234");
    }
}
