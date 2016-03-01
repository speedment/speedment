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
import com.speedment.internal.util.sql.SqlTypeInfo;
import java.util.Collections;
import static java.util.stream.Collectors.collectingAndThen;

/**
 * Created by fdirlikl on 11/13/2015.
 * 
 * @author  Fatih Dirlikli
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

        private final static String 
            ENCLOSER = "\"",
            QUOTE    = "'";
        
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
            return result.toString();
        }
    }

    private static final Set<SqlTypeInfo> dataTypes() {

        return Stream.of(
            new SqlTypeInfo("bool", -7, 0, 0, (short) 1, true),
            new SqlTypeInfo("bytea", -2, 0, 0, (short) 1, true),
            new SqlTypeInfo("char", 1, 0, 0, (short) 1, true),
            new SqlTypeInfo("name", 12, 0, 0, (short) 1, true),
            new SqlTypeInfo("int8", -5, 0, 0, (short) 1, false),
            new SqlTypeInfo("bigserial", -5, 0, 0, (short) 1, false),
            new SqlTypeInfo("int2", 5, 0, 0, (short) 1, false),
            new SqlTypeInfo("int2vector", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("int4", 4, 0, 0, (short) 1, false),
            new SqlTypeInfo("serial", 4, 0, 0, (short) 1, false),
            new SqlTypeInfo("regproc", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("text", 12, 0, 0, (short) 1, true),
            new SqlTypeInfo("oid", -5, 0, 0, (short) 1, true),
            new SqlTypeInfo("tid", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("xid", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("cid", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("oidvector", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_type", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_attribute", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_proc", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_class", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("json", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("xml", 2009, 0, 0, (short) 1, true),
            new SqlTypeInfo("_xml", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_json", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_node_tree", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("smgr", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("point", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("lseg", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("path", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("box", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("polygon", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("line", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_line", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("float4", 7, 0, 0, (short) 1, false),
            new SqlTypeInfo("float8", 8, 0, 0, (short) 1, false),
            new SqlTypeInfo("abstime", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("reltime", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("tinterval", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("unknown", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("circle", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_circle", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("money", 8, 0, 0, (short) 1, true),
            new SqlTypeInfo("_money", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("macaddr", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("inet", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("cidr", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_bool", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_bytea", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_char", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_name", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_int2", 2003, 0, 0, (short) 1, false),
            new SqlTypeInfo("_int2vector", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_int4", 2003, 0, 0, (short) 1, false),
            new SqlTypeInfo("_regproc", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_text", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_oid", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tid", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_xid", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_cid", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_oidvector", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_bpchar", 2003, 10485760, 10485760, (short) 1, true),
            new SqlTypeInfo("_varchar", 2003, 10485760, 10485760, (short) 1, true),
            new SqlTypeInfo("_int8", 2003, 0, 0, (short) 1, false),
            new SqlTypeInfo("_point", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_lseg", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_path", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_box", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_float4", 2003, 0, 0, (short) 1, false),
            new SqlTypeInfo("_float8", 2003, 0, 0, (short) 1, false),
            new SqlTypeInfo("_abstime", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_reltime", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tinterval", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_polygon", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("aclitem", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_aclitem", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_macaddr", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_inet", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_cidr", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_cstring", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("bpchar", 1, 10485760, 10485760, (short) 1, true),
            new SqlTypeInfo("varchar", 12, 10485760, 10485760, (short) 1, true),
            new SqlTypeInfo("date", 91, 0, 0, (short) 1, true),
            new SqlTypeInfo("time", 92, 6, 6, (short) 1, true),
            new SqlTypeInfo("timestamp", 93, 6, 6, (short) 1, true),
            new SqlTypeInfo("_timestamp", 2003, 6, 6, (short) 1, true),
            new SqlTypeInfo("_date", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_time", 2003, 6, 6, (short) 1, true),
            new SqlTypeInfo("timestamptz", 93, 6, 6, (short) 1, true),
            new SqlTypeInfo("_timestamptz", 2003, 6, 6, (short) 1, true),
            new SqlTypeInfo("interval", 1111, 6, 6, (short) 1, true),
            new SqlTypeInfo("_interval", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_numeric", 2003, 1000, 1000, (short) 1, false),
            new SqlTypeInfo("timetz", 92, 6, 6, (short) 1, true),
            new SqlTypeInfo("_timetz", 2003, 6, 6, (short) 1, true),
            new SqlTypeInfo("bit", -7, 83886080, 83886080, (short) 1, true),
            new SqlTypeInfo("_bit", 2003, 83886080, 83886080, (short) 1, true),
            new SqlTypeInfo("varbit", 1111, 83886080, 83886080, (short) 1, true),
            new SqlTypeInfo("_varbit", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("numeric", 2, 1000, 1000, (short) 1, false),
            new SqlTypeInfo("refcursor", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_refcursor", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("regprocedure", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regoper", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regoperator", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regclass", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regtype", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regprocedure", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regoper", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regoperator", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regclass", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regtype", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("uuid", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_uuid", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_lsn", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_lsn", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("tsvector", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("gtsvector", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("tsquery", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regconfig", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("regdictionary", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tsvector", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_gtsvector", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tsquery", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regconfig", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("_regdictionary", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("jsonb", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_jsonb", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("txid_snapshot", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_txid_snapshot", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("int4range", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_int4range", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("numrange", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_numrange", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("tsrange", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tsrange", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("tstzrange", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_tstzrange", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("daterange", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_daterange", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("int8range", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_int8range", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("record", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("_record", 2003, 0, 0, (short) 1, true),
            new SqlTypeInfo("cstring", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("any", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("anyarray", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("void", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("trigger", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("event_trigger", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("language_handler", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("internal", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("opaque", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("anyelement", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("anynonarray", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("anyenum", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("fdw_handler", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("anyrange", 1111, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_attrdef", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_constraint", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_inherits", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_index", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_operator", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_opfamily", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_opclass", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_am", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_amop", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_amproc", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_language", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_largeobject_metadata", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_largeobject", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_aggregate", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statistic", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_rewrite", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_trigger", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_event_trigger", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_description", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_cast", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_enum", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_namespace", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_conversion", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_depend", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_database", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_db_role_setting", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_tablespace", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_pltemplate", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_authid", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_auth_members", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_shdepend", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_shdescription", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_ts_config", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_ts_config_map", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_ts_dict", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_ts_parser", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_ts_template", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_extension", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_foreign_data_wrapper", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_foreign_server", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_user_mapping", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_foreign_table", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_default_acl", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_seclabel", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_shseclabel", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_collation", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_range", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_roles", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_shadow", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_group", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_user", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_rules", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_views", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_matviews", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stats", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_locks", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_cursors", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_available_extensions", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_available_extension_versions", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_prepared_xacts", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_prepared_statements", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_seclabels", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_settings", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_timezone_abbrevs", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_timezone_names", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_all_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_xact_all_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_sys_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_xact_sys_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_user_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_xact_user_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_all_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_sys_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_user_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_all_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_sys_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_user_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_all_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_sys_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_user_indexes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_all_sequences", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_sys_sequences", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_statio_user_sequences", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_activity", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_replication", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_replication_slots", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_database", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_database_conflicts", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_user_functions", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_xact_user_functions", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_archiver", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_stat_bgwriter", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("pg_user_mappings", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("cardinal_number", 2001, 0, 0, (short) 1, true),
            new SqlTypeInfo("character_data", 2001, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_identifier", 2001, 0, 0, (short) 1, true),
            new SqlTypeInfo("information_schema_catalog_name", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("time_stamp", 2001, 0, 0, (short) 1, true),
            new SqlTypeInfo("yes_or_no", 2001, 0, 0, (short) 1, true),
            new SqlTypeInfo("applicable_roles", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("administrable_role_authorizations", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("attributes", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("character_sets", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("check_constraint_routine_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("check_constraints", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("collations", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("collation_character_set_applicability", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("column_domain_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("column_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("column_udt_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("columns", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("constraint_column_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("constraint_table_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("domain_constraints", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("domain_udt_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("domains", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("enabled_roles", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("key_column_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("parameters", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("referential_constraints", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("role_column_grants", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("routine_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("role_routine_grants", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("routines", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("schemata", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sequences", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_features", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_implementation_info", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_languages", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_packages", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_parts", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_sizing", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("sql_sizing_profiles", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("table_constraints", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("table_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("role_table_grants", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("triggered_update_columns", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("triggers", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("udt_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("role_udt_grants", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("usage_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("role_usage_grants", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("user_defined_types", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("view_column_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("view_routine_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("view_table_usage", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("views", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("data_type_privileges", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("element_types", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_foreign_table_columns", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("column_options", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_data_wrapper_options", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_data_wrappers", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_foreign_servers", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_server_options", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_servers", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_foreign_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_table_options", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("foreign_tables", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("_pg_user_mappings", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("user_mapping_options", 2002, 0, 0, (short) 1, true),
            new SqlTypeInfo("user_mappings", 2002, 0, 0, (short) 1, true)
        ).collect(toSet());
    }
}
