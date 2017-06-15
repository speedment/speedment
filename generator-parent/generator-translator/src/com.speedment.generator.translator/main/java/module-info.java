module com.speedment.generator.translator {
    exports com.speedment.generator.translator.internal.namer;
    exports com.speedment.generator.translator.namer;
    exports com.speedment.generator.translator.exception;
    exports com.speedment.generator.translator.component;
    exports com.speedment.generator.translator;
    requires java.sql;
    requires com.speedment.runtime.typemapper;
    requires com.speedment.common.mapstream;
    requires com.speedment.common.annotation;
    requires com.speedment.common.injector;
    requires com.speedment.runtime.core;
    requires com.speedment.runtime.config;
    requires com.speedment.common.codegen;
}
