import com.speedment.common.codegen.model.Field;

import java.util.List;

/**
 * This is a test interface.
 *
 * @author tester the one and only
 *
 * @apiNote always
 */
public interface InterfaceExampleTest extends List<String> {

    String NAME = "John";

    default int three() {
        return 3;
    }

    Object render(Field model);

    static InterfaceExampleTest create() {
        return null;
    }
}