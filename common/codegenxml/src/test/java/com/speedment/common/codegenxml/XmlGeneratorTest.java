package com.speedment.common.codegenxml;

import com.speedment.common.codegen.internal.util.Formatting;
import static com.speedment.common.codegenxml.Elements.body;
import static com.speedment.common.codegenxml.Elements.head;
import static com.speedment.common.codegenxml.Elements.html;
import static com.speedment.common.codegenxml.Elements.title;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class XmlGeneratorTest {
    
    @Test
    public void testSimpleHTML() {
        final Document doc = Document.html(
            html()
                .add(head()
                    .add(title().add("Welcome"))
                ).add(body()
                    .add("The content of the document")
                )
        );

        Formatting.tab("    ");
        final String html = new XmlGenerator().on(doc).get();

        System.out.println(html);
    }
    
    @Test
    public void testSimpleXML() {
        final Document doc = Document.xml(
            TagElement.of("project")
                .add(TagElement.of("dependencies")
                    .add(TagElement.of("dependency").add("Speedment"))
                )
        );

        Formatting.tab("    ");
        final String html = new XmlGenerator().on(doc).get();

        System.out.println(html);
    }
}