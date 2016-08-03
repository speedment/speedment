package com.speedment.tool.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.internal.util.ConfigFileHelper;
import com.speedment.tool.rule.Issue;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
                    if( item.getLevel() == Issue.Level.ERROR){
                        setTextFill( Color.RED );
                    }
                } else {
                    setText("");
                    setTextFill(Color.BLACK);
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
        configFileHelper.generateSources();
        closeWindow();
    }
    
    private void closeWindow() {
        issueComponent.clear();
        final Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
