package com.speedment.common.codegenxml;

import com.speedment.common.codegen.internal.DefaultGenerator;
import com.speedment.common.codegen.internal.DefaultTransformFactory;
import com.speedment.common.codegenxml.internal.view.AttributeView;
import com.speedment.common.codegenxml.internal.view.ContentElementView;
import com.speedment.common.codegenxml.internal.view.DocTypeView;
import com.speedment.common.codegenxml.internal.view.DocumentView;
import com.speedment.common.codegenxml.internal.view.TagElementView;
import com.speedment.common.codegenxml.internal.view.XmlDeclarationView;

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