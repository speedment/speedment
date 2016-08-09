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
package com.speedment.tool.internal.rule;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.rule.Issue;
import com.speedment.tool.rule.Rule;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class ReferencesEnabledRule implements Rule {

    private @Inject
    ProjectComponent projectComponent;
    private @Inject
    IssueComponent issues;

    @Override
    public CompletableFuture<Boolean> verify() {
        return CompletableFuture.supplyAsync( () -> checkRule());
    }

    private boolean checkRule() {
        final AtomicBoolean noIssues = new AtomicBoolean(true);
        final Project project = projectComponent.getProject();
        
        DocumentDbUtil.traverseOver(project)
            .forEach(doc -> check(doc, noIssues));
        
        return noIssues.get();
    }

    private void check(Document document, AtomicBoolean noIssues) {
       if( document instanceof HasColumn ){
           final HasColumn source = (HasColumn) document;
           final Optional<? extends Column> target = source.findColumn();
            if ( target.isPresent() ) {
                final Optional<HasEnabled> disabled = target.get().ancestors()
                    .filter( doc -> doc instanceof HasEnabled )
                    .map(HasEnabled::of)
                    .filter( doc -> !doc.isEnabled() )
                    .findAny();

                if( disabled.isPresent() || !target.get().isEnabled() ){
                    noIssues.set(false);
                    final String targetName = target.get().getName();
                    final String sourceName = source.getName();
                    issues.post(new Issue() {
                        @Override
                        public String getTitle() {
                            return "Reference not enabled: " + targetName;
                        }

                        @Override
                        public String getDescription() {
                            return "The referenced element " + targetName + ", is not enabled. Disabled elements will "
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
            }
        }
    }
}
