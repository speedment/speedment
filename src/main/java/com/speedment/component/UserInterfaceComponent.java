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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.component.brand.Brand;
import com.speedment.component.notification.Notification;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.controller.ProjectTreeController;
import com.speedment.internal.ui.util.OutputUtil;
import com.speedment.ui.config.DocumentProperty;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.PropertySheet;

/**
 * The user interface component contains a number of useful methods required to
 * pass information between different parts of the UI.
 * 
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version="2.3")
public interface UserInterfaceComponent extends Component {

    @Override
    default Class<UserInterfaceComponent> getComponentClass() {
        return UserInterfaceComponent.class;
    }
    
    /**
     * Sets the {@code UISession} to use. This should only be called by 
     * UISession itself to update the session in use once the platform has 
     * loaded.
     * 
     * @param session  the new UISession
     */
    void setUISession(UISession session);
    
    /**
     * Returns the UISession used. This method might throw an exception if the
     * UISession has not yet updated the component with a reference to itself
     * or if the UI isn't running.
     * 
     * @return                      the current session
     * @throws  SpeedmentException  if the UI isn't running
     */
    UISession getUISession() throws SpeedmentException;

    /**
     * Returns a observable and modifiable view of all the currently visible properties
     * in the workspace of the GUI. This can for an example be modified to show more
     * options.
     * <p>
     * The list will be modified by the GUI each time a new node in the tree is selected.
     * It is therefore recommended to only modify this component as a result of a
     * {@link #getSelectedTreeItems()} event.
     * 
     * @return  a view of the properties area
     */
    ObservableList<PropertySheet.Item> getProperties();

    /**
     * Returns a read-only view of the currently selected tree items in the GUI.
     * 
     * @return  the view of currently selected tree items.
     */
    ObservableList<TreeItem<DocumentProperty>> getSelectedTreeItems();
    
    /**
     * Returns an observable list with all the output messages currently
     * visible in the output area. New messages can be added to this list or
     * removed safely.
     * <p>
     * Messages should ideally be created using the {@link OutputUtil} class, but
     * could be any node implementation.
     * 
     * @return  the currently visible output messages
     */
    ObservableList<Node> getOutputMessages();
    
    /**
     * Returns a stream of all the css-file that should be used to style the UI.
     * 
     * @return  the stylesheet
     */
    Stream<String> stylesheetFiles();
    
    /**
     * Appends an additional stylesheet file to be used when styling the UI.
     * The specified file can override any rules specified by earlier files.
     * 
     * @param filename  the new css stylesheet
     */
    void addStylesheetFile(String filename);
    
    /**
     * Sets the brand that is shown in the top-left part of the UI.
     * 
     * @param brand  the new brand
     */
    void setBrand(Brand brand);
    
    /**
     * Returns the brand that is currently used in the top-left part of the UI.
     * 
     * @return  the brand
     */
    Brand getBrand();
    
    /**
     * Returns an observable list of the notifications that has yet to be shown 
     * in the UI.
     * 
     * @return  the observable list of notifications
     */
    ObservableList<Notification> getNotifications();
    
    /**
     * Installs a new context menu builder that will be used in the 
     * {@link ProjectTreeController} of the GUI. This is useful for plugins
     * that require a custom menu to handle custom project tree nodes. If no
     * builder exists for a particular type of node, no menu will be displayed.
     * 
     * @param <DOC>       the implementation type of the node
     * @param nodeType    the interface main type of the node
     * @param menuBuilder the builder to use
     */
    <DOC extends DocumentProperty & HasMainInterface> void 
    installContextMenu(Class<? extends DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder);
    
    /**
     * If a builder exists for the interface main type of the specified node,
     * it will be called and the result will be returned. If no builder exists,
     * an {@code empty} will be returned.
     * 
     * @param <DOC>    the implementation type of the node
     * @param treeCell  the tree cell that invoced the context menu
     * @param node      the node to create a context menu for
     * @return          the created context menu or {@code empty}
     */
    <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu> 
    createContextMenu(TreeCell<DocumentProperty> treeCell, DOC node);
    
    @FunctionalInterface
    interface ContextMenuBuilder<DOC extends DocumentProperty> {
        Stream<MenuItem> build(TreeCell<DocumentProperty> treeCell, DOC doc);
    }
}