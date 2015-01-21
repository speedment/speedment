package com.speedment.codegen;

import com.speedment.codegen.control.AccessorImplementer;
import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Field_;
import com.speedment.codegen.model.Package_;
import static com.speedment.codegen.model.Type_.STRING;
import com.speedment.codegen.model.modifier.ClassModifier_;

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
        class_.add(ClassModifier_.PUBLIC).add(ClassModifier_.STATIC);
        
        new AccessorImplementer(class_).apply();
        
    }
    
}
