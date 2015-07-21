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
import com.speedment.core.config.model.Table;
import com.speedment.core.Buildable;
import com.speedment.core.manager.Manager;
import com.speedment.core.field.Field;
import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.ManagerComponent;
import static com.speedment.util.java.JavaLanguage.javaVariableName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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
public final class JsonFormatter<ENTITY> {

	private final Map<String, Function<ENTITY, String>> getters;
		
	public JsonFormatter() {
		this.getters = new LinkedHashMap<>();
	}
	
	// Fields
	public <T> JsonFormatter<ENTITY> put(ReferenceField<ENTITY, T> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public JsonFormatter<ENTITY> put(IntField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public JsonFormatter<ENTITY> put(LongField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	public JsonFormatter<ENTITY> put(DoubleField<ENTITY> field) {
		return put(jsonField(field), field::getFrom);
	}
	
	// Foreign key fields.
	public <T, FK_ENTITY> JsonFormatter<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	public <T extends Comparable<? super T>, FK_ENTITY> JsonFormatter<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	public <FK_ENTITY> JsonFormatter<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, JsonFormatter<FK_ENTITY> builder) {
		return put(jsonField(field), field::findFrom, builder);
	}
	
	// Label-and-getter pairs
	public <T> JsonFormatter<ENTITY> put(String label, Function<ENTITY, T> getter) {
		getters.put(label, e -> "\"" + label + "\":" + jsonValue(getter.apply(e)));
		return this;
	}
	
	public <T> JsonFormatter<ENTITY> putDouble(String label, ToDoubleFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsDouble(e));
		return this;
	}
	
	public <T> JsonFormatter<ENTITY> putInt(String label, ToIntFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsInt(e));
		return this;
	}
	
	public <T> JsonFormatter<ENTITY> putLong(String label, ToLongFunction<ENTITY> getter) {
		getters.put(label, e -> "\"" + label + "\":" + getter.applyAsLong(e));
		return this;
	}
	
	// Label-and-getter with custom formatter
	public <FK_ENTITY> JsonFormatter<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, JsonFormatter<FK_ENTITY> builder) {
		getters.put(label, e -> "\"" + label + "\":" + builder.apply(getter.apply(e)));
		return this;
	}

	// Label-and-streamer with custom formatter.
	public <FK_ENTITY> JsonFormatter<ENTITY> putAll(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, JsonFormatter<FK_ENTITY> builder) {
		getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(builder::apply).collect(joining(",")) + "]");
		return this;
	}
	
	public <FK_ENTITY> JsonFormatter<ENTITY> putAll(String label, Function<ENTITY, Stream<FK_ENTITY>> streamer, Function<FK_ENTITY, String> formatter) {
		getters.put(label, e -> "\"" + label + "\":[" + streamer.apply(e).map(formatter).collect(joining(",")) + "]");
		return this;
	}
	
	// Removers by label
	public JsonFormatter<ENTITY> remove(String label) {
		getters.remove(label);
		return this;
	}
	
	public <T> JsonFormatter<ENTITY> remove(ReferenceField<ENTITY, T> field) {
		getters.remove(jsonField(field));
		return this;
	}
	
	public String apply(ENTITY entity) {
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
            final Optional<?> o = (Optional<?>) in;
            return o.map(JsonFormatter::jsonValue).orElse("null");
		} else {
            if (in == null) {
                value = "null";
            } else {
                if (in instanceof Byte
                ||  in instanceof Short
                ||  in instanceof Integer
                ||  in instanceof Long
                ||  in instanceof Boolean
                ||  in instanceof Float
                ||  in instanceof Double) {
                    value = String.valueOf(in);
                } else {
                    value = "\"" + String.valueOf(in).replace("\"", "\\\"") + "\"";
                }
            }
		}
        
        return value;
	}
	
	public static <PK, ENTITY, BUILDER extends Buildable<ENTITY>> JsonFormatter<ENTITY> allFrom(Class<ENTITY> entityType) {
		
		final JsonFormatter<ENTITY> json = new JsonFormatter<>();
        
        final Manager<PK, ENTITY, BUILDER> manager = Platform.get()
            .get(ManagerComponent.class)
            .managerOf(entityType);
        
        final Table table = manager.getTable();
		
		table
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