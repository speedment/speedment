package com.speedment.code;

import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.internal.core.code.TranslatorKeyImpl;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface StandardTranslatorKey {

    final TranslatorKey<Project> 
            SPEEDMENT_APPLICATION = new TranslatorKeyImpl<>("SpeedmentApplication"),
            SPEEDMENT_APPLICATION_METADATA = new TranslatorKeyImpl<>("SpeedmentApplicationMetadata");
    
    final TranslatorKey<Table> 
            ENTITY = new TranslatorKeyImpl<>("Entity"),
            ENTITY_IMPL = new TranslatorKeyImpl<>("EntityImpl"),
            MANAGER_IMPL = new TranslatorKeyImpl<>("ManagerImpl");

    
    static Stream<TranslatorKey<Project>> projectTranslatorKeys() {
        return Stream.of(SPEEDMENT_APPLICATION, SPEEDMENT_APPLICATION_METADATA);
    }
    
    static Stream<TranslatorKey<Table>> tableTranslatorKeys() {
        return Stream.of(ENTITY, ENTITY_IMPL, MANAGER_IMPL);
    }

}
