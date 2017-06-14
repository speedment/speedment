/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegenxml;

import com.speedment.common.codegen.internal.DefaultGenerator;
import com.speedment.common.codegen.internal.DefaultTransformFactory;
import com.speedment.common.codegenxml.internal.view.*;

/**
 *
 * @author Per Minborg
 */
public final class XmlGenerator extends DefaultGenerator {

    public XmlGenerator() {
        super(new XmlTransformFactory());
    }

    private static class XmlTransformFactory extends DefaultTransformFactory {

        private XmlTransformFactory() {
            super(XmlTransformFactory.class.getSimpleName());
            install(Document.class, DocumentView.class);
            install(XmlDeclaration.class, XmlDeclarationView.class);
            install(DocType.class, DocTypeView.class);
            install(Attribute.class, AttributeView.class);
            install(ContentElement.class, ContentElementView.class);
            install(TagElement.class, TagElementView.class);
        }
    }
}