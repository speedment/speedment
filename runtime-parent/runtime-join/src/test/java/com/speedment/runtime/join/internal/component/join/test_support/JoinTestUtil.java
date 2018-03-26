package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.PersistenceComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static org.junit.Assert.fail;

/**
 *
 * @author Per Minborg
 */
public final class JoinTestUtil {

    private JoinTestUtil() {
    }

    public static final Predicate<E0> E0_PREDICATE = e0 -> e0.getId() % 1 == 0;
    public static final Predicate<E1> E1_PREDICATE = e1 -> e1.getId() % 2 == 0;
    public static final Predicate<E2> E2_PREDICATE = e2 -> e2.getId() % 3 == 0;
    public static final Predicate<E3> E3_PREDICATE = e3 -> e3.getId() % 4 == 0;
    public static final Predicate<E4> E4_PREDICATE = e4 -> e4.getId() % 5 == 0;
    public static final Predicate<E5> E5_PREDICATE = e5 -> e5.getId() % 6 == 0;

    public static Stream<TableIdentifier<?>> identifiers() {
        return Stream.of(
            E0Manager.IDENTIFIER,
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER
        );
    }

    public interface E0Manager {

        static TableIdentifier<E0> IDENTIFIER = id(MockMetadata.T0_NAME);
    }

    public interface E1Manager {

        static TableIdentifier<E1> IDENTIFIER = id(MockMetadata.T1_NAME);
    }

    public interface E2Manager {

        static TableIdentifier<E2> IDENTIFIER = id(MockMetadata.T2_NAME);
    }

    public interface E3Manager {

        static TableIdentifier<E3> IDENTIFIER = id(MockMetadata.T3_NAME);
    }

    public interface E4Manager {

        static TableIdentifier<E4> IDENTIFIER = id(MockMetadata.T4_NAME);
    }

    public interface E5Manager {

        static TableIdentifier<E5> IDENTIFIER = id(MockMetadata.T5_NAME);
    }

    public interface EXManager {

        static TableIdentifier<E5> IDENTIFIER = id("tx");
    }

    public interface E0 extends HasId<E0> {

        IntField<E0, Integer> ID0 = IntField.create(id(E0Manager.IDENTIFIER, MockMetadata.T0_ID_NAME), E0::getId, E0::setId, TypeMapper.primitive(), true);

    }

    public interface E1 extends HasId<E1> {

        IntField<E1, Integer> ID1 = IntField.create(id(E1Manager.IDENTIFIER, MockMetadata.T1_ID_NAME), E1::getId, E1::setId, TypeMapper.primitive(), true);

    }

    public interface E2 extends HasId<E2> {

        IntField<E2, Integer> ID2 = IntField.create(id(E2Manager.IDENTIFIER, MockMetadata.T2_ID_NAME), E2::getId, E2::setId, TypeMapper.primitive(), true);

    }

    public interface E3 extends HasId<E3> {

        IntField<E3, Integer> ID3 = IntField.create(id(E3Manager.IDENTIFIER, MockMetadata.T3_ID_NAME), E3::getId, E3::setId, TypeMapper.primitive(), true);

    }

    public interface E4 extends HasId<E4> {

        IntField<E4, Integer> ID4 = IntField.create(id(E4Manager.IDENTIFIER, MockMetadata.T4_ID_NAME), E4::getId, E4::setId, TypeMapper.primitive(), true);

    }

    public interface E5 extends HasId<E5> {

        IntField<E5, Integer> ID5 = IntField.create(id(E5Manager.IDENTIFIER, MockMetadata.T5_ID_NAME), E5::getId, E5::setId, TypeMapper.primitive(), true);

    }

    public interface EX extends HasId<EX> {

        IntField<EX, Integer> IDX = IntField.create(id(EXManager.IDENTIFIER, "idx"), EX::getId, EX::setId, TypeMapper.primitive(), true);

    }

    public interface HasId<T extends HasId<T>> {

        T setId(int id);

        int getId();
    }

    public static class E0Impl extends HasIdImpl<E0> implements E0 {
    };

    public static class E1Impl extends HasIdImpl<E1> implements E1 {
    };

    public static class E2Impl extends HasIdImpl<E2> implements E2 {
    };

    public static class E3Impl extends HasIdImpl<E3> implements E3 {
    };

    public static class E4Impl extends HasIdImpl<E4> implements E4 {
    };

    public static class E5Impl extends HasIdImpl<E5> implements E5 {
    };

    static class HasIdImpl<T extends HasId<T>> implements HasId<T> {

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
            return getClass().getSimpleName() + "{" + "id=" + id + '}';
        }

        @SuppressWarnings("unchecked")
        private T self() {
            return (T) this;
        }

    }

    public static <T> TableIdentifier<T> id(String tableName) {
        return TableIdentifier.of(MockMetadata.DBMS_NAME, MockMetadata.SCHEMA_NAME, tableName);
    }

    public static <T> ColumnIdentifier<T> id(TableIdentifier<?> table, String columnName) {
        return ColumnIdentifier.of(table.getDbmsName(), table.getSchemaName(), table.getTableName(), columnName);
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
        return Stream.<Function<Stage<?>, ?>>of(Stage::foreignField,
            Stage::identifier,
            Stage::joinType,
            Stage::joinOperator,
            Stage::field,
            Stage::predicates
        ).allMatch(keyExtractor -> equals(keyExtractor, s1, s2));

    }

    private static boolean equals(Function<Stage<?>, ?> keyExtractor, Stage<?> s1, Stage<?> s2) {
        return Objects.equals(keyExtractor.apply(s1), keyExtractor.apply(s2));
    }

    public static class E0MangerImpl implements E0Manager {

    }

    public static class E1MangerImpl implements E1Manager {

    }

    public static class E2MangerImpl implements E2Manager {

    }

    public static class E3MangerImpl implements E3Manager {

    }

    public static class E4MangerImpl implements E4Manager {

    }

    public static class E5MangerImpl implements E5Manager {

    }

    public static class MockPersistanceComponent implements PersistenceComponent {

        @Override
        public <ENTITY> Persister<ENTITY> persister(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public <ENTITY> Updater<ENTITY> updater(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public <ENTITY> Remover<ENTITY> remover(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
    
//    public static class MockSqlPersistence implements SqlPersistence {
//        
//    }
    

}
