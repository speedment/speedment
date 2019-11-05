module basic.example {
/*    exports com.company.sakila.db0.sakila.staff.generated; // ????*/

    requires com.speedment.runtime.application;
    requires com.speedment.runtime.connector.mysql;
    requires com.speedment.runtime.join;
    requires custom.typemapper;
}