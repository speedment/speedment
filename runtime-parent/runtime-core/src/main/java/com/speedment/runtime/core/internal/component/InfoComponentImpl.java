/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.InfoComponent;

/**
 * Default implementation of the {@link InfoComponent}-interface.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class InfoComponentImpl implements InfoComponent {
    
    @Override
    public String getTitle() {
        return "Speedment Stream";
    }

    @Override
    public String getSubtitle() {
        return "Standard Java Streams from DB, Open Source";
    }

    @Override
    public String getRepository() {
        return "speedment";
    }

    @Override
    public String getImplementationVersion() {
        return "3.1.18-SNAPSHOT";
    }

    @Override
    public String getVendor() {
        return "Speedment, Inc.";
    }

    @Override
    public String getSpecificationVersion() {
        return "3.1";
    }

    @Override
    public String getSpecificationNickname() { return "Homer"; }

    @Override
    public boolean isProductionMode() {
        return !getImplementationVersion().toUpperCase().contains("EA") 
            && !getImplementationVersion().toUpperCase().contains("SNAPSHOT");
    }

    @Override
    public String getBanner() {
        return
            "   _____                     _                      _      _____ _                            \n" +
            "  / ____|                   | |                    | |    / ____| |                           \n" +
            " | (___  _ __   ___  ___  __| |_ __ ___   ___ _ __ | |_  | (___ | |_ _ __ ___  __ _ _ __ ___  \n" +
            "  \\___ \\| '_ \\ / _ \\/ _ \\/ _` | '_ ` _ \\ / _ \\ '_ \\| __|  \\___ \\| __| '__/ _ \\/ _` | '_ ` _ \\ \n" +
            "  ____) | |_) |  __/  __/ (_| | | | | | |  __/ | | | |_   ____) | |_| | |  __/ (_| | | | | | |\n" +
            " |_____/| .__/ \\___|\\___|\\__,_|_| |_| |_|\\___|_| |_|\\__| |_____/ \\__|_|  \\___|\\__,_|_| |_| |_|\n" +
            "  ======| |===================================================================================";
    }
}