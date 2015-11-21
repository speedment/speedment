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
import com.speedment.internal.gui.config.ProjectProperty;
import java.io.File;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.Event;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class UISession {
    
    public final static File DEFAULT_GROOVY_LOCATION = new File("src/main/groovy/speedment.groovy");
    
    private final Speedment speedment;
    private final Application application;
    private final Stage stage;
    private final ProjectProperty project;
    
    public UISession(Speedment speedment, Application application, Stage stage) {
        this.speedment   = requireNonNull(speedment);
        this.application = requireNonNull(application);
        this.stage       = requireNonNull(stage);
        this.project     = new ProjectProperty(speedment);
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
    
    public <T extends Event, E extends EventHandler<T>> E newProject() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>> E openProject() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>> E saveProject() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>> E saveProjectAs() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>> E quit() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                stage.close();
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>>  E generate() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        };
    }
    
    public <T extends Event, E extends EventHandler<T>> E showGithub() {
        return (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                application.getHostServices().showDocument(GITHUB_URI);
            }
        };
    }
    
    private final static String GITHUB_URI = "https://github.com/speedment/speedment/";
}