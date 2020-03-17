/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.provider.BaseDocument;
import com.speedment.runtime.config.util.DocumentUtil;

/**
 * Trait for {@link Document documents} that has a {@link #deepCopy()} method
 * that returns a mutable copy of the document.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
public interface HasDeepCopy extends Document {

    /**
     * Returns a <em>mutable</em> copy of this document. The deepCopy should be
     * a deep copy, but does not need to use the same implementations as the
     * original.
     *
     * @return  a mutable deep copy of the document
     */
    default Document deepCopy() {
        return DocumentUtil.deepCopy(this,
            data -> new BaseDocument(getParent().orElse(null), data));
    }
}
