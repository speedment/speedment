/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.core.manager.sql;

import com.speedment.field.Inclusion;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.internal.core.field.predicate.iface.type.HasInclusionOperand2;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand0;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand1;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand2;
import com.speedment.internal.core.field.predicate.iface.type.HasSetOperand0;
import com.speedment.internal.util.Cast;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.Set;

/**
 *
 * @author pemi
 */
public abstract class AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    protected abstract SqlPredicateFragment renderUninverted(SpeedmentPredicate<?, ?> model);

    protected SqlPredicateFragment render(SpeedmentPredicate<?, ?> model) {
        final SqlPredicateFragment unInverted = renderUninverted(model);
        if (!model.isNegated()) {
            return unInverted;
        } else {
            return unInverted.setSql("(NOT (" + unInverted.getSql() + ")");
        }
    }

    @Override
    public SqlPredicateFragment transform(SpeedmentPredicate<?, ?> model) {
        return render(requireNonNull(model));
    }

    public static SqlPredicateFragment of(String sql) {
        return SqlPredicateFragment.of(sql);
    }

    public static SqlPredicateFragment of(String sql, Collection<Object> objects) {
        return SqlPredicateFragment.of(sql, objects);
    }

    public static SqlPredicateFragment of(String sql, Object object) {
        return SqlPredicateFragment.of(sql, object);
    }

    public static Object oper0(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand0.class).getOperand0();
    }

    public static Set<?> setOper0(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasSetOperand0.class).getOperand0();
    }

    public static Object oper1(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand1.class).getOperand1();
    }

    public static Object oper2(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand2.class).getOperand2();
    }

    public static Inclusion inclusionOper2(SpeedmentPredicate<?, ?> p) {
        //QuaternaryInclusionOperation
        return Cast.castOrFail(p, HasInclusionOperand2.class).getOperand2();
    }

}
