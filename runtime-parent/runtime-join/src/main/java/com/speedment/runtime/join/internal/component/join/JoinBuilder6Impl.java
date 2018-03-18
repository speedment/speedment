package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function6;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4.JoinBuilder5.JoinBuilder6;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder6Impl<T0, T1, T2, T3, T4, T5>
    extends AbstractJoinBuilder<T5, JoinBuilder6<T0, T1, T2, T3, T4, T5>>
    implements JoinBuilder6<T0, T1, T2, T3, T4, T5> {

    JoinBuilder6Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T5> current) {
        super(previousStage, current);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function6<T0, T1, T2, T3, T4, T5, T> constructor) {
        requireNonNull(constructor);
        assertFieldsAreInJoinTables();
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier(),
            (TableIdentifier<T2>) stages.get(2).identifier(),
            (TableIdentifier<T3>) stages.get(3).identifier(),
            (TableIdentifier<T4>) stages.get(4).identifier(),
            (TableIdentifier<T5>) stages.get(5).identifier()
        );
    }

}
