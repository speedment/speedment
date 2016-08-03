package com.speedment.tool.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.internal.util.InjectionLoader;
import com.speedment.tool.rule.Issue;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class IssueComponentImpl extends InternalOpenSourceComponent implements  IssueComponent {
    private final ObservableList<Issue> issues;
    
    private @Inject InjectionLoader loader;
    
    public IssueComponentImpl(){
        this.issues = FXCollections.observableArrayList();
    }

    @Override
    public void post(Issue issue) {
        Platform.runLater( () -> {
            if( issues.isEmpty() ){
                loader.loadAsModal("ProjectProblem");
            }
            issues.add(issue);
        });
    }
    
    @Override
    public void clear(){
        Platform.runLater( () -> issues.clear() );
    }

    @Override
    public ObservableList<Issue> getIssues() {
        return FXCollections.unmodifiableObservableList(issues);
    }
    
     @Override
    protected String getDescription() {
        return "Component responsible for collecting and displaying all issues that are posted during rule validation.";
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return IssueComponent.class;
    }
    
    //jar:file:/C:/Users/Simon/.m2/repository/com/speedment/tool/3.0.0-SNAPSHOT/tool-3.0.0-SNAPSHOT.jar!/fxml/Scene.fxml

    //jar:file:/C:/Users/Simon/.m2/repository/com/speedment/tool/3.0.0-SNAPSHOT/tool-3.0.0-SNAPSHOT.jar!/fxml/ProjectProblem.fxml
}
