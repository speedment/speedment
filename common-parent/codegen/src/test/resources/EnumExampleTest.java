import com.speedment.common.codegen.model.Field;

import java.util.List;

/**
 * This is a test enum.
 *
 * @author tester the one and only
 */
public enum EnumExampleTest implements List<String> {

    /**
     * A constant
     */
    A ("a") {
        {
            int a = 1;
        }
    },
    /**
     * B constant
     */
    B ("b") {
        int foo;
    },
    /**
     * C constant
     */
    C ("c") {
        private static final class Foo {}
    },
    /**
     * D constant
     */
    D ("d");

    private static final String NAME = "John";
    private final String text;

    private EnumExampleTest(String text) {
        this.text = text;
    }

    {
        int a = 1;
    }

    default int three() {
        return 3;
    }

    public Object render(Field model) {}

    static EnumExampleTest create() {
        return null;
    }
}