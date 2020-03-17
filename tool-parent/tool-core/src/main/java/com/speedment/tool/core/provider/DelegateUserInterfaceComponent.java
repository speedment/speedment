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
package com.speedment.tool.core.provider;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.component.RuleComponent;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.notification.Notification;
import com.speedment.tool.core.resource.Icon;
import com.speedment.tool.core.util.InjectionLoader;
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

public final class DelegateUserInterfaceComponent implements UserInterfaceComponent {

    private final UserInterfaceComponentImpl inner;

    public DelegateUserInterfaceComponent(
        final DocumentPropertyComponent documentPropertyComponent,
        final PasswordComponent passwordComponent,
        final ProjectComponent projectComponent,
        final ConfigFileHelper configFileHelper,
        final InjectionLoader loader,
        final RuleComponent rules,
        final InfoComponent info
    ) {
        this.inner = new UserInterfaceComponentImpl(documentPropertyComponent, passwordComponent, projectComponent, configFileHelper,loader, rules, info);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        inner.setInjector(injector);
    }

    public static InjectBundle include() {
        return UserInterfaceComponentImpl.include();
    }

    public void start(Application application, Stage stage) {
        inner.start(application, stage);
    }

    @Override
    public ProjectProperty projectProperty() {
        return inner.projectProperty();
    }

    @Override
    public Application getApplication() {
        return inner.getApplication();
    }

    @Override
    public Stage getStage() {
        return inner.getStage();
    }

    @Override
    public ObservableList<Notification> notifications() {
        return inner.notifications();
    }

    @Override
    public ObservableList<Node> outputMessages() {
        return inner.outputMessages();
    }

    @Override
    public ObservableList<TreeItem<DocumentProperty>> getSelectedTreeItems() {
        return inner.getSelectedTreeItems();
    }

    @Override
    public ObservableList<PropertyEditor.Item> getProperties() {
        return inner.getProperties();
    }

    @Override
    public void newProject() {
        inner.newProject();
    }

    @Override
    public void openProject() {
        inner.openProject();
    }

    @Override
    public void openProject(ReuseStage reuse) {
        inner.openProject(reuse);
    }

    @Override
    public void saveProject() {
        inner.saveProject();
    }

    @Override
    public void saveProjectAs() {
        inner.saveProjectAs();
    }

    @Override
    public void quit() {
        inner.quit();
    }

    @Override
    public void reload() {
        inner.reload();
    }

    @Override
    public void generate() {
        inner.generate();
    }

    @Override
    public void showManual() {
        inner.showManual();
    }

    @Override
    public void showGitter() {
        inner.showGitter();
    }

    @Override
    public void reportIssue() {
        inner.reportIssue();
    }

    @Override
    public void showGithub() {
        inner.showGithub();
    }

    @Override
    public BooleanProperty projectTreeVisibleProperty() {
        return inner.projectTreeVisibleProperty();
    }

    @Override
    public BooleanProperty workspaceVisibleProperty() {
        return inner.workspaceVisibleProperty();
    }

    @Override
    public BooleanProperty outputVisibleProperty() {
        return inner.outputVisibleProperty();
    }

    @Override
    public void prepareProjectTree(SplitPane parent, Node projectTree) {
        inner.prepareProjectTree(parent, projectTree);
    }

    @Override
    public void prepareWorkspace(SplitPane parent, Node workspace) {
        inner.prepareWorkspace(parent, workspace);
    }

    @Override
    public void prepareOutput(SplitPane parent, Node output) {
        inner.prepareOutput(parent, output);
    }

    @Override
    public void showError(String title, String message) {
        inner.showError(title, message);
    }

    @Override
    public void showError(String title, String message, Throwable ex) {
        inner.showError(title, message, ex);
    }

    @Override
    public Optional<ButtonType> showWarning(String title, String message) {
        return inner.showWarning(title, message);
    }

    @Override
    public void showPasswordDialog(DbmsProperty dbms) {
        inner.showPasswordDialog(dbms);
    }

    @Override
    public void showProgressDialog(String title, ProgressMeasure progress, CompletableFuture<Boolean> task) {
        inner.showProgressDialog(title, progress, task);
    }

    @Override
    public void showIssues() {
        inner.showIssues();
    }

    @Override
    public void showNotification(String message) {
        inner.showNotification(message);
    }

    @Override
    public void showNotification(String message, Icon icon) {
        inner.showNotification(message, icon);
    }

    @Override
    public void showNotification(String message, Runnable action) {
        inner.showNotification(message, action);
    }

    @Override
    public void showNotification(String message, Palette palette) {
        inner.showNotification(message, palette);
    }

    @Override
    public void showNotification(String message, Icon icon, Palette palette) {
        inner.showNotification(message, icon, palette);
    }

    @Override
    public void showNotification(String message, Icon icon, Palette palette, Runnable action) {
        inner.showNotification(message, icon, palette, action);
    }

    @Override
    public void clearLog() {
        inner.clearLog();
    }

    @Override
    public void log(Label line) {
        inner.log(line);
    }

    @Override
    public void browse(String url) {
        inner.browse(url);
    }
}
