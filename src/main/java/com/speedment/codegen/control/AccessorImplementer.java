package com.speedment.codegen.control;

import com.speedment.codegen.model.Class_;
import com.speedment.codegen.model.Field_;
import com.speedment.codegen.model.Method_;
import com.speedment.codegen.model.Statement_;

/**
 *
 * @author pemi
 */
public class AccessorImplementer implements Controller {

    private final Class_ class_;

    public AccessorImplementer(final Class_ class_) {
        this.class_ = class_;
    }

    @Override
    public void apply() {
        class_.getFields().forEach((f) -> generateAccessors(f));
    }

    protected void generateAccessors(final Field_ field_) {
        generateGetter(field_);
        generateSetter(field_);
    }

    protected void generateGetter(final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "get" + firstCharCapital(field_.getName_()));
        method_.add(new Statement_("return " + field_.getName_()));
        class_.add(method_);
    }

    protected void generateSetter(final Field_ field_) {
        final Method_ method_ = new Method_(field_.getType_(), "set" + firstCharCapital(field_.getName_())).add(new Field_(field_.getType_(), field_.getName_()));
        method_.add(new Statement_("this." + field_.getName_() + " = " + field_.getName_()));
        class_.add(method_);
    }

    private String firstCharCapital(CharSequence s) {
        return new StringBuilder().append(Character.toUpperCase(s.charAt(0))).append(s.subSequence(1, s.length())).toString();
    }

}
