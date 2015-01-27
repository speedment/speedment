package com.speedment.codegen.model.javadoc;

import static com.speedment.codegen.CodeUtil.lcfirst;
import com.speedment.util.java.JavaLanguage;
import com.speedment.util.$;

/**
 *
 * @author pemi
 */
public enum Tag {

    AUTHOR, VERSION, SINCE, SEE, PARAM, RETURN,
    EXCEPTION(2), THROWS(2),
    DEPRICATED,
    INHERIT_DOC(true, 0), LINK(1), VALUE(true, 1);

    private final boolean braces;
    private final int maxParameters;
    private static final int DEFAULT_ARGS = 1;

    private Tag() {
        this(false, DEFAULT_ARGS);
    }

    private Tag(int maxParameters) {
        this(false, maxParameters);
    }

    private Tag(boolean hasBraces) {
        this(hasBraces, DEFAULT_ARGS);
    }

    private Tag(boolean hasBraces, int maxParameters) {
        this.braces = hasBraces;
        this.maxParameters = maxParameters;
    }

    public boolean isBraces() {
        return braces;
    }

    public int getMaxParameters() {
        return maxParameters;
    }

    public CharSequence toJavaCode() {
        return new $("@", lcfirst(JavaLanguage.getJavaNameFromSqlName(name())));
    }

}
