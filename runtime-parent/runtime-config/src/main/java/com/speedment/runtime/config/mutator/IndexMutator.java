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
package com.speedment.runtime.config.mutator;


import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.internal.IndexColumnImpl;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;

import static com.speedment.runtime.config.IndexUtil.INDEX_COLUMNS;
import static com.speedment.runtime.config.IndexUtil.UNIQUE;
import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import static com.speedment.runtime.config.util.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public class IndexMutator<DOC extends Index> extends DocumentMutatorImpl<DOC> implements 
        HasIdMutator<DOC>,    
        HasEnabledMutator<DOC>, 
        HasNameMutator<DOC> {

    public IndexMutator(DOC index) {
        super(index);
    }

    public void setUnique(Boolean unique) {
        put(UNIQUE, unique);
    }

    public IndexColumn addNewIndexColumn() {
        return new IndexColumnImpl(document(), newDocument(document(), INDEX_COLUMNS));
    }
}