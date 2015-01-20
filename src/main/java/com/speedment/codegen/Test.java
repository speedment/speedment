package com.speedment.codegen;

import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Field_;
import static com.speedment.codegen.model.Type_.STRING;

/**
 *
 * @author pemi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final Field_ field = new Field_(STRING, "foo").private_();

        final Class_ class_ = new Class_();
        class_.add(field);
        class_.add(new Field_(STRING, "bar"));

    }

}
