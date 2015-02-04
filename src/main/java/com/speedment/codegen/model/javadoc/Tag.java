/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
