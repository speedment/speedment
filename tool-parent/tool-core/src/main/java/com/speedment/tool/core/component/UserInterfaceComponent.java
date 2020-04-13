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
package com.speedment.tool.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.notification.Notification;
import com.speedment.tool.core.resource.Icon;
import com.speedment.tool.propertyeditor.PropertyEditor;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The user interface component contains a number of useful methods required to
 * pass information between different parts of the UI.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@InjectKey(UserInterfaceComponent.class)
public interface UserInterfaceComponent {

    ////////////////////////////////////////////////////////////////////////////
    //                            Global Properties                           //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Returns the {@link ProjectProperty} used in this session.
     * 
     * @return  the project property
     */
    ProjectProperty projectProperty();
    
    /**
     * Returns the JavaFX Application that is currently running.
     * 
     * @return  the JavaFX application
     */
    Application getApplication();
    
    /**
     * Returns the main JavaFX stage of the current session.
     * 
     * @return  the main JavaFX stage.
     */
    Stage getStage();
    
    /**
     * Returns an observable list with all the notifications
     * currently visible in the user interface.
     * 
     * @return  the notification list
     */
    ObservableList<Notification> notifications();
    
    /**
     * Returns an observable list of the output messages in the
     * output section of the user interface.
     * 
     * @return  the output messages 
     */
    ObservableList<Node> outputMessages();
    
    /**
     * Returns an observable list with the items in the configuration tree
     * that is currently selected.
     * 
     * @return  the list of selected tree items
     */
    ObservableList<TreeItem<DocumentProperty>> getSelectedTreeItems();
    
    /**
     * Returns the observable list of currently visible properties.
     * 
     * @return  visible properties
     */
    ObservableList<PropertyEditor.Item> getProperties();

    ////////////////////////////////////////////////////////////////////////////
    //                            Menubar actions                             //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Opens a new empty project in a new window.
     */
    void newProject();
    
    /**
     * Opens an existing project in a new window. This will query the 
     * user for the location of the project.
     */
    void openProject();

    /**
     * Opens an existing project wither in the same or in a new window. 
     * This will query the user for the location of the project.
     * 
     * @param reuse  if the same or a new stage should be used
     */
    void openProject(ReuseStage reuse);

    /**
     * Saves the project. If the project location is known, it is saved
     * to that location. Else, the user is queried for the save target.
     */
    void saveProject();

    /**
     * Saves the project to a location that the user specified.
     */
    void saveProjectAs();
    
    /**
     * Closes down this window. If this is the last remaining stage, the
     * application also terminates.
     */
    void quit();

    /**
     * Reloads the currently open project from the database after
     * verifying that this is really what the user wants. If no password
     * exists in the {@link PasswordComponent}, the user will be queried
     * for this as well.
     */
    void reload();

    /**
     * Executes the code generator for the current project.
     */
    void generate();

    BooleanProperty projectTreeVisibleProperty();

    BooleanProperty workspaceVisibleProperty();

    BooleanProperty outputVisibleProperty();

    void prepareProjectTree(SplitPane parent, Node projectTree);

    void prepareWorkspace(SplitPane parent, Node workspace);

    void prepareOutput(SplitPane parent, Node output);

    /**
     * Opens the default internet browser and shows the relevant manual page.
     */
    void showManual();

    /**
     * Opens the default internet browser and shows the relevant Gitter page.
     */
    void showGitter();

    /**
     * Opens the default internet browser and shows the relevant issue page.
     */
    void reportIssue();

    /**
     * Opens the default internet browser and shows the relevant Github page.
     */
    void showGithub();

    ////////////////////////////////////////////////////////////////////////////
    //                             Dialog Messages                            //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Shows an error message in the user interface with the specified
     * title and message.
     * 
     * @param title    the title
     * @param message  the message
     * 
     * @see #showError(String, String, Throwable)
     */
    void showError(String title, String message);

    /**
     * Shows an error message in the user interface with the specified
     * title and message and the contents of the specified throwable in
     * a expandable area below it.
     * 
     * @param title    the title
     * @param message  the message
     * @param ex       the details of the error
     * 
     * @see #showError(String, String)
     */
    void showError(String title, String message, Throwable ex);

    /**
     * Shows a warning message in the user interface with the specified
     * title and message. The user can answer the warning with either
     * an {@link ButtonType#OK} or a {@link ButtonType#CANCEL}.
     * 
     * @param title    the title
     * @param message  the message
     * @return         the result of the warning.
     */
    Optional<ButtonType> showWarning(String title, String message);

    /**
     * Shows a dialog to the user where he or she is asked to enter
     * a password. The entered password will be stored in the 
     * {@link PasswordComponent} under the key given by the 
     * specified {@link Dbms}.
     * <p>
     * The password will only be stored for this session!
     * 
     * @param dbms  the dbms to store the password for
     */
    void showPasswordDialog(DbmsProperty dbms);

    /**
     * Shows a progress dialog that measures how far a particular 
     * task has come. This is useful for particularly time 
     * consuming processes like connecting to a remote database.
     * 
     * @param title     the title on the dialog
     * @param progress  the progress measure
     * @param task      the task that is being performed
     */
    void showProgressDialog(String title, ProgressMeasure progress, CompletableFuture<Boolean> task);
    
    /**
     * Shows a dialog with all the issues that has been posted to the
     * {@link IssueComponent} so far.
     */
    void showIssues();

    /**
     * Shows a small notification in the user interface. The specified
     * message should be really short and to-the-point.
     * <p>
     * The default icon is an exclamation mark.
     * 
     * @param message  short notification message
     */
    void showNotification(String message);

    /**
     * Shows a small notification with a custom icon in the user 
     * interface. The specified message should be really short 
     * and to-the-point.
     * 
     * @param message  short notification message
     * @param icon     custom icon
     */
    void showNotification(String message, Icon icon);

    /**
     * Shows a small notification in the user interface, executing
     * an action if the user clicks on it. The specified
     * message should be really short and to-the-point.
     *
     * @param message  short notification message
     * @param action   action to be performed on click
     */
    void showNotification(String message, Runnable action);

    /**
     * Shows a small notification with a custom color palette in 
     * the user interface. The specified message should be really 
     * short and to-the-point.
     * 
     * @param message  short notification message
     * @param palette  custom color palette
     */
    void showNotification(String message, Palette palette);

    /**
     * Shows a small notification with a custom icon and color 
     * palette in the user interface. The specified message should 
     * be really short and to-the-point.
     * 
     * @param message  short notification message
     * @param icon     custom icon
     * @param palette  custom color palette
     */
    void showNotification(String message, Icon icon, Palette palette);

    /**
     * Shows a small notification with a custom icon and color 
     * palette in the user interface, executing an action if the 
     * user clicks on it. The specified message should be really 
     * short and to-the-point.
     * 
     * @param message  short notification message
     * @param icon     custom icon
     * @param palette  custom color palette
     * @param action   action to be performed on click
     */
    void showNotification(String message, Icon icon, Palette palette, Runnable action);

    ////////////////////////////////////////////////////////////////////////////
    //                                  Other                                 //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Clears the output log in the user interface.
     */
    void clearLog();

    /**
     * Logs a particular line in the output section of the user interface.
     * 
     * @param line  the message to log
     */
    void log(Label line);
    /**
     * Opens the specified URL in an internet browser.
     * 
     * @param url  the url to open
     */
    void browse(String url);
    
    /**
     * Enumeration of whether a new project should be opened in the same
     * stage or in a new stage.
     */
    enum ReuseStage {
        
        /**
         * This will close down the currently opened project and use the
         * same JavaFX window to open the specified project.
         */
        USE_EXISTING_STAGE,
        
        /**
         * This will open the specified project in a whole new window.
         * Both windows will be open from now on.
         */
        CREATE_A_NEW_STAGE
    }

    /**
     * Starts the application
     *
     * @param application to start
     * @param stage to use
     */
    void start(Application application, Stage stage);

}