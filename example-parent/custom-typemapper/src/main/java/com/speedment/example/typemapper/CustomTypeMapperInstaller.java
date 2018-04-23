package com.speedment.example.typemapper;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.translator.component.TypeMapperComponent;

/**
 *
 * @author Per Minborg
 */
public class CustomTypeMapperInstaller {

    @ExecuteBefore(RESOLVED)
    public void onResolve(@WithState(INITIALIZED) TypeMapperComponent typeMapper) {
        typeMapper.install(Integer.class, IntegerZeroOneToYesNoTypeMapper::new);
        typeMapper.install(int.class, IntegerZeroOneToYesNoTypeMapper::new);
    }    
}
