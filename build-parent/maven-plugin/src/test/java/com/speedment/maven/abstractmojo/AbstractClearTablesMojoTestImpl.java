package com.speedment.maven.abstractmojo;

import com.speedment.runtime.core.Speedment;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.lang.reflect.Field;

public class AbstractClearTablesMojoTestImpl extends AbstractClearTablesMojo {
	@Override
	protected String launchMessage() {
		return "test implementaiton of AbstractClearTablesMojo";
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
}
