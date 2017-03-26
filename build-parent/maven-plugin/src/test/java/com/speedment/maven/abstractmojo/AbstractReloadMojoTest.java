package com.speedment.maven.abstractmojo;

import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Since ConfigFileHelper is final we need to use Powermock so it is possible to actually mock it.
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigFileHelper.class})
public class AbstractReloadMojoTest {

    private AbstractReloadMojo mojo;

    @Mock
    private File mockedConfigLocation;
    @Mock
    private Speedment mockedSpeedment;
    @Mock
    private ConfigFileHelper mockedConfigFileHelper;

    @Before
    public void setup() {
        mojo = new AbstractReloadMojoTestImpl();
    }

    @Test
    public void execute() throws Exception {
        // Given
        when(mockedSpeedment.getOrThrow(ConfigFileHelper.class)).thenReturn(mockedConfigFileHelper);
        Whitebox.setInternalState(mojo, "configFile", mockedConfigLocation);

        // When
        mojo.execute(mockedSpeedment);

        // Then
        verify(mockedConfigFileHelper).setCurrentlyOpenFile(mockedConfigLocation);
        verify(mockedConfigFileHelper).loadFromDatabaseAndSaveToFile();
    }

}