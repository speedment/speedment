/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.code.model.java;

import com.speedment.orm.code.model.Translator;
import com.speedment.codegen.lang.models.File;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.aspects.Node;
import com.speedment.util.java.JavaLanguage;

/**
 *
 * @author pemi
 * @param <T>
 */
public interface JavaClassTranslator<T extends Node> extends Translator<T, File> {

    default String variableName() {
        return variableName(getNode());
    }

    default String variableName(Node node) {
        return JavaLanguage.javaVariableName(node.getName());
    }

    default String typeName() {
        return typeName(getNode());
    }

    default String fullyQualifiedTypeName() {
        return fullyQualifiedTypeName(null);
    }

    default String fullyQualifiedTypeName(String subPath) {
        if (subPath == null || subPath.isEmpty()) {
            return basePackageName() + "." + typeName(getNode());
        } else {
            return basePackageName() + "." + subPath + "." + typeName(getNode());
        }
    }

    default String typeName(Node node) {
        return JavaLanguage.javaTypeName(node.getName());
    }

    default String basePackageName() {
        if (getNode() instanceof Project) {
            return project().getPacketName() + "." + project().getName();
        } else {
            return project().getPacketName() + "." + getNode().getRelativeName(Project.class);
        }
    }

    default String baseDirectoryName() {
        return basePackageName().replace(".", "/");
    }

}
