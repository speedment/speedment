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
package com.speedment.runtime.config;

import java.util.regex.Pattern;

public final class ProjectUtil {

    private ProjectUtil() {}

    public static final String  COMPANY_NAME            = "companyName";
    public static final String PACKAGE_LOCATION         = "packageLocation";
    public static final String SPEEDMENT_VERSION        = "speedmentVersion";
    public static final String CONFIG_PATH              = "configPath";
    public static final String DBMSES                   = "dbmses";
    public static final String APP_ID                   = "appId";

    public static final String DEFAULT_COMPANY_NAME     = "company";
    public static final String DEFAULT_PACKAGE_NAME     = "com.";
    public static final String DEFAULT_PACKAGE_LOCATION = "src/main/java/";
    public static final String DEFAULT_PROJECT_NAME     = Project.class.getSimpleName();

    static final Pattern SPLIT_PATTERN = Pattern.compile("\\."); // Pattern is immutable and therefor thread safe

}
