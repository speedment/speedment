import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ValueExampleTest {

    String text = "a";
    Number number = 1;
    Integer[] array = {1, 2};
    Integer[] emptyArray = {};
    long epoch = System.currentTimeMillis();
    boolean debug = true;
    Stream<String> stream = Stream.of("A", "B");
    String nullable = ;
    Function<Integer, String> function = new Function() {
        @Override
        String apply(Integer val) {
            return Integer.toString(val);
        }
    };
    Charset charset = StandardCharsets.UTF_8;
}