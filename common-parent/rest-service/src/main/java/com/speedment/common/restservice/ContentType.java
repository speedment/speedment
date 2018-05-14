package com.speedment.common.restservice;

/**
 * Utility class used to get correctly spelled content-types.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class ContentType {

    public final static String TEXT_HTML              = "text/html";
    public final static String TEXT_PLAIN             = "text/plain";
    public final static String TEXT_EVENT_STREAM      = "text/event-stream";
    public final static String APPLICATION_JSON       = "application/json";
    public final static String APPLICATION_JAVASCRIPT = "application/javascript";

    /**
     * Should not be instantiated.
     */
    private ContentType() {}
}
