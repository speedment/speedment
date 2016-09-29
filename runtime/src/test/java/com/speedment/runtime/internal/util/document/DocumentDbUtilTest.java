/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.util.document;

import com.speedment.common.dbmodel.util.DocumentDbUtil;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentDbUtilTest extends AbstractDocumentTest {

    /**
     * Test of isUnique method, of class DocumentDbUtil.
     */
    @Test
    public void testIsUnique() {
        assertTrue("  Is " + columnA1.getName() + " unique: ", DocumentDbUtil.isUnique(columnA1));
        assertFalse("  Is " + columnA2.getName() + " unique: ", DocumentDbUtil.isUnique(columnA2));
        assertTrue("  Is " + columnB1.getName() + " unique: ", DocumentDbUtil.isUnique(columnB1));
        assertTrue("  Is " + columnB2.getName() + " unique: ", DocumentDbUtil.isUnique(columnB2));
        assertTrue("  Is " + columnC1.getName() + " unique: ", DocumentDbUtil.isUnique(columnC1));
        assertFalse("  Is " + columnC2.getName() + " unique: ", DocumentDbUtil.isUnique(columnC2));
        assertTrue("  Is " + columnD1.getName() + " unique: ", DocumentDbUtil.isUnique(columnD1));
        assertFalse("  Is " + columnD2.getName() + " unique: ", DocumentDbUtil.isUnique(columnD2));
    }
}