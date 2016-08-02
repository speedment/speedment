package com.speedment.tool.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.rule.Issue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ProjectProblemController implements Initializable{
    @FXML private ListView<Issue> lstProjectProblems;
    @FXML private TextFlow txtDescription;
    @FXML private Button btnClose;
    
    private final ObservableList<Issue> issues;
    
    private @Inject IssueComponent issueComponent;
    
    public ProjectProblemController(){
        issues = FXCollections.observableArrayList();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.bindContent(issues, issueComponent.getIssues());
        lstProjectProblems.setItems(issues);
        
        btnClose.setOnAction( (ev) -> closeWindow(ev) );
        
        lstProjectProblems.setCellFactory((ListView<Issue> param) -> new ListCell<Issue>() {
            @Override
            protected void updateItem(Issue item, boolean empty) {
                super.updateItem(item, empty);
                if( item != null ){
                    setText( item.getTitle() );
                } else {
                    setText("");
                }
            }
        });
        lstProjectProblems.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            txtDescription.getChildren().clear();
            if( newValue != null ) {
                txtDescription.getChildren().add( new Text( newValue.getDescription() ));
            }
        });
    }
    
    private void closeWindow(ActionEvent ev){
        issueComponent.clear();
        final Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
