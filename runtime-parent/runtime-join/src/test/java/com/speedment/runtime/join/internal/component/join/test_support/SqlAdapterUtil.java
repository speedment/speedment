package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E0;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E3;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E4;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E5;
import java.sql.ResultSet;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class SqlAdapterUtil {

    private SqlAdapterUtil() {
    }

    public static class E0SqlAdapter extends AbstractSqlAdapter<E0> implements SqlAdapter<E0> {
        public E0SqlAdapter() {super(JoinTestUtil.E0Manager.IDENTIFIER);}
    }
    public static class E1SqlAdapter extends AbstractSqlAdapter<E1> implements SqlAdapter<E1> {
        public E1SqlAdapter() {super(JoinTestUtil.E1Manager.IDENTIFIER);}
    }
    public static class E2SqlAdapter extends AbstractSqlAdapter<E2> implements SqlAdapter<E2> {
        public E2SqlAdapter() {super(JoinTestUtil.E2Manager.IDENTIFIER);}
    }
    public static class E3SqlAdapter extends AbstractSqlAdapter<E3> implements SqlAdapter<E3> {
        public E3SqlAdapter() {super(JoinTestUtil.E3Manager.IDENTIFIER);}
    }
    public static class E4SqlAdapter extends AbstractSqlAdapter<E4> implements SqlAdapter<E4> {
        public E4SqlAdapter() {super(JoinTestUtil.E4Manager.IDENTIFIER);}
    }
    public static class E5SqlAdapter extends AbstractSqlAdapter<E5> implements SqlAdapter<E5> {
        public E5SqlAdapter() {super(JoinTestUtil.E5Manager.IDENTIFIER);}
    }

    public static class AbstractSqlAdapter<ENTITY> implements SqlAdapter<ENTITY> {

        private final TableIdentifier<ENTITY> identifier;

        public AbstractSqlAdapter(TableIdentifier<ENTITY> identifier) {
            this.identifier = requireNonNull(identifier);
        }

        @Override
        public TableIdentifier<ENTITY> identifier() {
            return identifier;
        }

        @Override
        public SqlFunction<ResultSet, ENTITY> entityMapper() {
            return rs -> {
               throw new UnsupportedOperationException();
            };
        }

        @Override
        public SqlFunction<ResultSet, ENTITY> entityMapper(int offset) {
            return rs -> {
               throw new UnsupportedOperationException();
            };
        }

    }

}
