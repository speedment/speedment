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
package com.speedment.maven.abstractmojo;

import com.speedment.runtime.core.Speedment;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.lang.reflect.Field;

public class AbstractClearTablesMojoTestImpl extends AbstractClearTablesMojo {
	@Override
	protected String launchMessage() {
		return "test implementation of AbstractClearTablesMojo";
	}

	public void execute(Speedment speedment) throws MojoFailureException, MojoExecutionException {
		super.execute(speedment);
	}

	public void setConfigFile(String mockedConfigLocation) {
		try {
			Field field = AbstractClearTablesMojo.class.getDeclaredField("configFile");
			field.setAccessible(true);
			field.set(this, mockedConfigLocation);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setDebug(Boolean mockedDebug) {
		try {
			Field field = AbstractClearTablesMojo.class.getDeclaredField("debug");
			field.setAccessible(true);
			field.set(this, mockedDebug);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
