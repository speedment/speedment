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

import com.speedment.core.config.model.Column;
import com.speedment.core.core.Buildable;
import com.speedment.core.manager.Manager;
import com.speedment.core.field.Field;
import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import static com.speedment.util.java.JavaLanguage.javaVariableName;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public final class Json<ENTITY> {

	private final Map<String, Function<ENTITY, String>> getters;
		
	public Json() {
		this.getters = new ConcurrentHashMap<>();
	}
	
	// Fields
	public <T> Json<ENTITY> put(ReferenceField<ENTITY, T> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public Json<ENTITY> put(IntField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public Json<ENTITY> put(LongField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public Json<ENTITY> put(DoubleField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	// Foreign key fields.
	public <T, FK_ENTITY> Json<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	public <T extends Comparable<? super T>, FK_ENTITY> Json<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	public <FK_ENTITY> Json<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	// Label-and-getter pairs
	public <T> Json<ENTITY> put(String label, Function<ENTITY, T> getter) {
		getters.put(label, e -> "\"" + label + "\":\"" + jsonValue(getter.apply(e)) + "\"");
		return this;
	}
	
	public <T> Json<ENTITY> putDouble(String label, ToDoubleFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsDouble(e));
		return this;
	}
	
	public <T> Json<ENTITY> putInt(String label, ToIntFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsInt(e));
		return this;
	}
	
	public <T> Json<ENTITY> putLong(String label, ToLongFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsLong(e));
		return this;
	}
	
	// Label-and-getter with custom formatter
	public <FK_ENTITY> Json<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, Json<FK_ENTITY> builder) {
		getters.put(label, e -> "\"" + label + "\":" + builder.build(getter.apply(e)));
		return this;
	}

	// Label-and-streamer with custom formatter.
	public <FK_ENTITY> Json<ENTITY> putAll(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Json<FK_ENTITY> builder) {
		getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::build).collect(joining(",")) + "]");
		return this;
	}
	
	public <FK_ENTITY> Json<ENTITY> putAll(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> formatter) {
		getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(formatter).collect(joining(",")) + "]");
		return this;
	}
	
	// Removers by label
	public Json<ENTITY> remove(String label) {
		getters.remove(label);
		return this;
	}
	
	public <T> Json<ENTITY> remove(ReferenceField<ENTITY, T> field) {
		getters.remove(jsonField(field));
		return this;
	}
	
	public String build(ENTITY entity) {
		return "{" + 
			getters.values().stream()
				.map(g -> g.apply(entity))
				.collect(joining(",")) + 
			"}";
	}
	
	private static <ENTITY> String jsonField(Field<ENTITY> field) {
		return javaVariableName(field.getColumn().getName());
	}
	
	private static String jsonValue(Object in) {
		final String value;
		if (in instanceof Optional<?>) {
			value = String.valueOf(((Optional<?>) in).get());
		} else {
			value = String.valueOf(in);
		}
		return value.replace("\"", "\\\"");
	}
	
	public static <PK, ENTITY, BUILDER extends Buildable<ENTITY>, MANAGER extends Manager<PK, ENTITY, BUILDER>> Json<ENTITY> allFrom(MANAGER manager) {
		
		final Json<ENTITY> json = new Json<>();
		
		manager.getTable()
			.streamOf(Column.class)
			.forEachOrdered(c -> {
				
			json.put(
				javaVariableName(c.getName()),
				(ENTITY entity) -> manager.get(entity, c)
			);
		});
		
		return json;
	}
}