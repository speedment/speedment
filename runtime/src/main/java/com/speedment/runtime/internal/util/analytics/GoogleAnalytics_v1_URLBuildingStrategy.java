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
package com.speedment.runtime.internal.util.analytics;

import com.speedment.runtime.component.InfoComponent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

public final class GoogleAnalytics_v1_URLBuildingStrategy implements URLBuildingStrategy {

    private final String googleAnalyticsTrackingCode;
    private String refererURL = "http://www.speedment.org";

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
    public String buildURL(FocusPoint focusPoint, InfoComponent infoComponent) {

        final int cookie = RANDOM.nextInt();
        final int randomValue = RANDOM.nextInt(2147483647) - 1;
        final long now = new Date().getTime();

        String url = TRACKING_URL_PREFIX + "?utmwv=1" + //Urchin/Analytics version
            "&utmn=" + RANDOM.nextInt() +
            "&utmcs=UTF-8" + //document encoding
            "&utmsr=1440x900" + //screen resolution
            "&utmsc=32-bit" + //color depth
            "&utmul=en-us" + //user language
            "&utmje=1" + //java enabled
            "&utmfl=9.0%20%20r28" + //flash
            "&utmcr=1" + //carriage return
            "&utmdt=" + focusPoint.getEventName() + //The optimum keyword density //document title
            "&utmhn=" + HOST_NAME +//document hostname
            "&utmr=" + refererURL + //referer URL
            "&utmp=" + focusPoint.getContentURI(infoComponent) +//document page URL
            "&utmac=" + googleAnalyticsTrackingCode +//Google Analytics account
            "&utmcc=__utma%3D'" + cookie + "." + randomValue + "." + now + "." + now + "." + now + ".2%3B%2B__utmb%3D" + cookie + "%3B%2B__utmc%3D" + cookie + "%3B%2B__utmz%3D" + cookie + "." + now + ".2.2.utmccn%3D(direct)%7Cutmcsr%3D(direct)%7Cutmcmd%3D(none)%3B%2B__utmv%3D" + cookie;
        return url;
    }

    @Override
    public void setRefererURL(String refererURL) {
        this.refererURL = refererURL;
    }
}