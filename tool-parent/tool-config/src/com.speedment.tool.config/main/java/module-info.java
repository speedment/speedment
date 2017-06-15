module com.speedment.tool.config {
    exports com.speedment.tool.config.trait;
    exports com.speedment.tool.config;
    requires com.speedment.common.function;
    requires com.speedment.common.injector;
    requires com.speedment.common.mapstream;
    requires com.speedment.generator.translator;
    requires com.speedment.runtime.config;
    requires com.speedment.runtime.core;
    requires javafx.base;
}
