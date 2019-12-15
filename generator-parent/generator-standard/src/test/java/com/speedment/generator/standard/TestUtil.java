package com.speedment.generator.standard;

import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.TranslatorBundle;
import com.speedment.runtime.application.RuntimeBundle;
import com.speedment.runtime.application.provider.EmptyApplicationMetadata;
import com.speedment.runtime.connector.mysql.MySqlBundle;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.provider.*;

public final class TestUtil {

    private TestUtil() {}

    public static Injector injector()  {
        try {
            return Injector.builder()
                .withComponent(DelegateInfoComponent.class)
                .withBundle(TranslatorBundle.class)
                .withBundle(StandardTranslatorBundle.class)
                .withBundle(RuntimeBundle.class)
                .withComponent(EmptyApplicationMetadata.class)
                .withBundle(MySqlBundle.class)
                .build();
        } catch (InstantiationException e) {
            throw new SpeedmentException(e);
        }
    }
}