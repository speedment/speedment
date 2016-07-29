package com.speedment.tool.internal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ProjectProblemsController implements Initializable{
    @FXML private ListView lstProjectProblems;
    @FXML private TextFlow txtDescription;
    @FXML private Button btnClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                
    }
    
    @FXML
    private void closeWindow(ActionEvent ev){
        final Node origin = (Node) ev.getSource();
        final Stage stage = (Stage) origin.getScene().getWindow();
        stage.close();
    }
    
//    TODO
//    
//    class Issue {
//        String title;
//        String description;
//        
//        enum Severity {
//            WARNING, ERROR
//        }
//    }
//    
//    class IssueComponent {
//        ObservableList<Issue> getIssues();
//    }
//    
//    class Rule {
//        //Inject
//        
//        //Will run before generating
//        CompletableFuture<Boolean> verify();
//        
//    }
//    
//    class RuleComponent {
//        CompletableFuture<Boolean> verifyAll();
//        
//        void install(Rule rule);
//    }
}
