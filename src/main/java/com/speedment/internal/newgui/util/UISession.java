/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.newgui.util;

import com.speedment.Speedment;
import com.speedment.internal.core.code.MainGenerator;
import com.speedment.internal.gui.config.ProjectProperty;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.newgui.output.Line;
import static com.speedment.internal.newgui.output.Line.error;
import static com.speedment.internal.newgui.output.Line.info;
import static com.speedment.internal.newgui.output.Line.success;
import com.speedment.internal.newgui.property.PropertySheetFactory;
import static com.speedment.internal.util.TextUtil.alignRight;
import com.speedment.internal.util.testing.Stopwatch;
import java.io.File;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.Event;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 */
public final class UISession {
    
    private final static Logger LOGGER = LoggerManager.getLogger(UISession.class);
    public final static File DEFAULT_GROOVY_LOCATION = new File("src/main/groovy/speedment.groovy");
    
    private final Speedment speedment;
    private final Application application;
    private final Stage stage;
    private final ProjectProperty project;
    private final PropertySheetFactory propertySheetFactory;
    
    public UISession(Speedment speedment, Application application, Stage stage) {
        this.speedment            = requireNonNull(speedment);
        this.application          = requireNonNull(application);
        this.stage                = requireNonNull(stage);
        this.project              = new ProjectProperty(speedment);
        
        this.propertySheetFactory = new PropertySheetFactory();
    }
    
    public Speedment getSpeedment() {
        return speedment;
    }
    
    public Application getApplication() {
        return application;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public ProjectProperty getProject() {
        return project;
    }
    
    public PropertySheetFactory getPropertySheetFactory() {
        return propertySheetFactory;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E newProject() {
        return on(event -> {throw new UnsupportedOperationException("Not yet implemented.");});
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E openProject() {
        return on(event -> {throw new UnsupportedOperationException("Not yet implemented.");});
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProject() {
        return on(event -> {throw new UnsupportedOperationException("Not yet implemented.");});
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProjectAs() {
        return on(event -> {throw new UnsupportedOperationException("Not yet implemented.");});
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E quit() {
        return on(event -> stage.close());
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>>  E generate() {
        return on(event -> {
            clearLog();
            final Stopwatch stopwatch = Stopwatch.createStarted();
            log(info("Generating classes " + project.getPackageName() + "." + project.getName() + ".*"));
            log(info("Target directory is " + project.getPackageLocation()));
            final MainGenerator instance = new MainGenerator(speedment);
            
            try {
                instance.accept(project);
                stopwatch.stop();
                
                log(success(
                    "+------------: Generation completed! :------------+" + "\n" +
                    "| Total time......." + alignRight(stopwatch.toString(), 31, '.') + "|\n" +
                    "| Files generated.." + alignRight("" + instance.getFilesCreated(), 31, '.') + "|\n" +
                    "+-------------------------------------------------+"
                ));
            } catch (final Exception ex) {
                if (!stopwatch.isStopped()) {
                    stopwatch.stop();
                }
                
                log(error(
                    "+--------------: Generation failed! :-------------+" + "\n" +
                    "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n" +
                    "| Files generated  " + alignRight("" + instance.getFilesCreated(), 30) + " |\n" +
                    "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 30) + " |\n" +
                    "+-------------------------------------------------+"
                ));
                
                LOGGER.error(ex, "Error! Failed to generate code.");
            }
        });
    }
    
    public void clearLog() {
        speedment.getUserInterfaceComponent().getOutputMessages().clear();
    }
    
    public void log(Line line) {
        speedment.getUserInterfaceComponent().getOutputMessages().add(line);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E showGithub() {
        return on(event -> application.getHostServices().showDocument(GITHUB_URI));
    }
    
    private <T extends Event, E extends EventHandler<T>> E on(Consumer<T> listener) {
        @SuppressWarnings("unchecked")
        final E handler = (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                listener.accept(event);
            }
        };
        
        return handler;
    }
    
    private final static String GITHUB_URI = "https://github.com/speedment/speedment/";
}