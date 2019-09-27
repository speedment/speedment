package com.speedment.tool.config.component;

import com.speedment.runtime.config.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public final class DocumentPropertyComponentUtil {
    private DocumentPropertyComponentUtil() {}

    public static final List<String> PROJECTS            = emptyList();
    public static final List<String> DBMSES              = singletonList(ProjectUtil.DBMSES);
    public static final List<String> SCHEMAS             = Stream.concat(DBMSES.stream(), Stream.of(DbmsUtil.SCHEMAS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> TABLES              = Stream.concat(SCHEMAS.stream(), Stream.of(SchemaUtil.TABLES)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> INDEXES             = Stream.concat(TABLES.stream(), Stream.of(TableUtil.INDEXES)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> INDEX_COLUMNS       = Stream.concat(INDEXES.stream(), Stream.of(IndexUtil.INDEX_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> FOREIGN_KEYS        = Stream.concat(TABLES.stream(), Stream.of(TableUtil.FOREIGN_KEYS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> FOREIGN_KEY_COLUMNS = Stream.concat(FOREIGN_KEYS.stream(), Stream.of(ForeignKeyUtil.FOREIGN_KEY_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> PRIMARY_KEY_COLUMNS = Stream.concat(TABLES.stream(), Stream.of(TableUtil.PRIMARY_KEY_COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
    public static final List<String> COLUMNS             = Stream.concat(TABLES.stream(), Stream.of(TableUtil.COLUMNS)).collect(collectingAndThen(toList(), Collections::unmodifiableList));
}
