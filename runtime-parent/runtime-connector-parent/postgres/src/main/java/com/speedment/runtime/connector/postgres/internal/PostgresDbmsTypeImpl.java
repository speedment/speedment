/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.connector.postgres.internal;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.core.abstracts.AbstractDatabaseNamingConvention;

import java.sql.Driver;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.speedment.runtime.core.db.metadata.TypeInfoMetaData.of;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

/**
 * Created by fdirlikl on 11/13/2015.
 *
 * @author Fatih Dirlikli
 * @author Per Minborg
 * @author Emil Forslund
 */
public final class PostgresDbmsTypeImpl implements DbmsType {

    private static final FieldPredicateView PREDICATE_VIEW = new PostgresSpeedmentPredicateView();

    private final DbmsTypeDefault support;
    private final DriverComponent driverComponent;
    private final PostgresDbmsMetadataHandler metadataHandler;
    private final PostgresDbmsOperationHandler operationHandler;
    private final PostgresNamingConvention namingConvention;
    private final PostgresConnectionUrlGenerator connectionUrlGenerator;

    public PostgresDbmsTypeImpl(
        final DriverComponent driverComponent,
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ProjectComponent projectComponent,
        final TransactionComponent transactionComponent
    ) {
        this.driverComponent = requireNonNull(driverComponent);
        this.metadataHandler = new PostgresDbmsMetadataHandler(connectionPoolComponent, dbmsHandlerComponent, projectComponent);
        this.operationHandler = new PostgresDbmsOperationHandler(connectionPoolComponent, transactionComponent);
        this.namingConvention = new PostgresNamingConvention();
        this.connectionUrlGenerator = new PostgresConnectionUrlGenerator();
        this.support = DbmsTypeDefault.create();
    }

    @ExecuteBefore(State.STOPPED)
    public void close() {
        operationHandler.close();
    }

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public String getDriverManagerName() {
        return "PostgreSQL JDBC Driver";
    }

    @Override
    public int getDefaultPort() {
        return 5432;
    }

    @Override
    public Optional<String> getDefaultSchemaName() {
        return Optional.of("public");
    }

    @Override
    public String getDbmsNameMeaning() {
        return "The name of the PostgreSQL database to connect to.";
    }

    @Override
    public String getDriverName() {
        return "org.postgresql.Driver";
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return namingConvention;
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        return metadataHandler;
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        return operationHandler;
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return connectionUrlGenerator;
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return PREDICATE_VIEW;
    }

    @Override
    public String getInitialQuery() {
        return "select version() as \"PostgreSQL version\"";
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return dataTypes().collect(toSet());
    }

    @Override
    public boolean isSupported() {
        return isSupported(getDriverName());
    }

    private static final class PostgresNamingConvention extends AbstractDatabaseNamingConvention {

        private static final String ENCLOSER = "\"";
        private static final String QUOTE = "'";

        private static final Set<String> EXCLUDE_SET = Stream.of(
            "pg_catalog",
            "information_schema"
        ).collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

        @Override
        public Set<String> getSchemaExcludeSet() {
            return EXCLUDE_SET;
        }

        @Override
        protected String getFieldQuoteStart() {
            return QUOTE;
        }

        @Override
        protected String getFieldQuoteEnd() {
            return QUOTE;
        }

        @Override
        protected String getFieldEncloserStart() {
            return ENCLOSER;
        }

        @Override
        protected String getFieldEncloserEnd() {
            return ENCLOSER;
        }
    }

    @Override
    public SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return SortByNullOrderInsertion.POST;
    }

    private static final class PostgresConnectionUrlGenerator implements ConnectionUrlGenerator {

        @Override
        public String from(Dbms dbms) {
            final StringBuilder result = new StringBuilder()
                .append("jdbc:postgresql://")
                .append(dbms.getIpAddress().orElse(""));

            dbms.getPort().ifPresent(p -> result.append(":").append(p));
            result.append("/").append(dbms.getName());
            result.append("?stringtype=unspecified");  // to allow database JSON types to be written
            return result.toString();
        }
    }


    @Override
    public DbmsColumnHandler getColumnHandler() {
        return support.getColumnHandler();
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return support.getDefaultDbmsName();
    }

    @Override
    public String getSchemaTableDelimiter() {
        return support.getSchemaTableDelimiter();
    }

    @Override
    public boolean hasDatabaseNames() {
        return support.hasDatabaseNames();
    }

    @Override
    public boolean hasDatabaseUsers() {
        return support.hasDatabaseUsers();
    }

    @Override
    public ConnectionType getConnectionType() {
        return support.getConnectionType();
    }

    @Override
    public String getResultSetTableSchema() {
        return support.getResultSetTableSchema();
    }

    @Override
    public SkipLimitSupport getSkipLimitSupport() {
        return support.getSkipLimitSupport();
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        return support.applySkipLimit(originalSql, params, skip, limit);
    }

    @Override
    public SubSelectAlias getSubSelectAlias() {
        return support.getSubSelectAlias();
    }

    private boolean isSupported(String driverName) {
        return driver(driverName).isPresent();
    }

    private Optional<Driver> driver(String driverName) {
        return driverComponent.driver(driverName);
    }

    private static Stream<TypeInfoMetaData> dataTypes() {

        return Stream.of(
            of("bool", -7, 0, 0, (short) 1, true),
            of("bytea", -2, 0, 0, (short) 1, true),
            of("char", 1, 0, 0, (short) 1, true),
            of("name", 12, 0, 0, (short) 1, true),
            of("int8", -5, 0, 0, (short) 1, false),
            of("bigserial", -5, 0, 0, (short) 1, false),
            of("int2", 5, 0, 0, (short) 1, false),
            of("int2vector", 1111, 0, 0, (short) 1, true),
            of("int4", 4, 0, 0, (short) 1, false),
            of("serial", 4, 0, 0, (short) 1, false),
            of("regproc", 1111, 0, 0, (short) 1, true),
            of("text", 12, 0, 0, (short) 1, true),
            of("oid", -5, 0, 0, (short) 1, true),
            of("tid", 1111, 0, 0, (short) 1, true),
            of("xid", 1111, 0, 0, (short) 1, true),
            of("cid", 1111, 0, 0, (short) 1, true),
            of("oidvector", 1111, 0, 0, (short) 1, true),
            of("pg_type", 2002, 0, 0, (short) 1, true),
            of("pg_attribute", 2002, 0, 0, (short) 1, true),
            of("pg_proc", 2002, 0, 0, (short) 1, true),
            of("pg_class", 2002, 0, 0, (short) 1, true),
            of("json", 1111, 0, 0, (short) 1, true),
            of("xml", 2009, 0, 0, (short) 1, true),
            of("_xml", 2003, 0, 0, (short) 1, true),
            of("_json", 2003, 0, 0, (short) 1, true),
            of("pg_node_tree", 1111, 0, 0, (short) 1, true),
            of("smgr", 1111, 0, 0, (short) 1, true),
            of("point", 1111, 0, 0, (short) 1, true),
            of("lseg", 1111, 0, 0, (short) 1, true),
            of("path", 1111, 0, 0, (short) 1, true),
            of("box", 1111, 0, 0, (short) 1, true),
            of("polygon", 1111, 0, 0, (short) 1, true),
            of("line", 1111, 0, 0, (short) 1, true),
            of("_line", 2003, 0, 0, (short) 1, true),
            of("float4", 7, 0, 0, (short) 1, false),
            of("float8", 8, 0, 0, (short) 1, false),
            of("abstime", 1111, 0, 0, (short) 1, true),
            of("reltime", 1111, 0, 0, (short) 1, true),
            of("tinterval", 1111, 0, 0, (short) 1, true),
            of("unknown", 1111, 0, 0, (short) 1, true),
            of("circle", 1111, 0, 0, (short) 1, true),
            of("_circle", 2003, 0, 0, (short) 1, true),
            of("money", 8, 0, 0, (short) 1, true),
            of("_money", 2003, 0, 0, (short) 1, true),
            of("macaddr", 1111, 0, 0, (short) 1, true),
            of("inet", 1111, 0, 0, (short) 1, true),
            of("cidr", 1111, 0, 0, (short) 1, true),
            of("_bool", 2003, 0, 0, (short) 1, true),
            of("_bytea", 2003, 0, 0, (short) 1, true),
            of("_char", 2003, 0, 0, (short) 1, true),
            of("_name", 2003, 0, 0, (short) 1, true),
            of("_int2", 2003, 0, 0, (short) 1, false),
            of("_int2vector", 2003, 0, 0, (short) 1, true),
            of("_int4", 2003, 0, 0, (short) 1, false),
            of("_regproc", 2003, 0, 0, (short) 1, true),
            of("_text", 2003, 0, 0, (short) 1, true),
            of("_oid", 2003, 0, 0, (short) 1, true),
            of("_tid", 2003, 0, 0, (short) 1, true),
            of("_xid", 2003, 0, 0, (short) 1, true),
            of("_cid", 2003, 0, 0, (short) 1, true),
            of("_oidvector", 2003, 0, 0, (short) 1, true),
            of("_bpchar", 2003, 10485760, 10485760, (short) 1, true),
            of("_varchar", 2003, 10485760, 10485760, (short) 1, true),
            of("_int8", 2003, 0, 0, (short) 1, false),
            of("_point", 2003, 0, 0, (short) 1, true),
            of("_lseg", 2003, 0, 0, (short) 1, true),
            of("_path", 2003, 0, 0, (short) 1, true),
            of("_box", 2003, 0, 0, (short) 1, true),
            of("_float4", 2003, 0, 0, (short) 1, false),
            of("_float8", 2003, 0, 0, (short) 1, false),
            of("_abstime", 2003, 0, 0, (short) 1, true),
            of("_reltime", 2003, 0, 0, (short) 1, true),
            of("_tinterval", 2003, 0, 0, (short) 1, true),
            of("_polygon", 2003, 0, 0, (short) 1, true),
            of("aclitem", 1111, 0, 0, (short) 1, true),
            of("_aclitem", 2003, 0, 0, (short) 1, true),
            of("_macaddr", 2003, 0, 0, (short) 1, true),
            of("_inet", 2003, 0, 0, (short) 1, true),
            of("_cidr", 2003, 0, 0, (short) 1, true),
            of("_cstring", 2003, 0, 0, (short) 1, true),
            of("bpchar", 1, 10485760, 10485760, (short) 1, true),
            of("varchar", 12, 10485760, 10485760, (short) 1, true),
            of("date", 91, 0, 0, (short) 1, true),
            of("time", 92, 6, 6, (short) 1, true),
            of("timestamp", 93, 6, 6, (short) 1, true),
            of("_timestamp", 2003, 6, 6, (short) 1, true),
            of("_date", 2003, 0, 0, (short) 1, true),
            of("_time", 2003, 6, 6, (short) 1, true),
            of("timestamptz", 93, 6, 6, (short) 1, true),
            of("_timestamptz", 2003, 6, 6, (short) 1, true),
            of("interval", 1111, 6, 6, (short) 1, true),
            of("_interval", 2003, 0, 0, (short) 1, true),
            of("_numeric", 2003, 1000, 1000, (short) 1, false),
            of("timetz", 92, 6, 6, (short) 1, true),
            of("_timetz", 2003, 6, 6, (short) 1, true),
            of("bit", -7, 83886080, 83886080, (short) 1, true),
            of("_bit", 2003, 83886080, 83886080, (short) 1, true),
            of("varbit", 1111, 83886080, 83886080, (short) 1, true),
            of("_varbit", 2003, 0, 0, (short) 1, true),
            of("numeric", 2, 1000, 1000, (short) 1, false),
            of("refcursor", 1111, 0, 0, (short) 1, true),
            of("_refcursor", 2003, 0, 0, (short) 1, true),
            of("regprocedure", 1111, 0, 0, (short) 1, true),
            of("regoper", 1111, 0, 0, (short) 1, true),
            of("regoperator", 1111, 0, 0, (short) 1, true),
            of("regclass", 1111, 0, 0, (short) 1, true),
            of("regtype", 1111, 0, 0, (short) 1, true),
            of("_regprocedure", 2003, 0, 0, (short) 1, true),
            of("_regoper", 2003, 0, 0, (short) 1, true),
            of("_regoperator", 2003, 0, 0, (short) 1, true),
            of("_regclass", 2003, 0, 0, (short) 1, true),
            of("_regtype", 2003, 0, 0, (short) 1, true),
            of("uuid", 1111, 0, 0, (short) 1, true),
            of("_uuid", 2003, 0, 0, (short) 1, true),
            of("pg_lsn", 1111, 0, 0, (short) 1, true),
            of("_pg_lsn", 2003, 0, 0, (short) 1, true),
            of("tsvector", 1111, 0, 0, (short) 1, true),
            of("gtsvector", 1111, 0, 0, (short) 1, true),
            of("tsquery", 1111, 0, 0, (short) 1, true),
            of("regconfig", 1111, 0, 0, (short) 1, true),
            of("regdictionary", 1111, 0, 0, (short) 1, true),
            of("_tsvector", 2003, 0, 0, (short) 1, true),
            of("_gtsvector", 2003, 0, 0, (short) 1, true),
            of("_tsquery", 2003, 0, 0, (short) 1, true),
            of("_regconfig", 2003, 0, 0, (short) 1, true),
            of("_regdictionary", 2003, 0, 0, (short) 1, true),
            of("jsonb", 1111, 0, 0, (short) 1, true),
            of("_jsonb", 2003, 0, 0, (short) 1, true),
            of("txid_snapshot", 1111, 0, 0, (short) 1, true),
            of("_txid_snapshot", 2003, 0, 0, (short) 1, true),
            of("int4range", 1111, 0, 0, (short) 1, true),
            of("_int4range", 2003, 0, 0, (short) 1, true),
            of("numrange", 1111, 0, 0, (short) 1, true),
            of("_numrange", 2003, 0, 0, (short) 1, true),
            of("tsrange", 1111, 0, 0, (short) 1, true),
            of("_tsrange", 2003, 0, 0, (short) 1, true),
            of("tstzrange", 1111, 0, 0, (short) 1, true),
            of("_tstzrange", 2003, 0, 0, (short) 1, true),
            of("daterange", 1111, 0, 0, (short) 1, true),
            of("_daterange", 2003, 0, 0, (short) 1, true),
            of("int8range", 1111, 0, 0, (short) 1, true),
            of("_int8range", 2003, 0, 0, (short) 1, true),
            of("record", 1111, 0, 0, (short) 1, true),
            of("_record", 2003, 0, 0, (short) 1, true),
            of("cstring", 1111, 0, 0, (short) 1, true),
            of("any", 1111, 0, 0, (short) 1, true),
            of("anyarray", 1111, 0, 0, (short) 1, true),
            of("void", 1111, 0, 0, (short) 1, true),
            of("trigger", 1111, 0, 0, (short) 1, true),
            of("event_trigger", 1111, 0, 0, (short) 1, true),
            of("language_handler", 1111, 0, 0, (short) 1, true),
            of("internal", 1111, 0, 0, (short) 1, true),
            of("opaque", 1111, 0, 0, (short) 1, true),
            of("anyelement", 1111, 0, 0, (short) 1, true),
            of("anynonarray", 1111, 0, 0, (short) 1, true),
            of("anyenum", 1111, 0, 0, (short) 1, true),
            of("fdw_handler", 1111, 0, 0, (short) 1, true),
            of("anyrange", 1111, 0, 0, (short) 1, true),
            of("pg_attrdef", 2002, 0, 0, (short) 1, true),
            of("pg_constraint", 2002, 0, 0, (short) 1, true),
            of("pg_inherits", 2002, 0, 0, (short) 1, true),
            of("pg_index", 2002, 0, 0, (short) 1, true),
            of("pg_operator", 2002, 0, 0, (short) 1, true),
            of("pg_opfamily", 2002, 0, 0, (short) 1, true),
            of("pg_opclass", 2002, 0, 0, (short) 1, true),
            of("pg_am", 2002, 0, 0, (short) 1, true),
            of("pg_amop", 2002, 0, 0, (short) 1, true),
            of("pg_amproc", 2002, 0, 0, (short) 1, true),
            of("pg_language", 2002, 0, 0, (short) 1, true),
            of("pg_largeobject_metadata", 2002, 0, 0, (short) 1, true),
            of("pg_largeobject", 2002, 0, 0, (short) 1, true),
            of("pg_aggregate", 2002, 0, 0, (short) 1, true),
            of("pg_statistic", 2002, 0, 0, (short) 1, true),
            of("pg_rewrite", 2002, 0, 0, (short) 1, true),
            of("pg_trigger", 2002, 0, 0, (short) 1, true),
            of("pg_event_trigger", 2002, 0, 0, (short) 1, true),
            of("pg_description", 2002, 0, 0, (short) 1, true),
            of("pg_cast", 2002, 0, 0, (short) 1, true),
            of("pg_enum", 2002, 0, 0, (short) 1, true),
            of("pg_namespace", 2002, 0, 0, (short) 1, true),
            of("pg_conversion", 2002, 0, 0, (short) 1, true),
            of("pg_depend", 2002, 0, 0, (short) 1, true),
            of("pg_database", 2002, 0, 0, (short) 1, true),
            of("pg_db_role_setting", 2002, 0, 0, (short) 1, true),
            of("pg_tablespace", 2002, 0, 0, (short) 1, true),
            of("pg_pltemplate", 2002, 0, 0, (short) 1, true),
            of("pg_authid", 2002, 0, 0, (short) 1, true),
            of("pg_auth_members", 2002, 0, 0, (short) 1, true),
            of("pg_shdepend", 2002, 0, 0, (short) 1, true),
            of("pg_shdescription", 2002, 0, 0, (short) 1, true),
            of("pg_ts_config", 2002, 0, 0, (short) 1, true),
            of("pg_ts_config_map", 2002, 0, 0, (short) 1, true),
            of("pg_ts_dict", 2002, 0, 0, (short) 1, true),
            of("pg_ts_parser", 2002, 0, 0, (short) 1, true),
            of("pg_ts_template", 2002, 0, 0, (short) 1, true),
            of("pg_extension", 2002, 0, 0, (short) 1, true),
            of("pg_foreign_data_wrapper", 2002, 0, 0, (short) 1, true),
            of("pg_foreign_server", 2002, 0, 0, (short) 1, true),
            of("pg_user_mapping", 2002, 0, 0, (short) 1, true),
            of("pg_foreign_table", 2002, 0, 0, (short) 1, true),
            of("pg_default_acl", 2002, 0, 0, (short) 1, true),
            of("pg_seclabel", 2002, 0, 0, (short) 1, true),
            of("pg_shseclabel", 2002, 0, 0, (short) 1, true),
            of("pg_collation", 2002, 0, 0, (short) 1, true),
            of("pg_range", 2002, 0, 0, (short) 1, true),
            of("pg_roles", 2002, 0, 0, (short) 1, true),
            of("pg_shadow", 2002, 0, 0, (short) 1, true),
            of("pg_group", 2002, 0, 0, (short) 1, true),
            of("pg_user", 2002, 0, 0, (short) 1, true),
            of("pg_rules", 2002, 0, 0, (short) 1, true),
            of("pg_views", 2002, 0, 0, (short) 1, true),
            of("pg_tables", 2002, 0, 0, (short) 1, true),
            of("pg_matviews", 2002, 0, 0, (short) 1, true),
            of("pg_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_stats", 2002, 0, 0, (short) 1, true),
            of("pg_locks", 2002, 0, 0, (short) 1, true),
            of("pg_cursors", 2002, 0, 0, (short) 1, true),
            of("pg_available_extensions", 2002, 0, 0, (short) 1, true),
            of("pg_available_extension_versions", 2002, 0, 0, (short) 1, true),
            of("pg_prepared_xacts", 2002, 0, 0, (short) 1, true),
            of("pg_prepared_statements", 2002, 0, 0, (short) 1, true),
            of("pg_seclabels", 2002, 0, 0, (short) 1, true),
            of("pg_settings", 2002, 0, 0, (short) 1, true),
            of("pg_timezone_abbrevs", 2002, 0, 0, (short) 1, true),
            of("pg_timezone_names", 2002, 0, 0, (short) 1, true),
            of("pg_stat_all_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_xact_all_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_sys_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_xact_sys_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_user_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_xact_user_tables", 2002, 0, 0, (short) 1, true),
            of("pg_statio_all_tables", 2002, 0, 0, (short) 1, true),
            of("pg_statio_sys_tables", 2002, 0, 0, (short) 1, true),
            of("pg_statio_user_tables", 2002, 0, 0, (short) 1, true),
            of("pg_stat_all_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_stat_sys_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_stat_user_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_statio_all_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_statio_sys_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_statio_user_indexes", 2002, 0, 0, (short) 1, true),
            of("pg_statio_all_sequences", 2002, 0, 0, (short) 1, true),
            of("pg_statio_sys_sequences", 2002, 0, 0, (short) 1, true),
            of("pg_statio_user_sequences", 2002, 0, 0, (short) 1, true),
            of("pg_stat_activity", 2002, 0, 0, (short) 1, true),
            of("pg_stat_replication", 2002, 0, 0, (short) 1, true),
            of("pg_replication_slots", 2002, 0, 0, (short) 1, true),
            of("pg_stat_database", 2002, 0, 0, (short) 1, true),
            of("pg_stat_database_conflicts", 2002, 0, 0, (short) 1, true),
            of("pg_stat_user_functions", 2002, 0, 0, (short) 1, true),
            of("pg_stat_xact_user_functions", 2002, 0, 0, (short) 1, true),
            of("pg_stat_archiver", 2002, 0, 0, (short) 1, true),
            of("pg_stat_bgwriter", 2002, 0, 0, (short) 1, true),
            of("pg_user_mappings", 2002, 0, 0, (short) 1, true),
            of("cardinal_number", 2001, 0, 0, (short) 1, true),
            of("character_data", 2001, 0, 0, (short) 1, true),
            of("sql_identifier", 2001, 0, 0, (short) 1, true),
            of("information_schema_catalog_name", 2002, 0, 0, (short) 1, true),
            of("time_stamp", 2001, 0, 0, (short) 1, true),
            of("yes_or_no", 2001, 0, 0, (short) 1, true),
            of("applicable_roles", 2002, 0, 0, (short) 1, true),
            of("administrable_role_authorizations", 2002, 0, 0, (short) 1, true),
            of("attributes", 2002, 0, 0, (short) 1, true),
            of("character_sets", 2002, 0, 0, (short) 1, true),
            of("check_constraint_routine_usage", 2002, 0, 0, (short) 1, true),
            of("check_constraints", 2002, 0, 0, (short) 1, true),
            of("collations", 2002, 0, 0, (short) 1, true),
            of("collation_character_set_applicability", 2002, 0, 0, (short) 1, true),
            of("column_domain_usage", 2002, 0, 0, (short) 1, true),
            of("column_privileges", 2002, 0, 0, (short) 1, true),
            of("column_udt_usage", 2002, 0, 0, (short) 1, true),
            of("columns", 2002, 0, 0, (short) 1, true),
            of("constraint_column_usage", 2002, 0, 0, (short) 1, true),
            of("constraint_table_usage", 2002, 0, 0, (short) 1, true),
            of("domain_constraints", 2002, 0, 0, (short) 1, true),
            of("domain_udt_usage", 2002, 0, 0, (short) 1, true),
            of("domains", 2002, 0, 0, (short) 1, true),
            of("enabled_roles", 2002, 0, 0, (short) 1, true),
            of("key_column_usage", 2002, 0, 0, (short) 1, true),
            of("parameters", 2002, 0, 0, (short) 1, true),
            of("referential_constraints", 2002, 0, 0, (short) 1, true),
            of("role_column_grants", 2002, 0, 0, (short) 1, true),
            of("routine_privileges", 2002, 0, 0, (short) 1, true),
            of("role_routine_grants", 2002, 0, 0, (short) 1, true),
            of("routines", 2002, 0, 0, (short) 1, true),
            of("schemata", 2002, 0, 0, (short) 1, true),
            of("sequences", 2002, 0, 0, (short) 1, true),
            of("sql_features", 2002, 0, 0, (short) 1, true),
            of("sql_implementation_info", 2002, 0, 0, (short) 1, true),
            of("sql_languages", 2002, 0, 0, (short) 1, true),
            of("sql_packages", 2002, 0, 0, (short) 1, true),
            of("sql_parts", 2002, 0, 0, (short) 1, true),
            of("sql_sizing", 2002, 0, 0, (short) 1, true),
            of("sql_sizing_profiles", 2002, 0, 0, (short) 1, true),
            of("table_constraints", 2002, 0, 0, (short) 1, true),
            of("table_privileges", 2002, 0, 0, (short) 1, true),
            of("role_table_grants", 2002, 0, 0, (short) 1, true),
            of("tables", 2002, 0, 0, (short) 1, true),
            of("triggered_update_columns", 2002, 0, 0, (short) 1, true),
            of("triggers", 2002, 0, 0, (short) 1, true),
            of("udt_privileges", 2002, 0, 0, (short) 1, true),
            of("role_udt_grants", 2002, 0, 0, (short) 1, true),
            of("usage_privileges", 2002, 0, 0, (short) 1, true),
            of("role_usage_grants", 2002, 0, 0, (short) 1, true),
            of("user_defined_types", 2002, 0, 0, (short) 1, true),
            of("view_column_usage", 2002, 0, 0, (short) 1, true),
            of("view_routine_usage", 2002, 0, 0, (short) 1, true),
            of("view_table_usage", 2002, 0, 0, (short) 1, true),
            of("views", 2002, 0, 0, (short) 1, true),
            of("data_type_privileges", 2002, 0, 0, (short) 1, true),
            of("element_types", 2002, 0, 0, (short) 1, true),
            of("_pg_foreign_table_columns", 2002, 0, 0, (short) 1, true),
            of("column_options", 2002, 0, 0, (short) 1, true),
            of("_pg_foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
            of("foreign_data_wrapper_options", 2002, 0, 0, (short) 1, true),
            of("foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
            of("_pg_foreign_servers", 2002, 0, 0, (short) 1, true),
            of("foreign_server_options", 2002, 0, 0, (short) 1, true),
            of("foreign_servers", 2002, 0, 0, (short) 1, true),
            of("_pg_foreign_tables", 2002, 0, 0, (short) 1, true),
            of("foreign_table_options", 2002, 0, 0, (short) 1, true),
            of("foreign_tables", 2002, 0, 0, (short) 1, true),
            of("_pg_user_mappings", 2002, 0, 0, (short) 1, true),
            of("user_mapping_options", 2002, 0, 0, (short) 1, true),
            of("user_mappings", 2002, 0, 0, (short) 1, true)
        );
    }
}
