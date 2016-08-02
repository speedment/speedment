package com.speedment.tool.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.tool.rule.Issue;
import com.speedment.tool.rule.Rule;
import javafx.collections.ObservableList;

/**
 * A component for posting and retrieving {@link Issue} that has been detected
 * during validation of all {@link Rule}
 * 
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
@InjectorKey(IssueComponent.class)
public interface IssueComponent extends Component {
    
    /**
     * Allows for posting an {@link Issue} that's been detected during
     * rule validation.
     * 
     * @param issue  the Issue
     */
    void post(Issue issue);
    
    /**
     * Clears the list of all issues.
     */
    void clear();
    
    /**
     * Retrieves an <b>unmodifiable</b> ObservableList of all current issues.
     * 
     * @return  list of all current issues
     */
    ObservableList<Issue> getIssues();
}
