/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.core.component.IssueComponent;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.rule.Issue;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ProjectProblemController implements Initializable {
    
    @FXML private ListView<Issue> lstProjectProblems;
    @FXML private TextFlow txtDescription;
    @FXML private Button btnClose;
    @FXML private Button btnProceed;
    
    private final ObservableList<Issue> issues;
    private final BooleanExpression hasErrors;
    
    private @Inject ConfigFileHelper configFileHelper;
    private @Inject IssueComponent issueComponent;
    
    public ProjectProblemController(){
        issues = FXCollections.observableArrayList();
        hasErrors = Bindings.isNotEmpty(issues.filtered(issue -> issue.getLevel() == Issue.Level.ERROR));
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.bindContent(issues, issueComponent.getIssues());
        
        lstProjectProblems.setItems(issues);
        lstProjectProblems.setCellFactory((ListView<Issue> param) -> new ListCell<Issue>() {
            @Override
            protected void updateItem(Issue item, boolean empty) {
                super.updateItem(item, empty);
                if( item != null ){
                    setText( item.getLevel() +" - "+ item.getTitle() );
                    switch( item.getLevel() ){
                        case ERROR:
                            setTextFill(Color.RED);
                            break;
                        case WARNING:
                            setTextFill(Color.DARKORANGE);
                            break;
                    }
                } else {
                    setText("");
                }
            }
        });
        lstProjectProblems.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            txtDescription.getChildren().clear();
            if( newValue != null ) {
                txtDescription.getChildren().addAll( new Text(newValue.getDescription() ) );
            }
        });
        
        
        
        btnClose.setOnAction( (ev) -> closeWindow() );
        
        btnProceed.setOnAction( (ev) -> closeWindowAndGenerate());
        btnProceed.disableProperty().bind(hasErrors);
        //We need to attach a scene listener somewhere, so this button will do
        btnProceed.sceneProperty().addListener( (ov, oldVal, newVal) -> {
            if( oldVal == null && newVal != null ){
                Window window = newVal.windowProperty().get();
                if( window != null ){
                    window.setOnCloseRequest( (ev) -> closeWindow() );
                } else {
                    newVal.windowProperty().addListener( (ob, oldW, newW) -> {
                        newW.setOnCloseRequest( (ev) -> closeWindow() );
                    });
                }
            }
        });
    }
    
    private void closeWindowAndGenerate() {
        if (!configFileHelper.isFileOpen()) {
            configFileHelper.setCurrentlyOpenFile(new File(ConfigFileHelper.DEFAULT_CONFIG_LOCATION));
        }
        configFileHelper.saveCurrentlyOpenConfigFile();
        configFileHelper.generateSources();
        closeWindow();
    }
    
    private void closeWindow() {
        issueComponent.clear();
        final Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
