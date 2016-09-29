/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.dbmodel.mutator.trait;


import com.speedment.common.dbmodel.mutator.DocumentMutator;
import com.speedment.common.dbmodel.parameter.OrderType;
import com.speedment.common.dbmodel.trait.HasOrderType;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public interface HasOrderTypeMutator<DOC extends HasOrderType> extends DocumentMutator<DOC> {
    
    default void setOrderType(OrderType orderType) {
         put(HasOrderType.ORDER_TYPE, orderType.name());
    }
}