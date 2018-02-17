package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.join.pipeline.JoinType;
import com.speedment.runtime.config.identifier.TableIdentifier;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
abstract class AbstractJoinBuilder {

    private final List<StageBean<?>> tableInfoList;

    AbstractJoinBuilder() {
        this(new ArrayList<>());
    }

    AbstractJoinBuilder(List<StageBean<?>> tableInfoList) {
        this.tableInfoList = requireNonNull(tableInfoList);
    }

    protected <T> StageBean<T> addInfo(TableIdentifier<T> table) {
        return addInfoHelper(new StageBean<>(table));
    }

    protected <T> StageBean<T> addInfo(TableIdentifier<T> table, JoinType joinType) {
        return addInfoHelper(new StageBean<>(table, joinType));
    }

    private <T> StageBean<T> addInfoHelper(final StageBean<T> info) {
        tableInfoList.add((StageBean<?>) info);
        return info;
    }

    public List<StageBean<?>> tableInfoList() {
        return tableInfoList;
    }

}
