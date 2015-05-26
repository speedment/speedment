package com.speedment.util.analytics;

/**
 *
 * @author pemi
 */
public class AnalyticsUtil {
    
    private static final String TRACKING_CODE = "UA-54384165-1";
    
    private AnalyticsUtil() {
    }
    
    public static void notify(final FocusPoint focusPoint) {
        final JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(TRACKING_CODE);
        tracker.setLoggingAdapter(new LoggingAdapterImpl());
        tracker.trackAsynchronously(focusPoint);
    }
    
    public static void notify(final String focusPointName) {
        notify(new FocusPoint(focusPointName));
    }
    
}
