package com.speedment.codegen;

import com.speedment.codegen.control.AccessorImplementer;
import com.speedment.codegen.model.Annotation_;
import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Field_;
import com.speedment.codegen.model.Package_;
import static com.speedment.codegen.model.Type_.STRING;
import static com.speedment.codegen.model.modifier.ClassModifier_.*;
import com.speedment.codegen.view.java8.JavaCodeGen;

/**
 *
 * @author pemi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Package_ package_ = new Package_();

        final Field_ field = new Field_(STRING, "foo").private_();

        final Class_ class_ = new Class_();
        class_.add(field);
        class_.add(new Field_(STRING, "bar"));
        class_.add(PUBLIC, STATIC);

        new AccessorImplementer(class_).apply();

        final Class_ c2 = new Class_();
        c2.set(of("private static final"));

        c2.add(Annotation_.DEPRECATED);

        JavaCodeGen gen = new JavaCodeGen();

        Field_ f = new Field_(STRING, "olle");

        System.out.println(gen.on(f));

    }

}
