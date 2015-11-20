package com.speedment.internal.newgui.util;

import com.speedment.Speedment;
import com.speedment.internal.gui.config.ProjectProperty;
import static java.util.Objects.requireNonNull;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectHandler {
    
    private final Speedment speedment;
    private final Stage stage;
    private final ProjectProperty project;
    
    public ProjectHandler(Speedment speedment, Stage stage) {
        this.speedment = requireNonNull(speedment);
        this.stage     = stage;
        this.project   = new ProjectProperty(speedment);
    }
    
    public ProjectProperty getProject() {
        return project;
    }
    
    public EventHandler<ActionEvent> newProject() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public EventHandler<ActionEvent> openProject() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public EventHandler<ActionEvent> saveProject() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public EventHandler<ActionEvent> saveProjectAs() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public EventHandler<ActionEvent> quit() {
        return ev -> stage.close();
    }
    
    public EventHandler<ActionEvent> generate() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}