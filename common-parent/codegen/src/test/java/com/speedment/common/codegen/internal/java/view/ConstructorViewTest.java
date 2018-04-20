package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Javadoc;
import org.junit.Test;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.SUPPRESS_WARNINGS_UNCHECKED;
import static com.speedment.common.codegen.util.Formatting.block;
import static org.junit.Assert.*;

/**
 * @author Emil Forslund
 * @since  2.4.10
 */
public class ConstructorViewTest {

    @Test
    public void transform() {
        final File file = File.of("com/example/Main.java")
            .add(Import.of(StringBuilder.class))
            .add(Class.of("Main").public_().final_()
                .add(Field.of("string", String.class).private_().final_())
                .add(Constructor.of()
                    .public_()
                    .add(SUPPRESS_WARNINGS_UNCHECKED)
                    .add(IllegalArgumentException.class)
                    .set(Javadoc.of("The constructor for this class."))
                    .add(Field.of("stringOrBuilder", CharSequence.class))
                    .add("if (stringOrBuilder instanceof String) " + block(
                        "this.string = (String) stringOrBuilder;"
                    ) + " else if () " + block(
                        "this.string = ((StringBuilder) stringOrBuilder).toString();"
                    ) + " else " + block(
                        "throw new IllegalArgumentException(\"Not a String or StringBuilder!\");"
                    ))
                )
            );

        final Generator generator = new JavaGenerator();
        System.out.println(generator.on(file).get());
    }
}