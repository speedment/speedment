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
package com.speedment.tool.core.internal.rule;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.tool.core.component.IssueComponent;
import com.speedment.tool.core.rule.Issue;
import com.speedment.tool.core.rule.Rule;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Simon Jonasson
 * @since  3.0.0
 */
public final class ReferencesEnabledRule implements Rule {

    private final ProjectComponent projectComponent;
    private final IssueComponent issues;

    public ReferencesEnabledRule(
        final ProjectComponent projectComponent,
        final IssueComponent issues
    ) {
        this.projectComponent = requireNonNull(projectComponent);
        this.issues = requireNonNull(issues);
    }

    @Override
    public CompletableFuture<Boolean> verify() {
        return CompletableFuture.supplyAsync(this::checkRule);
    }

    private boolean checkRule() {
        final AtomicBoolean noIssues = new AtomicBoolean(true);
        final Project project = projectComponent.getProject();
        
        DocumentDbUtil.traverseOver(project)
            .filter(DocumentDbUtil::isAllAncestorsEnabled)
            .forEach(doc -> check(doc, noIssues));
        
        return noIssues.get();
    }

    private void check(Document document, AtomicBoolean noIssues) {
       if (document instanceof HasColumn) {
            final HasColumn source = (HasColumn) document;
            final Optional<? extends Column> target = source.findColumn();
            final String sourceName = nameOf(source);
            
            if (target.isPresent()) {
                final Column col = target.get();
                if (!col.isEnabled()) {
                    noIssues.set(false);
                    
                    final String targetName = nameOf(target.get());
                    
                    issues.post(new Issue() {
                        @Override
                        public String getTitle() {
                            return "Reference not enabled: " + col.getId();
                        }

                        @Override
                        public String getDescription() {
                            return "The referenced element " + col.getId()
                                + ", is not enabled. Disabled elements will "
                                + "not be generated. Thus, referencing a disabled element "
                                + "will result in broken code.\n"
                                + "This might be a result of the element in question not being enabled, "
                                + "or that an ancestor of the element is not enabled. \n"
                                + "To fix this issue, make sure the element " + targetName + " is enabled.";
                        }

                        @Override
                        public Issue.Level getLevel() {
                            return Issue.Level.ERROR;
                        }
                    });
                }
            } else {
                issues.post(new Issue() {
                    @Override
                    public String getTitle() {
                        return "Referenced column does not exist";
                    }

                    @Override
                    public String getDescription() {
                        return "'" + sourceName + 
                            "' references a column that does not exist. Make " + 
                            "sure the name of it corresponds to the name of " + 
                            "the referenced column in the database.";
                    }

                    @Override
                    public Issue.Level getLevel() {
                        return Level.ERROR;
                    }
                });
            }
        }
    }
    
    private String nameOf(HasName doc) {
        return DocumentUtil.relativeName(
            doc, Project.class, DocumentUtil.Name.DATABASE_NAME
        );
    }
}
