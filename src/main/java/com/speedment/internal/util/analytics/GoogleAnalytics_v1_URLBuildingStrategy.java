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
package com.speedment.internal.util.analytics;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public final class GoogleAnalytics_v1_URLBuildingStrategy implements URLBuildingStrategy {

    private final String googleAnalyticsTrackingCode;
    private String refererURL = "http://www.BoxySystems.com";

    private static final String TRACKING_URL_PREFIX = "http://www.google-analytics.com/__utm.gif";

    private static final Random RANDOM = new Random();
    private static String HOST_NAME = "localhost";

    static {
        try {
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            //ignore this
        }
    }

    public GoogleAnalytics_v1_URLBuildingStrategy(String googleAnalyticsTrackingCode) {
        this.googleAnalyticsTrackingCode = googleAnalyticsTrackingCode;
    }

    @Override
    public String buildURL(FocusPoint focusPoint) {

        final int cookie = RANDOM.nextInt();
        final int randomValue = RANDOM.nextInt(2147483647) - 1;
        final long now = new Date().getTime();

        StringBuilder url = new StringBuilder(TRACKING_URL_PREFIX);
        url.append("?utmwv=1"); //Urchin/Analytics version
        url.append("&utmn=").append(RANDOM.nextInt());
        url.append("&utmcs=UTF-8"); //document encoding
        url.append("&utmsr=1440x900"); //screen resolution
        url.append("&utmsc=32-bit"); //color depth
        url.append("&utmul=en-us"); //user language
        url.append("&utmje=1"); //java enabled
        url.append("&utmfl=9.0%20%20r28"); //flash
        url.append("&utmcr=1"); //carriage return
        url.append("&utmdt=").append(focusPoint.getContentTitle()); //The optimum keyword density //document title
        url.append("&utmhn=").append(HOST_NAME);//document hostname
        url.append("&utmr=").append(refererURL); //referer URL
        url.append("&utmp=").append(focusPoint.getContentURI());//document page URL
        url.append("&utmac=").append(googleAnalyticsTrackingCode);//Google Analytics account
        url.append("&utmcc=__utma%3D'").append(cookie).append(".").append(randomValue).append(".").append(now).append(".").append(now).append(".").append(now).append(".2%3B%2B__utmb%3D").append(cookie).append("%3B%2B__utmc%3D").append(cookie).append("%3B%2B__utmz%3D").append(cookie).append(".").append(now).append(".2.2.utmccn%3D(direct)%7Cutmcsr%3D(direct)%7Cutmcmd%3D(none)%3B%2B__utmv%3D").append(cookie);
        return url.toString();
    }

    @Override
    public void setRefererURL(String refererURL) {
        this.refererURL = refererURL;
    }
}
