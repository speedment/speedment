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
package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class DocumentImpl implements Document {

    private final List<Element> preamble;
    private final List<Element> elements;
    private XmlDeclaration declaration;
    private DocType docType;
    private TagElement root;

    public DocumentImpl() {
        this.elements = new ArrayList<>();
        this.preamble = new ArrayList<>();
    }

    @Override
    public List<Element> preamble() {
        return preamble;
    }

    @Override
    public List<Element> elements() {
        return elements;
    }

    @Override
    public Document setRoot(TagElement root) {
        this.root = requireNonNull(root);
        return this;
    }

    @Override
    public TagElement getRoot() {
        return root;
    }

    @Override
    public Document setXmlDeclaration(XmlDeclaration declaration) {
        this.declaration = declaration;
        return this;
    }

    @Override
    public Optional<XmlDeclaration> getXmlDeclaration() {
        return Optional.ofNullable(declaration);
    }

    @Override
    public Document setDocType(DocType docType) {
        this.docType = docType;
        return this;
    }

    @Override
    public Optional<DocType> getDocType() {
        return Optional.ofNullable(docType);
    }
}
