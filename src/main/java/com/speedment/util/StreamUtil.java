package com.speedment.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

/**
 *
 * @author Duncan
 */
public class StreamUtil {
	public static <T> Stream<T> mandatory(Optional<T> element) {
		if (element.isPresent()) {
			return Stream.of(element.get());
		} else { return Stream.empty(); }
	}
	
	public static <T> Stream<T> of(List<Optional<T>> list) {
		final Builder<T> build = Stream.builder();
		list.forEach(t -> {if (t.isPresent()) build.add(t.get());});
		return build.build();
	}
}
