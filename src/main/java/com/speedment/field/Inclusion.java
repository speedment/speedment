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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.field;

import com.speedment.annotation.Api;

/**
 *
 * @author pemi
 */
@Api(version = "2.2")
public enum Inclusion {

    START_INCLUSIVE_END_INCLUSIVE(true, true),
    START_INCLUSIVE_END_EXCLUSIVE(true, false),
    START_EXCLUSIVE_END_INCLUSIVE(false, true),
    START_EXCLUSIVE_END_EXCLUSIVE(false, false);

    private final boolean startInclusive, endInclusive;

    Inclusion(boolean startInclusive, boolean endInclusive) {
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    public boolean isStartInclusive() {
        return startInclusive;
    }

    public boolean isEndInclusive() {
        return endInclusive;
    }
}