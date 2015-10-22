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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.field.methods;

import com.speedment.annotation.Api;
import com.speedment.field.trait.FieldTrait;
import java.util.function.UnaryOperator;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> column value type
 */
@Api(version = "2.2")
public interface FieldSetter<ENTITY, V> extends UnaryOperator<ENTITY> {

    FieldTrait getField();
    
    V getValue();
    
    // Arne::setFoo
    
    // users.stream()
    //   .filter(User.NAME.equal("Eric"))
    //   .map(User.NAME.setTo("Emil"))
    //   .forEach()

}
