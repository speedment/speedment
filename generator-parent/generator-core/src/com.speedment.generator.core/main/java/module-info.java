module com.speedment.generator.core {
    exports com.speedment.generator.core.component;
    exports com.speedment.generator.core.event;
    requires com.speedment.common.codegen;
    requires com.speedment.common.injector;
    requires com.speedment.common.logger;
    requires com.speedment.generator.standard;
    requires com.speedment.generator.translator;
    requires com.speedment.runtime.config;
    requires com.speedment.runtime.core;
}
