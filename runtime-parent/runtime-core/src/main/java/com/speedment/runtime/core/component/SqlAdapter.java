package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.db.SqlFunction;
import java.sql.ResultSet;
import static javafx.scene.input.KeyCode.T;

/**
 * An SqlAdapter provides a means to read entities from a RsultSet.
 *
 * @author Per Minborg
 * @param <ENTITY> type for which mapping shall be carried out
 * @since 3.0.23
 */
@InjectKey(SqlAdapter.class)
public interface SqlAdapter<ENTITY> {

    /**
     * Returns the TableIdentifier for this SqlAdapter.
     *
     * @return the TableIdentifier for this SqlAdapter
     */
    TableIdentifier<ENTITY> identifier();

    /**
     * Returns an entity mapper for this SqlAdapter. The entity mapper will
     * start reading form the first column in the ResultSet.
     *
     * @return an entity mapper for this SqlAdapter
     */
    SqlFunction<ResultSet, ENTITY> entityMapper();

    /**
     * Returns an entity mapper for this SqlAdapter. The entity mapper will
     * start reading form the first column plus the given {@code offset} in the
     * ResultSet. This is usable for ResultSets obtained from joined operations.
     *
     * @param offset to add when reading columns from the ResultSet
     * @return an entity mapper for this SqlAdapter.
     *
     * @throws IllegalArgumentException if the given {@code offset } is
     * negative.
     */
    SqlFunction<ResultSet, ENTITY> entityMapper(int offset);

}
