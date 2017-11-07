/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.maven.abstractmojo;

import com.speedment.maven.parameter.ConfigParam;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.internal.DbmsImpl;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.internal.SchemaImpl;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A maven goal (re)creates the configuration file with just the provided initialisation parameters.
 *
 * @author Mark Schrijver
 * @since 3.0.17
 */
public abstract class AbstractInitMojo extends AbstractSpeedmentMojo {

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject mavenProject;

	private @Parameter(defaultValue = "${debug}") Boolean debug;
	private @Parameter(defaultValue = "${companyName}") String companyName;
	private @Parameter(defaultValue = "${appName}") String appName;
	private @Parameter(defaultValue = "${package.location}") String packageLocation;
	private @Parameter(defaultValue = "${package.name}") String packageName;
	private @Parameter(defaultValue = "${dbms.type}") String dbmsType;
	private @Parameter(defaultValue = "${dbms.host}") String dbmsHost;
	private @Parameter(defaultValue = "${dbms.port}") int dbmsPort;
	private @Parameter(defaultValue = "${dbms.schemas}") String dbmsSchemas;
	private @Parameter(defaultValue = "${dbms.username}") String dbmsUsername;
	private @Parameter(defaultValue = "${dbms.password}") String dbmsPassword;
	private @Parameter ConfigParam[] parameters;
	private @Parameter(defaultValue = "${configFile}") String configFile;

	protected AbstractInitMojo() {
	}

	protected AbstractInitMojo(Consumer<ApplicationBuilder<?, ?>> configurer) {
		super(configurer);
	}

	@Override
	protected void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
		getLog().info("Saving default configuration from database to '" + configLocation().toAbsolutePath() + "'.");

		final ConfigFileHelper helper = speedment.getOrThrow(ConfigFileHelper.class);

		try {
			helper.setCurrentlyOpenFile(configLocation().toFile());
			helper.clearTablesAndSaveToFile();
		} catch (final Exception ex) {
			final String err = "An error occured while reloading.";
			getLog().error(err);
			throw new MojoExecutionException(err, ex);
		}
	}

	@Override
	protected MavenProject project() {
		return mavenProject;
	}

	@Override
	protected boolean debug() {
		return debug == null ? false: debug;
	}

	public String getConfigFile() {
		return configFile;
	}

	@Override
	protected String[] components() {
		return null;
	}

	@Override
	protected String[] typeMappers() {
		return null;
	}

	@Override
	protected ConfigParam[] parameters() {
		return parameters;
	}

	@Override
	protected String dbmsHost() {
		return dbmsHost;
	}

	@Override
	protected int dbmsPort() {
		return dbmsPort;
	}

	@Override
	protected String dbmsUsername() {
		return dbmsUsername;
	}

	@Override
	protected String dbmsPassword() {
		return dbmsPassword;
	}

	private Project createProject() {
		Map<String, Object> projectData = new HashMap<>();
		projectData.put(Project.COMPANY_NAME, companyName);
		projectData.put(Project.NAME, appName);
		projectData.put(Project.CONFIG_PATH, UUID.fromString(appName));
		projectData.put(Project.PACKAGE_LOCATION, packageLocation);
		projectData.put(Project.PACKAGE_NAME, packageName);
		projectData.put(Project.ID, appName);
		projectData.put(Project.ENABLED, true);
		projectData.put(Project.DBMSES, createDbmses());
		return new ProjectImpl(projectData);
	}

	private List<Dbms> createDbmses() {
		List<Dbms> dbmses = new ArrayList<>(1);
		dbmses.add(createDbms());
		return dbmses;
	}

	private Dbms createDbms() {
		Map<String, Object> dbmsData = new HashMap<>();
		dbmsData.put(Dbms.NAME, appName);
		dbmsData.put(Dbms.TYPE_NAME, dbmsType);
		dbmsData.put(Dbms.ID, appName);
		dbmsData.put(Dbms.PORT, dbmsPort);
		dbmsData.put(Dbms.IP_ADDRESS, dbmsHost);
		dbmsData.put(Dbms.USERNAME, dbmsUsername);
		dbmsData.put(Dbms.ENABLED, true);
		dbmsData.put(Dbms.SCHEMAS, createSchemas());
		return new DbmsImpl(null, dbmsData);
	}

	private List<Schema> createSchemas() {
		List<Schema> schemas = new ArrayList<>();

		Arrays.stream(dbmsSchemas.split(",")).forEach((schema) -> {
			String schemaName = schema.trim();
			Map<String, Object> schemaData = new HashMap<>();
			schemaData.put(Schema.NAME, schemaName);
			schemaData.put(Schema.ID, schemaName);
			schemaData.put(Schema.ENABLED, true);
			schemas.add(new SchemaImpl(null, schemaData));
		});

		return schemas;
	}

}