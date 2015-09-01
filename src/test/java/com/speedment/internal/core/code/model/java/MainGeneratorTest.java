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
package com.speedment.internal.core.code.model.java;

import com.speedment.internal.core.code.MainGenerator;
import com.speedment.Speedment;
import com.speedment.internal.core.platform.SpeedmentFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class MainGeneratorTest extends SimpleModelTest {

    @Test
    @Ignore
    public void testAccept() {
        System.out.println("accept");
        final Speedment speedment = SpeedmentFactory.newSpeedmentInstance();
        final MainGenerator instance = new MainGenerator(speedment);
        instance.accept(project);
    }

}