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

    static Document xml(TagElement root) {
        return of().setXmlDeclaration(XmlDeclaration.of("1.0", "UTF-8")).setDocType(DocType.of("xml")).add(root);
    }
}
