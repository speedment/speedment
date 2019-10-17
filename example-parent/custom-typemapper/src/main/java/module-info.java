module custom.typemapper {
    exports com.speedment.example.typemapper;

    requires transitive com.speedment.common.injector;
    requires transitive com.speedment.generator.translator;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.runtime.typemapper;
}
