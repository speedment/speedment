/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.config.dbms;

import com.speedment.config.db.Dbms;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.Stream;

import com.speedment.config.db.parameters.DbmsType;
import com.speedment.db.ConnectionUrlGenerator;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.internal.core.db.AbstractDatabaseNamingConvention;
import com.speedment.internal.core.db.PostgresDbmsHandler;
import com.speedment.internal.core.manager.sql.PostgresSpeedmentPredicateView;
import com.speedment.util.sql.SqlTypeInfo;
import java.util.Collections;
import static java.util.stream.Collectors.collectingAndThen;

/**
 * Created by fdirlikl on 11/13/2015.
 *
 * @author Fatih Dirlikli
 */
public final class PostgresDbmsType {

    private final static DatabaseNamingConvention NAMER = new PostgresNamingConvention();

    public static final DbmsType INSTANCE = DbmsType.builder()
            // Mandatory parameters
            .withName("PostgreSQL")
            .withDriverManagerName("PostgreSQL JDBC Driver")
            .withDefaultPort(5432)
            .withDbmsNameMeaning("Database name")
            .withDriverName("org.postgresql.Driver")
            .withDatabaseNamingConvention(NAMER)
            .withDbmsMapper(PostgresDbmsHandler::new)
            .withConnectionUrlGenerator(new PostgresConnectionUrlGenerator())
            .withSpeedmentPredicateView(new PostgresSpeedmentPredicateView(NAMER))
            // Optional parameters
            .withInitialQuery("select version() as \"PostgreSQL version\"")
            .withResultSetTableSchema("TABLE_SCHEM")
            .withDataTypes(dataTypes())
            .build();

    private final static class PostgresNamingConvention extends AbstractDatabaseNamingConvention {

        private final static String ENCLOSER = "\"",
                QUOTE = "'";

        private final static Set<String> EXCLUDE_SET = Stream.of(
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

    private final static class PostgresConnectionUrlGenerator implements ConnectionUrlGenerator {

        @Override
        public String from(Dbms dbms) {
            final StringBuilder result = new StringBuilder()
                    .append("jdbc:postgresql://")
                    .append(dbms.getIpAddress().orElse(""));

            dbms.getPort().ifPresent(p -> result.append(":").append(p));
            result.append("/").append(dbms.getName());
            return result.toString();
        }
    }

    private static final Set<SqlTypeInfo> dataTypes() {

        return Stream.of(
                SqlTypeInfo.of("bool", -7, 0, 0, (short) 1, true),
                SqlTypeInfo.of("bytea", -2, 0, 0, (short) 1, true),
                SqlTypeInfo.of("char", 1, 0, 0, (short) 1, true),
                SqlTypeInfo.of("name", 12, 0, 0, (short) 1, true),
                SqlTypeInfo.of("int8", -5, 0, 0, (short) 1, false),
                SqlTypeInfo.of("bigserial", -5, 0, 0, (short) 1, false),
                SqlTypeInfo.of("int2", 5, 0, 0, (short) 1, false),
                SqlTypeInfo.of("int2vector", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("int4", 4, 0, 0, (short) 1, false),
                SqlTypeInfo.of("serial", 4, 0, 0, (short) 1, false),
                SqlTypeInfo.of("regproc", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("text", 12, 0, 0, (short) 1, true),
                SqlTypeInfo.of("oid", -5, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tid", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("xid", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("cid", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("oidvector", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_type", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_attribute", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_proc", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_class", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("json", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("xml", 2009, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_xml", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_json", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_node_tree", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("smgr", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("point", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("lseg", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("path", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("box", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("polygon", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("line", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_line", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("float4", 7, 0, 0, (short) 1, false),
                SqlTypeInfo.of("float8", 8, 0, 0, (short) 1, false),
                SqlTypeInfo.of("abstime", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("reltime", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tinterval", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("unknown", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("circle", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_circle", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("money", 8, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_money", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("macaddr", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("inet", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("cidr", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_bool", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_bytea", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_char", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_name", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_int2", 2003, 0, 0, (short) 1, false),
                SqlTypeInfo.of("_int2vector", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_int4", 2003, 0, 0, (short) 1, false),
                SqlTypeInfo.of("_regproc", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_text", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_oid", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tid", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_xid", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_cid", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_oidvector", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_bpchar", 2003, 10485760, 10485760, (short) 1, true),
                SqlTypeInfo.of("_varchar", 2003, 10485760, 10485760, (short) 1, true),
                SqlTypeInfo.of("_int8", 2003, 0, 0, (short) 1, false),
                SqlTypeInfo.of("_point", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_lseg", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_path", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_box", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_float4", 2003, 0, 0, (short) 1, false),
                SqlTypeInfo.of("_float8", 2003, 0, 0, (short) 1, false),
                SqlTypeInfo.of("_abstime", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_reltime", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tinterval", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_polygon", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("aclitem", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_aclitem", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_macaddr", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_inet", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_cidr", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_cstring", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("bpchar", 1, 10485760, 10485760, (short) 1, true),
                SqlTypeInfo.of("varchar", 12, 10485760, 10485760, (short) 1, true),
                SqlTypeInfo.of("date", 91, 0, 0, (short) 1, true),
                SqlTypeInfo.of("time", 92, 6, 6, (short) 1, true),
                SqlTypeInfo.of("timestamp", 93, 6, 6, (short) 1, true),
                SqlTypeInfo.of("_timestamp", 2003, 6, 6, (short) 1, true),
                SqlTypeInfo.of("_date", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_time", 2003, 6, 6, (short) 1, true),
                SqlTypeInfo.of("timestamptz", 93, 6, 6, (short) 1, true),
                SqlTypeInfo.of("_timestamptz", 2003, 6, 6, (short) 1, true),
                SqlTypeInfo.of("interval", 1111, 6, 6, (short) 1, true),
                SqlTypeInfo.of("_interval", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_numeric", 2003, 1000, 1000, (short) 1, false),
                SqlTypeInfo.of("timetz", 92, 6, 6, (short) 1, true),
                SqlTypeInfo.of("_timetz", 2003, 6, 6, (short) 1, true),
                SqlTypeInfo.of("bit", -7, 83886080, 83886080, (short) 1, true),
                SqlTypeInfo.of("_bit", 2003, 83886080, 83886080, (short) 1, true),
                SqlTypeInfo.of("varbit", 1111, 83886080, 83886080, (short) 1, true),
                SqlTypeInfo.of("_varbit", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("numeric", 2, 1000, 1000, (short) 1, false),
                SqlTypeInfo.of("refcursor", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_refcursor", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regprocedure", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regoper", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regoperator", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regclass", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regtype", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regprocedure", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regoper", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regoperator", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regclass", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regtype", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("uuid", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_uuid", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_lsn", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_lsn", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tsvector", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("gtsvector", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tsquery", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regconfig", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("regdictionary", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tsvector", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_gtsvector", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tsquery", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regconfig", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_regdictionary", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("jsonb", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_jsonb", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("txid_snapshot", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_txid_snapshot", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("int4range", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_int4range", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("numrange", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_numrange", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tsrange", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tsrange", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tstzrange", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_tstzrange", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("daterange", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_daterange", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("int8range", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_int8range", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("record", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_record", 2003, 0, 0, (short) 1, true),
                SqlTypeInfo.of("cstring", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("any", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("anyarray", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("void", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("trigger", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("event_trigger", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("language_handler", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("internal", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("opaque", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("anyelement", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("anynonarray", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("anyenum", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("fdw_handler", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("anyrange", 1111, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_attrdef", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_constraint", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_inherits", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_index", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_operator", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_opfamily", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_opclass", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_am", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_amop", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_amproc", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_language", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_largeobject_metadata", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_largeobject", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_aggregate", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statistic", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_rewrite", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_trigger", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_event_trigger", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_description", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_cast", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_enum", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_namespace", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_conversion", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_depend", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_database", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_db_role_setting", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_tablespace", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_pltemplate", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_authid", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_auth_members", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_shdepend", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_shdescription", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_ts_config", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_ts_config_map", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_ts_dict", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_ts_parser", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_ts_template", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_extension", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_foreign_data_wrapper", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_foreign_server", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_user_mapping", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_foreign_table", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_default_acl", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_seclabel", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_shseclabel", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_collation", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_range", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_roles", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_shadow", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_group", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_user", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_rules", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_views", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_matviews", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stats", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_locks", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_cursors", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_available_extensions", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_available_extension_versions", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_prepared_xacts", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_prepared_statements", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_seclabels", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_settings", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_timezone_abbrevs", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_timezone_names", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_all_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_xact_all_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_sys_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_xact_sys_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_user_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_xact_user_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_all_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_sys_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_user_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_all_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_sys_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_user_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_all_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_sys_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_user_indexes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_all_sequences", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_sys_sequences", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_statio_user_sequences", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_activity", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_replication", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_replication_slots", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_database", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_database_conflicts", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_user_functions", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_xact_user_functions", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_archiver", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_stat_bgwriter", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("pg_user_mappings", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("cardinal_number", 2001, 0, 0, (short) 1, true),
                SqlTypeInfo.of("character_data", 2001, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_identifier", 2001, 0, 0, (short) 1, true),
                SqlTypeInfo.of("information_schema_catalog_name", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("time_stamp", 2001, 0, 0, (short) 1, true),
                SqlTypeInfo.of("yes_or_no", 2001, 0, 0, (short) 1, true),
                SqlTypeInfo.of("applicable_roles", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("administrable_role_authorizations", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("attributes", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("character_sets", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("check_constraint_routine_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("check_constraints", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("collations", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("collation_character_set_applicability", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("column_domain_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("column_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("column_udt_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("columns", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("constraint_column_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("constraint_table_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("domain_constraints", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("domain_udt_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("domains", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("enabled_roles", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("key_column_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("parameters", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("referential_constraints", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("role_column_grants", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("routine_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("role_routine_grants", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("routines", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("schemata", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sequences", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_features", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_implementation_info", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_languages", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_packages", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_parts", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_sizing", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("sql_sizing_profiles", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("table_constraints", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("table_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("role_table_grants", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("triggered_update_columns", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("triggers", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("udt_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("role_udt_grants", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("usage_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("role_usage_grants", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("user_defined_types", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("view_column_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("view_routine_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("view_table_usage", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("views", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("data_type_privileges", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("element_types", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_foreign_table_columns", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("column_options", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_data_wrapper_options", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_foreign_servers", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_server_options", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_servers", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_foreign_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_table_options", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("foreign_tables", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("_pg_user_mappings", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("user_mapping_options", 2002, 0, 0, (short) 1, true),
                SqlTypeInfo.of("user_mappings", 2002, 0, 0, (short) 1, true)
        ).collect(toSet());
    }
}
