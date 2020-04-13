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
package com.speedment.tool.config.provider;

import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.internal.component.DocumentPropertyComponentImpl;

import java.util.List;

public class DelegateDocumentPropertyComponent implements DocumentPropertyComponent {

    private final DocumentPropertyComponentImpl documentPropertyComponent;

    public DelegateDocumentPropertyComponent() {
        this.documentPropertyComponent = new DocumentPropertyComponentImpl();
    }

    @Override
    public <PARENT extends DocumentProperty> void setConstructor(
            Constructor<PARENT> constructor, List<String> keyPath) {
        documentPropertyComponent.setConstructor(constructor, keyPath);
    }

    @Override
    public <PARENT extends DocumentProperty> Constructor<PARENT> getConstructor(
            List<String> keyPath) {
        return documentPropertyComponent.getConstructor(keyPath);
    }


}
