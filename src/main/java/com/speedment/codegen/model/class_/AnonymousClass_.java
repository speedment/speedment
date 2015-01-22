package com.speedment.codegen.model.class_;

import com.speedment.codegen.model.modifier.ClassModifier_;
import com.speedment.codegen.model.package_.Package_;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author pemi
 */
public class AnonymousClass_ extends Class_ {

    @Override
    public boolean is(ClassModifier_ modifier) {
        return false;
    }

    @Override
    public Set<ClassModifier_> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public CharSequence getName() {
        return "";
    }

    @Override
    public void setName(CharSequence name) {
        throwUnsupportedOperationException("setName");
    }

    @Override
    public AnonymousClass_ setPackage(Package_ pagage) {
        return throwUnsupportedOperationException("setName");
    }

    @Override
    public Package_ getPackage() {
        return throwUnsupportedOperationException("getPackage");
    }

    private static <T> T throwUnsupportedOperationException(final String msg) {
        throw new UnsupportedOperationException("The method " + msg + " is not supported by " + AnonymousClass_.class.getName());
    }

}
