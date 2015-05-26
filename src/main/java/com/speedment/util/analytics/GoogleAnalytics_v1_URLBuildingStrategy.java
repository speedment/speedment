package com.speedment.util.analytics;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class GoogleAnalytics_v1_URLBuildingStrategy implements URLBuildingStrategy {

    private final String googleAnalyticsTrackingCode;
    private String refererURL = "http://www.BoxySystems.com";

    private static final String TRACKING_URL_Prefix = "http://www.google-analytics.com/__utm.gif";

    private static final Random random = new Random();
    private static String hostName = "localhost";

    static {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            //ignore this
        }
    }

    public GoogleAnalytics_v1_URLBuildingStrategy(String googleAnalyticsTrackingCode) {
        this.googleAnalyticsTrackingCode = googleAnalyticsTrackingCode;
    }

    @Override
    public String buildURL(FocusPoint focusPoint) {

        final int cookie = random.nextInt();
        final int randomValue = random.nextInt(2147483647) - 1;
        final long now = new Date().getTime();

        StringBuilder url = new StringBuilder(TRACKING_URL_Prefix);
        url.append("?utmwv=1"); //Urchin/Analytics version
        url.append("&utmn=").append(random.nextInt());
        url.append("&utmcs=UTF-8"); //document encoding
        url.append("&utmsr=1440x900"); //screen resolution
        url.append("&utmsc=32-bit"); //color depth
        url.append("&utmul=en-us"); //user language
        url.append("&utmje=1"); //java enabled
        url.append("&utmfl=9.0%20%20r28"); //flash
        url.append("&utmcr=1"); //carriage return
        url.append("&utmdt=").append(focusPoint.getContentTitle()); //The optimum keyword density //document title
        url.append("&utmhn=").append(hostName);//document hostname
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
