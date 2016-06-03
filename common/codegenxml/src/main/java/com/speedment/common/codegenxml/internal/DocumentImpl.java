package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.DocType;
import com.speedment.common.codegenxml.Document;
import com.speedment.common.codegenxml.Element;
import com.speedment.common.codegenxml.TagElement;
import com.speedment.common.codegenxml.XmlDeclaration;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

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
