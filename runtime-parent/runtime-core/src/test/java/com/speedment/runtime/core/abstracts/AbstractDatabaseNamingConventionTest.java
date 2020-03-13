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

package com.speedment.runtime.core.abstracts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

final class AbstractDatabaseNamingConventionTest {

    private static final AbstractDatabaseNamingConvention DATABASE_NAMING_CONVENTION = new AbstractDatabaseNamingConvention() {
        @Override
        protected String getFieldEncloserStart() {
            return "'";
        }

        @Override
        protected String getFieldEncloserEnd() {
            return "\"";
        }

        @Override
        public Set<String> getSchemaExcludeSet() {
            return Collections.emptySet();
        }
    };

    @Test
    void fullNameOf() {
        assertNotNull(DATABASE_NAMING_CONVENTION.fullNameOf("schema", "table"));
        assertNotNull(DATABASE_NAMING_CONVENTION.fullNameOf("schema", "table", "column"));
    }

    @Test
    void quoteField() {
        assertNotNull(DATABASE_NAMING_CONVENTION.quoteField("field"));
    }

    @Test
    void encloseField() {
        assertNotNull(DATABASE_NAMING_CONVENTION.encloseField("field"));
    }

    @Test
    void getFieldQuoteStart() {
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldQuoteStart());
    }

    @Test
    void getFieldQuoteEnd() {
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldQuoteEnd());
    }

    @Test
    void getFieldEncloserStart() {
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserStart());
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserStart(true));
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserStart(false));
    }

    @Test
    void getFieldEncloserEnd() {
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserEnd());
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserEnd(true));
        assertNotNull(DATABASE_NAMING_CONVENTION.getFieldEncloserEnd(false));
    }
}
