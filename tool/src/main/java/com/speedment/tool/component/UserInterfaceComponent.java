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
package com.speedment.tool.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.util.ProgressMeasure;
import com.speedment.tool.brand.Palette;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.notification.Notification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;

/**
 * The user interface component contains a number of useful methods required to
 * pass information between different parts of the UI.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version="2.4")
@InjectorKey(UserInterfaceComponent.class)
public interface UserInterfaceComponent extends Component {

    /*************************************************************/
    /*                     Global properties                     */
    /*************************************************************/
    
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
    ObservableList<PropertySheet.Item> getProperties();
    
    /*************************************************************/
    /*                      Menubar actions                      */
    /*************************************************************/
    
    void newProject();
    
    void openProject();

    void openProject(ReuseStage reuse);

    void saveProject();

    void saveProjectAs();
    
    void quit();

    void reload();

    void generate();

    void toggleProjectTree();

    void toggleWorkspace();

    void toggleOutput();

    void togglePreview();
    
    void showGitter();

    void showGithub();

    /*************************************************************/
    /*                      Dialog messages                      */
    /*************************************************************/
    
    void showError(String title, String message);

    void showError(String title, String message, Throwable ex);

    Optional<ButtonType> showWarning(String title, String message);

    void showPasswordDialog(DbmsProperty dbms);

    void showProgressDialog(String title, ProgressMeasure progress, CompletableFuture<Boolean> task);

    void showNotification(String message);

    void showNotification(String message, FontAwesomeIcon icon);

    void showNotification(String message, Runnable action);

    void showNotification(String message, Palette palette);

    void showNotification(String message, FontAwesomeIcon icon, Palette palette);

    void showNotification(String message, FontAwesomeIcon icon, Palette palette, Runnable action);
    
    /*************************************************************/
    /*                      Context Menues                       */
    /*************************************************************/
    
    <DOC extends DocumentProperty & HasMainInterface> void installContextMenu(Class<? extends DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder);

    <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc);
    
    @FunctionalInterface
    interface ContextMenuBuilder<DOC extends DocumentProperty> {
        Stream<MenuItem> build(TreeCell<DocumentProperty> tc, DOC doc);
    }

    /*************************************************************/
    /*                            Other                          */
    /*************************************************************/
    
    void clearLog();

    void log(Label line);

    void browse(String url);
    
    
    enum ReuseStage {
        USE_EXISTING_STAGE,
        CREATE_A_NEW_STAGE
    }
    
    @Override
    default Class<UserInterfaceComponent> getComponentClass() {
        return UserInterfaceComponent.class;
    }
}