package com.speedment.util.analytics;

public interface URLBuildingStrategy {

    public String buildURL(FocusPoint focusPoint);

    public void setRefererURL(String refererURL);
}
