package com.speedment.util.stream.builder.action;

import static com.speedment.util.stream.builder.action.Property.ORDER;
import static com.speedment.util.stream.builder.action.Property.SIDE_EFFECT;
import static com.speedment.util.stream.builder.action.Property.SIZE;
import static com.speedment.util.stream.builder.action.Property.STREAM_TYPE;
import static com.speedment.util.stream.builder.action.Property.TYPE;
import static com.speedment.util.stream.builder.action.Verb.PRESERVE;
import static com.speedment.util.stream.builder.action.Verb.SET;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public enum StandardBasicAction implements BasicAction {

    FILTER(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    DISTINCT(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    FLAT_MAP(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    FLAT_MAP_TO(
            Statement.of(PRESERVE, ORDER)
    ),
    LIMIT(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE),
            Statement.of(SET, Property.FINITE)
    ),
    MAP(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, SIZE),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    MAP_TO_SAME(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, SIZE),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    MAP_TO(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, SIZE)
    ),
    PEEK(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE),
            Statement.of(PRESERVE, SIZE),
            Statement.of(SET, SIDE_EFFECT)
    ),
    SKIP(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE)
    ),
    SORTED(
            Statement.of(PRESERVE, TYPE),
            Statement.of(PRESERVE, STREAM_TYPE),
            Statement.of(SET, com.speedment.util.stream.builder.action.Property.SORTED)
    ),
    BOXED(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, SIZE),
            Statement.of(SET, STREAM_TYPE)
    ),
    AS(
            Statement.of(PRESERVE, ORDER),
            Statement.of(PRESERVE, SIZE),
            Statement.of(SET, STREAM_TYPE)
    );

    private final Statement[] statements;

    private StandardBasicAction(Statement... statements) {
        this.statements = statements;
    }

    @Override
    public Stream<Statement> statements() {
        return Stream.of(statements);
    }

}
