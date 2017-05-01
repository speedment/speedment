/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.oracle_java_magazine.util;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.actor.ActorManager;
import com.company.sakila.db0.sakila.address.AddressManager;
import com.company.sakila.db0.sakila.category.CategoryManager;
import com.company.sakila.db0.sakila.city.CityManager;

/**
 *
 * @author Per Minborg
 */
public class ManagerHolder {

    protected final ActorManager actors;
    protected final AddressManager addresses;
    protected final CategoryManager categories;
    protected final CityManager cities;

    public ManagerHolder(final SakilaApplication app) {
        this.actors = app.getOrThrow(ActorManager.class);
        this.addresses = app.getOrThrow(AddressManager.class);
        this.categories = app.getOrThrow(CategoryManager.class);
        this.cities = app.getOrThrow(CityManager.class);
    }

}
