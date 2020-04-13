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

package com.speedment.runtime.config;

import static com.speedment.runtime.config.ProjectUtil.APP_ID;
import static com.speedment.runtime.config.ProjectUtil.COMPANY_NAME;
import static com.speedment.runtime.config.ProjectUtil.CONFIG_PATH;
import static com.speedment.runtime.config.ProjectUtil.DEFAULT_COMPANY_NAME;
import static com.speedment.runtime.config.ProjectUtil.DEFAULT_PACKAGE_LOCATION;
import static com.speedment.runtime.config.ProjectUtil.PACKAGE_LOCATION;
import static com.speedment.runtime.config.ProjectUtil.SPEEDMENT_VERSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.trait.HasEnableUtil;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import org.junit.jupiter.api.Test;

final class ProjectTest extends BaseConfigTest<Project> {

    @Override
    Project getDocumentInstance() {
        return Project.create(map());
    }

    @Test
    void getCompanyName() {
        final Project project = Project.create(map(entry(COMPANY_NAME, "company")));
        assertEquals("company", project.getCompanyName());

        assertEquals(DEFAULT_COMPANY_NAME, getDocumentInstance().getCompanyName());
    }

    @Test
    void getPackageLocation() {
        final Project project = Project.create(map(entry(PACKAGE_LOCATION, "package_location")));
        assertEquals("package_location", project.getPackageLocation());

        assertEquals(DEFAULT_PACKAGE_LOCATION, getDocumentInstance().getPackageLocation());
    }

    @Test
    void getSpeedmentVersion() {
        final Project project = Project.create(map(entry(SPEEDMENT_VERSION, "3.2.9")));
        assertTrue(project.getSpeedmentVersion().isPresent());

        assertFalse(getDocumentInstance().getSpeedmentVersion().isPresent());
    }

    @Test
    void getAppId() {
        final Project project = Project.create(map(entry(APP_ID, "id")));
        assertEquals("id", project.getAppId());

        assertThrows(SpeedmentConfigException.class, getDocumentInstance()::getAppId);
    }

    @Test
    void getConfigPath() {
        final Project project = Project.create(map(entry(CONFIG_PATH, "path")));
        assertTrue(project.getConfigPath().isPresent());

        assertFalse(getDocumentInstance().getConfigPath().isPresent());
    }

    @Test
    void findTableByName() {
        assertThrows(IllegalArgumentException.class, () -> getDocumentInstance().findTableByName("dbms"));
        assertThrows(IllegalArgumentException.class, () -> getDocumentInstance().findTableByName("dbms.schema"));

        final Project project = Project.create(map(
            entry(HasNameUtil.NAME, "Project"),
            entry(HasEnableUtil.ENABLED, true),
            entry(ProjectUtil.DBMSES, map(
                entry(HasIdUtil.ID, "dbms_id"),
                entry(HasNameUtil.NAME, "DBMS"),
                entry(HasEnableUtil.ENABLED, true),
                entry(DbmsUtil.TYPE_NAME, "MySQL"),
                entry(DbmsUtil.SCHEMAS, map(
                    entry(HasIdUtil.ID, "schema_id"),
                    entry(HasNameUtil.NAME, "schema"),
                    entry(HasEnableUtil.ENABLED, true),
                    entry(SchemaUtil.TABLES, map(
                        entry(HasIdUtil.ID, "table_id"),
                        entry(HasNameUtil.NAME, "table"),
                        entry(HasEnableUtil.ENABLED, true))
                    ))
                ))
            ))
        );

        assertThrows(IllegalArgumentException.class, () -> project.findTableByName("no_dbms.schema_id.table_id"));
        assertThrows(IllegalArgumentException.class, () -> project.findTableByName("dbms_id.no_schema.table_id"));
        assertThrows(IllegalArgumentException.class, () -> project.findTableByName("dbms_id.schema_id.no_table"));

        assertNotNull(project.findTableByName("dbms_id.schema_id.table_id"));
    }

    @Test
    void deepCopy() {
        super.deepCopy();
        assertNotNull(Project.deepCopy(getDocumentInstance()));
    }

    @Test
    void create() {
        assertNotNull(Project.create(null, map()));
    }

}
