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
package com.speedment.plugins.reactor;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A materialized view holds a snapshot of the current state of a table in the
 * database. The table rows are considered events that refer to entities. If 
 * multiple events refer to the same entity, the entities will be merged into 
 * the state so that each entity only exist once. This is useful to create an 
 * Event Sourcing system from a regular relational database.
 * <p>
 * <b>Example usage:</b>
 * <code>
 *      final MaterializedView&lt;ArticleEvent&gt; articlesView 
 *          = new MaterializedViewImpl&lt;&gt;(ArticleEvent.ARTICLE_ID);
 * 
 *      final Reactor&lt;ArticleEvent&gt; reactor = 
 *          Reactor.builder(
 *                  speedment.managerOf(ArticleEvent.class),
 *                  ArticleEvent.ARTICLE_ID
 *          ).withListener(articlesView).build();
 * 
 *      // The reactor is automatically started.
 * </code>
 * 
 * @param <ENTITY>  the entity type
 * @param <T>       the unique key type
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface MaterializedView<ENTITY, T extends Comparable<T>> 
    extends Consumer<List<ENTITY>> {

    /**
     * Returns the entity currently mapped to the specified value for the field
     * on which this view is identified.
     * 
     * @param key  the field value to look for
     * @return     the entity found or an empty {@code Optional}
     */
    Optional<ENTITY> get(T key);
    
    /**
     * returns a stream of all the entities currently in the view.
     * 
     * @return  the stream
     */
    Stream<ENTITY> stream();
}
