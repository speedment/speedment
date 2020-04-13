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
package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.DocumentImpl;
import com.speedment.common.codegenxml.trait.HasElements;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public interface Document extends HasElements<Document> {

    default Document preambleAdd(Element e) {
        preamble().add(e);
        return this;
    }

    List<Element> preamble();

    Document setXmlDeclaration(XmlDeclaration declaration);

    Optional<XmlDeclaration> getXmlDeclaration();

    Document setDocType(DocType docType);

    Optional<DocType> getDocType();

    Document setRoot(TagElement root);

    TagElement getRoot();

    static Document of() {
        return new DocumentImpl();
    }

    static Document html(TagElement root) {
        return of().setDocType(DocType.of("html")).add(root);
    }
    
    static Document xmlWithDocType(TagElement root) {
        return of().setXmlDeclaration(XmlDeclaration.of("1.0", "UTF-8")).setDocType(DocType.of(root.getName())).add(root);
    }

    static Document xml(TagElement root) {
        return of().setXmlDeclaration(XmlDeclaration.of("1.0", "UTF-8")).add(root);
    }
}
