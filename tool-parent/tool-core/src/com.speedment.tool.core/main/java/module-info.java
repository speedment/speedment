module com.speedment.tool.core {
    exports com.speedment.tool.core.resource;
    exports com.speedment.tool.core.exception;
    exports com.speedment.tool.core.component;
    requires com.speedment.common.invariant;
    requires com.speedment.runtime.typemapper;
    requires com.speedment.common.rest;
    requires com.speedment.common.function;
    requires com.speedment.common.mapstream;
    requires java.prefs;
    requires com.speedment.generator.core;
    requires com.speedment.generator.translator;
    requires com.speedment.common.logger;
    requires com.speedment.common.json;
    requires javafx.base;
    requires com.speedment.tool.propertyeditor;
    requires com.speedment.tool.config;
    requires com.speedment.runtime.core;
    requires com.speedment.runtime.config;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.speedment.common.injector;
}
