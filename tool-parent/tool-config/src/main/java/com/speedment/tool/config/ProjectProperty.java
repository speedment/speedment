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
package com.speedment.tool.config;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.ProjectPropertyMutator;
import com.speedment.tool.config.trait.*;
import com.speedment.tool.config.util.DocumentMerger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static com.speedment.runtime.config.ProjectUtil.*;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class ProjectProperty 
extends AbstractRootDocumentProperty<ProjectProperty>
implements Project,
    HasEnabledProperty,
    HasExpandedProperty,
    HasIdProperty,
    HasNameProperty,
    HasPackageNameProperty,
    HasNameProtectedProperty {

    public void merge(DocumentPropertyComponent documentPropertyComponent, Project project) {
        DocumentMerger.merge(this, project, (parent, key)
            -> ((AbstractDocumentProperty<?>) parent).createChild(documentPropertyComponent, key)
        );
    }

    @Override
    public String getName() {
        // Must implement getName because Project does not have any parent.
        return getAsString(HasNameUtil.NAME)
            .orElse(DEFAULT_PROJECT_NAME);
    }

    @Override
    public boolean isNameProtectedByDefault() {
        return false;
    }

    public StringProperty companyNameProperty() {
        return stringPropertyOf(COMPANY_NAME, Project.super::getCompanyName);
    }

    @Override
    public String getCompanyName() {
        return getAsString(COMPANY_NAME).orElse(DEFAULT_COMPANY_NAME);
    }

    public StringProperty packageLocationProperty() {
        return stringPropertyOf(PACKAGE_LOCATION, Project.super::getPackageLocation);
    }

    @Override
    public String getPackageLocation() {
        return packageLocationProperty().get();
    }

    public ObjectProperty<Path> configPathProperty() {
        final ObjectProperty<Path> pathProperty = new SimpleObjectProperty<>();

        Bindings.bindBidirectional(
            stringPropertyOf(CONFIG_PATH, () -> null),
            pathProperty,
            PATH_CONVERTER
        );

        return pathProperty;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPathProperty().get());
    }

    public ObservableList<DbmsProperty> dbmsesProperty() {
        return observableListOf(DBMSES);
    }

    @Override
    public Stream<Dbms> dbmses() {
        return dbmsesProperty().stream().map(Dbms.class::cast);
    }

    @Override
    public ProjectPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return singletonList(key);
    }

    private static final StringConverter<Path> PATH_CONVERTER = new StringConverter<Path>() {
        @Override
        public String toString(Path p) {
            if (p == null) {
                return null;
            } else {
                return p.toString();
            }
        }

        @Override
        public Path fromString(String string) {
            if (string == null) {
                return null;
            } else {
                return Paths.get(string);
            }
        }
    };
}
