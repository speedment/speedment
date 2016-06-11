package com.speedment.tool.internal.notification;

import com.speedment.tool.brand.Palette;
import com.speedment.tool.notification.Notification;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class NotificationImpl implements Notification {

    private final String message;
    private final FontAwesomeIcon icon;
    private final Palette palette;
    private final Runnable onClose;

    public NotificationImpl(String message, FontAwesomeIcon icon, Palette palette, Runnable onClose) {
        this.message = requireNonNull(message);
        this.icon    = requireNonNull(icon);
        this.palette = requireNonNull(palette);
        this.onClose = requireNonNull(onClose);
    }

    @Override
    public String text() {
        return message;
    }

    @Override
    public FontAwesomeIcon icon() {
        return icon;
    }

    @Override
    public Palette palette() {
        return palette;
    }

    @Override
    public Runnable onClose() {
        return onClose;
    }
}