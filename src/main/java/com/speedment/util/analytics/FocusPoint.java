package com.speedment.util.analytics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

public class FocusPoint {

    public static final Optional<String> version = Optional.ofNullable(FocusPoint.class.getPackage().getImplementationVersion());
    public static final Optional<String> title = Optional.ofNullable(FocusPoint.class.getPackage().getImplementationTitle());

    public static final FocusPoint CORE = new FocusPoint(title.orElse("speedment"));
    public static final FocusPoint CORE_WITH_VERSION = new FocusPoint(version.orElse("?.?.?"), CORE);

    public static final FocusPoint PROJECT_MANAGER = new FocusPoint("ProjectManager", CORE_WITH_VERSION);
    public static final FocusPoint PROJECT_MANAGER_INITIATED = new FocusPoint("Initiated", PROJECT_MANAGER);
    public static final FocusPoint PROJECT = new FocusPoint("Project", CORE_WITH_VERSION);
    public static final FocusPoint GENERATE = new FocusPoint("Generate", CORE_WITH_VERSION);
    public static final FocusPoint GENERATE_GUI_STARTED = new FocusPoint("GuiStarted", GENERATE);
    public static final FocusPoint GENERATE_CODE_GENERATED = new FocusPoint("CodeGenerated", GENERATE);

    private final String name;
    private final FocusPoint parentFocusPoint;
    private static final String URI_SEPARATOR = "/";
    private static final String TITLE_SEPARATOR = "-";

    public FocusPoint(String name) {
        this(name, null);
    }

    public FocusPoint(String name, FocusPoint parentFocusPoint) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        this.name = name;
        this.parentFocusPoint = parentFocusPoint;
    }

    public String getName() {
        return name;
    }

    public FocusPoint getParentFocusPoint() {
        return parentFocusPoint;
    }

    public String getContentURI() {
        final StringBuilder contentURIBuffer = new StringBuilder();
        getContentURI(contentURIBuffer, this);
        return contentURIBuffer.toString();
    }

    public String getContentTitle() {
        final StringBuilder titleBuffer = new StringBuilder();
        getContentTitle(titleBuffer, this);
        return titleBuffer.toString();
    }

    private void getContentURI(StringBuilder contentURIBuffer, FocusPoint focusPoint) {
        final FocusPoint parentFP = focusPoint.getParentFocusPoint();
        if (parentFP != null) {
            getContentURI(contentURIBuffer, parentFP);
        }
        contentURIBuffer.append(URI_SEPARATOR);
        contentURIBuffer.append(encode(focusPoint.getName()));
    }

    private String encode(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return name;
        }
    }

    private void getContentTitle(StringBuilder titleBuffer, FocusPoint focusPoint) {
        final FocusPoint parentFP = focusPoint.getParentFocusPoint();
        if (parentFP != null) {
            getContentTitle(titleBuffer, parentFP);
            titleBuffer.append(TITLE_SEPARATOR);
        }
        titleBuffer.append(encode(focusPoint.getName()));
    }
}
