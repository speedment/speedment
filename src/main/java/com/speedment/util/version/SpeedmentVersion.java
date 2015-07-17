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
package com.speedment.util.version;

/**
 *
 * @author pemi
 */
public class SpeedmentVersion {

    private static final String IMPLEMENTATION_TITLE = "Speedment";
    private static final String IMPLEMENTATION_VENDOR = "Speedment Inc."; // "Speedment, Inc." difficult to enter into POM because of ','
    private static final String IMPLEMENTATION_VERSION = "2.0.0-EA2-SNAPSHOT";

//    private static final String SPECIFICATION_TITLE = "Speedment";
//    private static final String SPECIFICATION_VENDOR = "Speedment, Inc.";
//    private static final String SPECIFICATION_VERSION = "2.0.0";

    private SpeedmentVersion() {
    }

    public static String getImplementationTitle() {
        return IMPLEMENTATION_TITLE;
    }

    public static String getImplementationVendor() {
        return IMPLEMENTATION_VENDOR;

    }

    public static String getImplementationVersion() {
        return IMPLEMENTATION_VERSION;
    }

//    public static String getSpecificationTitle() {
//        return SPECIFICATION_TITLE;
//    }
//
//    public static String getSpecificationVendor() {
//        return SPECIFICATION_VENDOR;
//    }
//
//    public static String getSpecificationVersion() {
//        return SPECIFICATION_VERSION;
//    }

}
