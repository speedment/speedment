package com.speedment.maven.exception;

public final class SpeedmentMavenPluginException extends RuntimeException {

    static final long serialVersionUID = -249582095209524923L;

    public SpeedmentMavenPluginException() {
    }

    public SpeedmentMavenPluginException(String message) {
        super(message);
    }

    public SpeedmentMavenPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentMavenPluginException(Throwable cause) {
        super(cause);
    }

    public SpeedmentMavenPluginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
