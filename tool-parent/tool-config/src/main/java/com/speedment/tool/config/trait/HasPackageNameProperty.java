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
package com.speedment.tool.config.trait;

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.trait.HasPackageName;
import com.speedment.runtime.config.trait.HasPackageNameUtil;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

import java.util.Optional;

import static javafx.beans.binding.Bindings.createStringBinding;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */

public interface HasPackageNameProperty extends DocumentProperty, HasPackageName, HasNameProperty {
    
    default StringProperty packageNameProperty(){
        return stringPropertyOf(HasPackageNameUtil.PACKAGE_NAME, () -> null);
    }
    
    @Override
    default Optional<String> getPackageName() {
        return Optional.ofNullable(packageNameProperty().get());
    }
    
    /**
     * Returns the default value for the package name
     *
     * @param injector  the injector, in case an implementing class needs it
     * @return          the default package name 
     */
    default ObservableStringValue defaultPackageNameProperty(Injector injector) {
        final TranslatorSupport<?> support = new TranslatorSupport<>(injector, this);
        
        if (this instanceof ProjectProperty) {
            final ProjectProperty project = (ProjectProperty) this;
            return createStringBinding(
                support::defaultPackageName, 
                project.nameProperty(), 
                project.companyNameProperty()
            );
        } else if (this instanceof HasAliasProperty) {
            final HasAliasProperty alias = (HasAliasProperty) this;
            return createStringBinding(support::defaultPackageName, alias.aliasProperty());
        } else {
            return createStringBinding(support::defaultPackageName, nameProperty());
        }
    }
}
