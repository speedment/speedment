package com.speedment.tool.core.menubar;

/**
 * Enumeration of all the tabs visible in the Speedment tool menu bar.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public enum MenuBarTab {

    FILE("_File"),
    EDIT("_Edit"),
    WINDOW("_Window"),
    HELP("_Help");

    private final String text;

    MenuBarTab(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}