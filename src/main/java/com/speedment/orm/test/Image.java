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
package com.speedment.orm.test;

import java.time.LocalDateTime;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.Persistable;
import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.platform.Speedment;

public interface Image {

//    enum Holder {
//
//        INSTANCE;
//
//        // Use dependency injection to set this
//        private Speedment speedment;
//        
//        private Manager<Image> manager() {
//            return speedment.managerOf(Image.class);
//        }
//
//    }
//    
//    static void setSpeedment(Speedment speedment) {
//        Holder.INSTANCE.speedment = speedment;
//    }
    
//    enum Holder {
//
//        INSTANCE;
//
//        private Function<Image, Bean> beanifier = i -> new ImageBeanImpl(i);
//        private Function<Image, Builder> buildifier = i -> new ImageBuilderImpl(i);
//        private Function<Image, Image> immutablefier = i -> new ImageImpl(i);
//        private Supplier<Bean> beanSupplier = () -> new ImageBeanImpl();
//        private Supplier<Builder> builderSupplier = () -> new ImageBuilderImpl();
//    }

    String getSrc();

    LocalDateTime getPublished();

    Integer getAuthor();

    String getDescription();

    String getTitle();

    Integer getId();
/*    
    static Manager<Image> manager() {
        return ImageConfig.INSTANCE.manager();
    }

    static Builder builder() {
        return manager().builder(Builder.class);
    }

    default Builder toBuilder() {
        return manager().builderOf(Builder.class, this);
    }
    
    static Persister persister() {
        return manager().persister(Persister.class);
    }

    default Persister toPersister() {
        return manager().builderOf(Persister.class, this);
    }
*/
//    default Bean toBean() {
//        return Holder.INSTANCE.beanifier.apply(this);
//    }
//
//    default Builder toBuilder() {
//        return Holder.INSTANCE.buildifier.apply(this);
//    }
//
//    default Image toImmutable() {
//        return Holder.INSTANCE.immutablefier.apply(this);
//    }
//
//    static Bean bean() {
//        return Holder.INSTANCE.beanSupplier.get();
//    }
//
//    static Builder builder() {
//        return Holder.INSTANCE.builderSupplier.get();
//    }
//
//    static void setBeanFactory(Supplier<Bean> factory) {
//        Holder.INSTANCE.beanSupplier = factory;
//    }
//
//    static void setBuilderFactory(Supplier<Builder> factory) {
//        Holder.INSTANCE.builderSupplier = factory;
//    }
//
//    static void setBeanFactory(Function<Image, Bean> factory) {
//        Holder.INSTANCE.beanifier = factory;
//    }
//
//    static void setBuilderFactory(Function<Image, Builder> factory) {
//        Holder.INSTANCE.buildifier = factory;
//    }
//
//    static void setImmutableFactory(Function<Image, Image> factory) {
//        Holder.INSTANCE.immutablefier = factory;
//    }
//    public interface Bean extends Image {
//
//        Bean setSrc(String src);
//
//        Bean setPublished(LocalDateTime published);
//
//        Bean setAuthor(Integer author);
//
//        Bean setDescription(String description);
//
//        Bean setTitle(String title);
//
//        Bean setId(Integer id);
//    }

    public interface Builder extends Image, Buildable<Image> {

        Builder withSrc(String src);

        Builder withPublished(LocalDateTime published);

        Builder withAuthor(Integer author);

        Builder withDescription(String description);

        Builder withTitle(String title);

        Builder withId(Integer id);
    }
    
    public interface Persister extends Builder, Persistable<Image> {
        
    }
    
}
