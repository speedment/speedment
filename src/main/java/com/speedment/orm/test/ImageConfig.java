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
package com.speedment.orm.test;

import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.platform.Speedment;

/**
 *
 * @author Emil Forslund
 */
public enum ImageConfig {

    INSTANCE;

    // Use dependency injection to set this
    boolean running = false;
    Speedment speedment;
/*
    Manager<Image> manager() {
        return speedment.managerOf(Image.class);
    }*/

    public void setSpeedment(Speedment speedment) {
        if (running) {
            throw new IllegalStateException("Can't change Speedment instance while running!");
        }
        
        INSTANCE.speedment = speedment;
    }

    public void setRunning() {
        running = true;
    }
}
