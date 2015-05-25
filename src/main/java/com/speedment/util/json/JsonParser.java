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
package com.speedment.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public final class JsonParser<ENTITY> {

	private enum Selector {INCLUDE, EXCLUDE}

	private final ENTITY entity;
	private final List<String> fieldNames;
	private final Map<Object, Function<Initaliser<?>, JsonParser<?>>> fieldReplacers;
	private final Selector selector;

	private JsonParser(ENTITY entity, Selector selector) {
		this.entity   = entity;
		this.selector = selector;
		this.fieldNames  = new ArrayList<>();
		this.fieldReplacers = new HashMap<>();
	}
	
	public boolean isIncluder() {
		return Selector.INCLUDE == selector;
	}
	
	public boolean isExcluder() {
		return Selector.EXCLUDE == selector;
	}
	
	public JsonParser<ENTITY> and(String columnName) {
		fieldNames.add(columnName);
		return this;
	}
	
//	public <FOREIGN_ENTITY, FIELD> JsonParser<ENTITY> expand(FIELD field) {
//		fieldReplacers.put(field, r -> r.)
//		return this;
//	}
	
	public <FOREIGN_ENTITY, FIELD> JsonParser<ENTITY> expand(FIELD field, 
		Function<Initaliser<FOREIGN_ENTITY>, JsonParser<FOREIGN_ENTITY>> replacer) {
		
		final Function<Initaliser<?>, JsonParser<?>> r = 
			(Function<Initaliser<?>, JsonParser<?>>) 
			(Function<?, ?>) replacer;
		
		fieldReplacers.put(field, r);
		
		return this;
	}
	
	public String toJson() {
		final StringBuilder json = new StringBuilder("{");
		
		
		
		return json.append("}").toString();
	}
	
	public class Initaliser<ENTITY> {
		
		private final ENTITY entity;
		
		public Initaliser(ENTITY entity) {
			this.entity = entity;
		}
		
		public JsonParser<ENTITY> include(String columnName) {
			return new JsonParser(entity, Selector.INCLUDE).and(columnName);
		}

		public JsonParser<ENTITY> exclude(String columnName) {
			return new JsonParser(entity, Selector.EXCLUDE).and(columnName);
		}
		
		
	}
}