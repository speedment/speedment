package com.speedment.common.injector.internal.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

final class UrlUtilTest {

    private static final String URL_STRING = "http://www,speedment.com";

    @Test
    void tryCreateURL() {
        final Object object = UrlUtil.tryCreateURL(URL_STRING);
        if (object instanceof URL) {
            final URL url = (URL) object;
            assertEquals(URL_STRING, url.toString());
        } else {
            fail(object.getClass().getName() + " does not extend " + URL.class.getName());
        }
    }
}