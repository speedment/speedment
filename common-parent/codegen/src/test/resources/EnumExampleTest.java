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
    A ("a"),
    /**
     * B constant
     */
    B ("b");

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