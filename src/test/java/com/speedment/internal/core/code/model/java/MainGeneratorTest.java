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
package com.speedment.internal.core.code.model.java;

import com.speedment.config.db.Project;
import com.speedment.internal.codegen.base.Meta;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.core.code.MainGenerator;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class MainGeneratorTest extends SimpleModelTest {

    @Test
    
    public void testAccept() {
        System.out.println("accept");

        final MainGenerator instance = new MainGenerator(speedment) {
            
            @Override
            protected void writeToFile(Project project, Meta<File, String> meta, AtomicInteger fileCounter) {
                System.out.println("Processing "+meta.getModel().getName());
                // Do nothing on file...
            }
            
        };
        instance.accept(project);
    }

}