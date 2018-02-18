package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.join.pipeline.JoinType;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.pipeline.PipelineImpl;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import com.speedment.runtime.join.pipeline.Pipeline;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
abstract class AbstractJoinBuilder<T> {

    private final JoinStreamSupplierComponent streamSupplier;
    private final List<StageBean<?>> tableInfoList;
    private final StageBean<T> stageBean;

    AbstractJoinBuilder(JoinStreamSupplierComponent streamSupplier, TableIdentifier<T> initialTable) {
        this.streamSupplier = requireNonNull(streamSupplier);
        this.tableInfoList = new ArrayList<>();
        this.stageBean = addInfo(requireNonNull(initialTable));
    }

    AbstractJoinBuilder(AbstractJoinBuilder<?> previous, StageBean<T> stageBean) {
        requireNonNull(previous);
        this.streamSupplier = previous.streamSuppler();
        this.tableInfoList = previous.tableInfoList();
        this.stageBean = requireNonNull(stageBean);
    }

    <T> StageBean<T> addInfo(TableIdentifier<T> table) {
        return addInfoHelper(new StageBean<>(table));
    }

    <T> StageBean<T> addInfo(TableIdentifier<T> table, JoinType joinType) {
        return addInfoHelper(new StageBean<>(table, joinType));
    }

    <T> StageBean<T> addInfoHelper(final StageBean<T> info) {
        requireNonNull(info);
        tableInfoList.add((StageBean<?>) info);
        return info;
    }

    List<StageBean<?>> tableInfoList() {
        return tableInfoList;
    }
    
    StageBean<T> stageBean() {
        return stageBean;
    }
    
    void addPredicate(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        stageBean.getPredicates().add(predicate);
    }

    JoinStreamSupplierComponent streamSuppler() {
        return streamSupplier;
    }

    Pipeline pipeline() {
        return new PipelineImpl(
            tableInfoList.stream()
                .map(StageBean::asStage)
                .collect(toList())
        );
    }

}
