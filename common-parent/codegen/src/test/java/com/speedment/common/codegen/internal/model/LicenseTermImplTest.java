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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class LicenseTermImplTest extends AbstractTest<LicenseTerm> {

    private final static String TEXT = "A";

    public LicenseTermImplTest() {
        super(LicenseTermImpl::new,
                a -> a.setParent(File.of("C")),
                a -> a.setText(TEXT)
        );
    }

    @Test
    void constructor() {
        final LicenseTerm licenseTerm = new LicenseTermImpl(TEXT);
        assertEquals(TEXT, licenseTerm.getText());
    }

    @Test
    void setParent() {
        final File file = File.of("V");
        instance().setParent(file);
        assertEquals(file, instance().getParent().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getParent() {
        assertEquals(Optional.empty(), instance().getParent());
    }

    @Test
    void getText() {
        assertEquals("", instance().getText());
    }

    @Test
    void setText() {
        final String text = "V";
        instance().setText(text);
        assertEquals(text, instance().getText());
    }
}