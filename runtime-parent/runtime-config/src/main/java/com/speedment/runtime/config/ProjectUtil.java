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
