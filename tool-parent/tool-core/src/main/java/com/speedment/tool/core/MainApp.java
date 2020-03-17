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
package com.speedment.tool.core;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.core.GeneratorBundle;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.runtime.application.provider.DefaultApplicationBuilder;
import com.speedment.runtime.application.provider.DefaultApplicationMetadata;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.util.EmailUtil;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.event.UIEvent;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.util.InjectionLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.speedment.common.logger.Level.DEBUG;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class MainApp extends Application {

    private static final Logger LOGGER = LoggerManager.getLogger(MainApp.class);

    public static void setInjector(Injector injector) {
        InjectorHolder.INSTANCE.setInjector(injector);
    }

    @Override
    public void start(Stage stage) {
        requireNonNull(stage);

        InjectorBuilder.logger().setLevel(DEBUG);
        
        if (InjectorHolder.INSTANCE.getInjector() == null) {
            LOGGER.warn("Creating new Speedment instance for UI session.");
            final Injector newInjector = new DefaultApplicationBuilder(
                    getClass().getClassLoader(),
                    DefaultApplicationMetadata.class
                )
                .withBundle(GeneratorBundle.class)
                .withComponent(ToolBundle.class)
                .build().getOrThrow(Injector.class);
            InjectorHolder.INSTANCE.setInjector(newInjector);
        }

        final Injector injector = InjectorHolder.INSTANCE.getInjector();

        final UserInterfaceComponentImpl ui = injector.getOrThrow(UserInterfaceComponentImpl.class);
        final ProjectComponent projects     = injector.getOrThrow(ProjectComponent.class);
        final EventComponent events         = injector.getOrThrow(EventComponent.class);
        final InjectionLoader loader        = injector.getOrThrow(InjectionLoader.class);

        events.on(UIEvent.class, ev -> {
            if (ev == UIEvent.OPEN_MAIN_WINDOW) {
                ui.showNotification(
                    "You are running Speedment Open Source. Click here to " +
                    "upgrade to Speedment Enterprise.",
                    FontAwesome.PLUS, Palette.SUCCESS,
                    () -> ui.browse("https://speedment.com/pricing")
                );
            }
        });
        
        ui.start(this, stage);

        if (EmailUtil.hasEmail()) {
            if (projects.getProject().dbmses().noneMatch(dbms -> true)) {
                loader.loadAndShow("Connect");
                stage.setTitle("Connect to database");
            } else {
                loader.loadAndShow("Scene");
                events.notify(UIEvent.OPEN_MAIN_WINDOW);
                ui.showNotification(
                    "Metadata has been loaded from an offline file. Click " +
                    "here to reload from database.",
                    FontAwesome.REFRESH,
                    Palette.INFO,
                    ui::reload
                );
            }
        } else {
            loader.loadAndShow("MailPrompt");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}