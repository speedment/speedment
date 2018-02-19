package com.speedment.runtime.join;

import com.speedment.common.function.Function5;
import com.speedment.common.function.Function6;
import com.speedment.common.function.QuadFunction;
import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.Assert.fail;

/**
 *
 * @author Per Minborg
 */
public final class JoinTestUtil {

    private JoinTestUtil() {
    }

    public static final Predicate<E1> E1_PREDICATE = e1 -> e1.getId() % 1 == 0;
    public static final Predicate<E2> E2_PREDICATE = e2 -> e2.getId() % 2 == 0;
    public static final Predicate<E3> E3_PREDICATE = e3 -> e3.getId() % 3 == 0;
    public static final Predicate<E4> E4_PREDICATE = e4 -> e4.getId() % 4 == 0;
    public static final Predicate<E5> E5_PREDICATE = e5 -> e5.getId() % 5 == 0;
    public static final Predicate<E6> E6_PREDICATE = e6 -> e6.getId() % 6 == 0;

    public static Stream<TableIdentifier<?>> identifiers() {
        return Stream.of(
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER,
            E6Manager.IDENTIFIER
        );
    }

    public interface E1Manager {

        static TableIdentifier<E1> IDENTIFIER = id("t1");
    }

    public interface E2Manager {

        static TableIdentifier<E2> IDENTIFIER = id("t2");
    }

    public interface E3Manager {

        static TableIdentifier<E3> IDENTIFIER = id("t3");
    }

    public interface E4Manager {

        static TableIdentifier<E4> IDENTIFIER = id("t4");
    }

    public interface E5Manager {

        static TableIdentifier<E5> IDENTIFIER = id("t5");
    }

    public interface E6Manager {

        static TableIdentifier<E6> IDENTIFIER = id("t6");
    }
    
    public interface EXManager {

        static TableIdentifier<E6> IDENTIFIER = id("tx");
    }

    public interface E1 extends HasId<E1> {

        IntField<E1, Integer> ID1 = IntField.create(id(E1Manager.IDENTIFIER, "id1"), E1::getId, E1::setId, TypeMapper.primitive(), true);

    }

    public interface E2 extends HasId<E2> {

        IntField<E2, Integer> ID2 = IntField.create(id(E3Manager.IDENTIFIER, "id2"), E2::getId, E2::setId, TypeMapper.primitive(), true);

    }

    public interface E3 extends HasId<E3> {

        IntField<E3, Integer> ID3 = IntField.create(id(E3Manager.IDENTIFIER, "id3"), E3::getId, E3::setId, TypeMapper.primitive(), true);

    }

    public interface E4 extends HasId<E4> {

        IntField<E4, Integer> ID4 = IntField.create(id(E4Manager.IDENTIFIER, "id3"), E4::getId, E4::setId, TypeMapper.primitive(), true);

    }

    public interface E5 extends HasId<E5> {

        IntField<E5, Integer> ID5 = IntField.create(id(E5Manager.IDENTIFIER, "id5"), E5::getId, E5::setId, TypeMapper.primitive(), true);

    }

    public interface E6 extends HasId<E6> {

        IntField<E6, Integer> ID6 = IntField.create(id(E6Manager.IDENTIFIER, "id6"), E6::getId, E6::setId, TypeMapper.primitive(), true);

    }
    
    public interface EX extends HasId<EX> {

        IntField<EX, Integer> IDX = IntField.create(id(E6Manager.IDENTIFIER, "idx"), EX::getId, EX::setId, TypeMapper.primitive(), true);

    }

    public interface HasId<T extends HasId<T>> {

        T setId(int id);

        int getId();
    }

    public static class E1Impl extends HasIdImpl<E1> {
    };

    public static class E2Impl extends HasIdImpl<E2> {
    };

    public static class E3Impl extends HasIdImpl<E3> {
    };

    public static class E4Impl extends HasIdImpl<E4> {
    };

    public static class E5Impl extends HasIdImpl<E5> {
    };

    public static class E6Impl extends HasIdImpl<E6> {
    };

    public static class HasIdImpl<T extends HasId<T>> implements HasId<T> {

        private int id;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public T setId(int id) {
            this.id = id;
            return self();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + this.id;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final HasIdImpl<?> other = (HasIdImpl<?>) obj;
            if (this.id != other.id) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "HasIdImpl{" + "id=" + id + '}';
        }

        @SuppressWarnings("unchecked")
        private T self() {
            return (T) this;
        }

    }

    public static <T> TableIdentifier<T> id(String tableName) {
        return TableIdentifier.of("db", "schema", tableName);
    }

    public static <T> ColumnIdentifier<T> id(TableIdentifier<?> table, String columnName) {
        return ColumnIdentifier.of(table.getDbmsName(), table.getSchemaName(), table.getTableName(), columnName);
    }

    public static class MockStreamSupplierComponent implements StreamSupplierComponent {

        @Override
        @SuppressWarnings("unchecked")
        public <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableIdentifier, ParallelStrategy strategy) {
            int table = -1;
            if (E1Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 1;
            }
            if (E2Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 2;
            }
            if (E3Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 3;
            }
            if (E4Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 4;
            }
            if (E5Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 5;
            }
            if (E6Manager.IDENTIFIER.equals(tableIdentifier)) {
                table = 6;
            }
            if (table == -1) {
                throw new UnsupportedOperationException("The table " + tableIdentifier + " is not known");
            }
            return (Stream<ENTITY>) IntStream.range(0, table * 8)
                .mapToObj(i -> new E1Impl().setId(i));
        }

    }

    public static class MockJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

        private List<Stage<?>> stages;
        private Object constructor;
        private TableIdentifier<E1> t1;
        private TableIdentifier<E2> t2;
        private TableIdentifier<E3> t3;
        private TableIdentifier<E4> t4;
        private TableIdentifier<E5> t5;
        private TableIdentifier<E6> t6;

        @Override
        @SuppressWarnings("unchecked")
        public <T1, T2, T> Join<T> createJoin(List<Stage<?>> stages, BiFunction<T1, T2, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2) {
            set(
                stages,
                constructor,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) null,
                (TableIdentifier<E4>) null,
                (TableIdentifier<E5>) null,
                (TableIdentifier<E6>) null
            );
            return empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T1, T2, T3, T> Join<T> createJoin(List<Stage<?>> stages, TriFunction<T1, T2, T3, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3) {
            set(
                stages,
                constructor,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) null,
                (TableIdentifier<E5>) null,
                (TableIdentifier<E6>) null
            );

            return empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T1, T2, T3, T4, T> Join<T> createJoin(List<Stage<?>> stages, QuadFunction<T1, T2, T3, T4, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4) {
            set(
                stages,
                constructor,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) null,
                (TableIdentifier<E6>) null
            );

            return empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T1, T2, T3, T4, T5, T> Join<T> createJoin(List<Stage<?>> stages, Function5<T1, T2, T3, T4, T5, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5) {
            set(
                stages,
                constructor,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) t5,
                (TableIdentifier<E6>) null
            );

            return empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(List<Stage<?>> stages, Function6<T1, T2, T3, T4, T5, T6, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6) {
            set(
                stages,
                constructor,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) t5,
                (TableIdentifier<E6>) t6
            );
            return empty();
        }

        public List<Stage<?>> stages() {
            return stages;
        }

        public Object constructor() {
            return constructor;
        }

        public TableIdentifier<E1> t1() {
            return t1;
        }

        public TableIdentifier<E2> t2() {
            return t2;
        }

        public TableIdentifier<E3> t3() {
            return t3;
        }

        public TableIdentifier<E4> t4() {
            return t4;
        }

        public TableIdentifier<E5> t5() {
            return t5;
        }

        public TableIdentifier<E6> t6() {
            return t6;
        }

        public TableIdentifier<?> t(int index) {
            switch (index) {
                case 0:
                    return t1();
                case 1:
                    return t2();
                case 2:
                    return t3();
                case 3:
                    return t4();
                case 4:
                    return t5();
                case 5:
                    return t6();
            }
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        private void set(List<Stage<?>> stages, Object constructor, TableIdentifier<E1> t1, TableIdentifier<E2> t2, TableIdentifier<E3> t3, TableIdentifier<E4> t4, TableIdentifier<E5> t5, TableIdentifier<E6> t6) {
            this.stages = stages;
            this.constructor = constructor;
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
        }

        private <T> Join<T> empty() {
            return new JoinImpl<>(Stream::empty);
        }
    }

    public static void assertStagesEquals(List<Stage<?>> expected, List<Stage<?>> actual) {
        if (expected.size() != actual.size()) {
            fail("Expected size " + expected.size() + " but actual size " + actual.size());
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!equals(expected.get(i), actual.get(i))) {
                System.out.println("The lists differ for index  " + i);
                System.out.println("Expected: " + expected.get(i));
                System.out.println("Actual  : " + actual.get(i));
                fail("Error at index " + i);
            }
        }

    }

    public static boolean equals(Stage<?> s1, Stage<?> s2) {
        return Stream.<Function<Stage<?>, ?>>of(
            Stage::firstField,
            Stage::identifier,
            Stage::joinType,
            Stage::operatorType,
            Stage::otherTableField,
            Stage::predicates,
            Stage::secondField
        ).allMatch(keyExtractor -> equals(keyExtractor, s1, s2));

    }

    private static boolean equals(Function<Stage<?>, ?> keyExtractor, Stage<?> s1, Stage<?> s2) {
        return Objects.equals(keyExtractor.apply(s1), keyExtractor.apply(s2));
    }

}
