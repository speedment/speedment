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
package com.speedment.plugins.springgenerator.configuration;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.EntityManager;
import com.speedment.runtime.component.EventComponent;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.component.PrimaryKeyFactoryComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.component.StreamSupplierComponent;
import com.speedment.runtime.component.TypeMapperComponent;
import com.speedment.runtime.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Emil Forslund
 */
public abstract class SpeedmentConfiguration {

    @Bean
    public EntityManager entityManager(Speedment speedment) {
        return speedment.getEntityManager();
    }
    
    @Bean
    public EventComponent eventComponent(Speedment speedment) {
        return speedment.getEventComponent();
    }
    
    @Bean
    public DbmsHandlerComponent dbmsHandlerComponent(Speedment speedment) {
        return speedment.getDbmsHandlerComponent();
    }
    
    @Bean
    public ManagerComponent managerComponent(Speedment speedment) {
        return speedment.getManagerComponent();
    }
    
    @Bean
    public PrimaryKeyFactoryComponent primaryKeyFactoryComponent(Speedment speedment) {
        return speedment.getPrimaryKeyFactoryComponent();
    }
    
    @Bean
    public ProjectComponent projectComponent(Speedment speedment) {
        return speedment.getProjectComponent();
    }
    
    @Bean
    public ResultSetMapperComponent resultSetMapperComponent(Speedment speedment) {
        return speedment.getResultSetMapperComponent();
    }
    
    @Bean
    public ConnectionPoolComponent connectionPoolComponent(Speedment speedment) {
        return speedment.getConnectionPoolComponent();
    }
    
    @Bean
    public StreamSupplierComponent streamSupplierComponent(Speedment speedment) {
        return speedment.getStreamSupplierComponent();
    }
    
    @Bean
    public TypeMapperComponent typeMapperComponent(Speedment speedment) {
        return speedment.getTypeMapperComponent();
    }
    
    @Bean
    public PasswordComponent passwordComponent(Speedment speedment) {
        return speedment.getPasswordComponent();
    }
    
    @Bean
    public InfoComponent infoComponent(Speedment speedment) {
        return speedment.getInfoComponent();
    }
}