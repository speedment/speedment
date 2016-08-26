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
package com.speedment.tool.internal.component;

import com.speedment.internal.common.injector.annotation.Inject;
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
}
