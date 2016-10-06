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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocTagsView;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import com.speedment.common.codegen.model.trait.HasJavadoc;
import com.speedment.common.codegen.util.Formatting;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from a {@link Javadoc} to java code.
 * 
 * @author Emil Forslund
 */
public final class JavadocView implements Transform<Javadoc, String>, 
    HasJavadocTagsView<Javadoc> {
    
    private final static int BLOCK_WIDTH = 80;
    
	private final static String
		JAVADOC_DELIMITER = nl() + " * ",
		JAVADOC_PREFIX    = "/**" + nl() + " * ",
		JAVADOC_SUFFIX    = nl() + " */";
	
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Javadoc model) {
        requireNonNulls(gen, model);

        final int blockWidth = BLOCK_WIDTH - 3 - currentTabLevel(gen);
        final String formattedText = formatText(model.getText(), 0, blockWidth).toString();
        
        final Map<String, List<JavadocTag>> tagGroups = model.getTags().stream()
            .collect(Collectors.groupingBy(
                JavadocView::groupOf, 
                TreeMap::new, 
                Collectors.toList()
            ));
        
        final String tagSection = tagGroups.values().stream().map(tags -> {
            final List<StringBuilder> rowBuilders = new ArrayList<>(tags.size());

            // Create a new row builder for each tag and initiate it with the 
            // tag name
            tags.forEach(tag -> {
                final StringBuilder row = new StringBuilder("@").append(tag.getName());
                
                if (tag.getText().isPresent()) {
                    row.append(tag.getValue().map(v -> " " + v).orElse(""));
                }
                
                rowBuilders.add(row);
            });
            
            // Determine the column width of the widest tag name and use that 
            // for padding.
            final int indentTo = rowBuilders.stream()
                .mapToInt(sb -> sb.length())
                .map(i -> i + 1)             // Add one extra space to each row.
                .max().orElse(0);            // If empty, do no padding.
            
            rowBuilders.forEach(row -> {
                final int padding = indentTo - row.length();
                if (padding > 0) {
                    row.append(repeat(" ", padding));
                }
            });
            
            // All the rows are now of the same width. Go through the remaining 
            // content and add it to the text, padding in each new line with the 
            // same amount.
            for (int i = 0; i < rowBuilders.size(); i++) {
                final JavadocTag tag    = tags.get(i);
                final StringBuilder row = rowBuilders.get(i);
                
                final String content;
                if (tag.getText().isPresent()) {
                    content = tag.getText().orElse("");
                } else {
                    content = Stream.of(
                        tag.getValue().orElse(null),
                        tag.getText().orElse(null)
                    ).filter(s -> s != null).collect(joining(" "));
                }
                
                row.append(formatText(content, indentTo, blockWidth));
            }
            
            // Concatenate the rows, separating them with new-line characters.
            return rowBuilders.stream()
                .map(StringBuilder::toString)
                .collect(joining(nl()));
        }).collect(joining(dnl()));
        
		return Optional.of(
            JAVADOC_PREFIX + 
            Stream.of(formattedText, tagSection)
                .filter(s -> s != null)
                .filter(s -> !s.isEmpty())
                .collect(joining(dnl()))
                .replace(nl(), JAVADOC_DELIMITER) + 
            JAVADOC_SUFFIX
        );
	}
    
    private static StringBuilder formatText(String text, int indentTo, int blockWidth) {
        final StringBuilder row = new StringBuilder();
        final AtomicInteger col = new AtomicInteger(indentTo);
        
        Stream.of(text.split(" "))
            .map(s -> s.replace("\t", tab()))
            .forEachOrdered(str -> {
                final String[] words = str.split(nl());
                for (int i = 0; i < words.length; i++) {
                    final String word = words[i];
                    
                    // If the were forced to do a line-break, make sure to tab
                    // into the current level and reset the counter.
                    if (i > 0) {
                        row.append(nl()).append(repeat(" ", indentTo));
                        col.set(indentTo);
                    }

                    // If this new word is about to push us over the blockWidth,
                    // create a new line and reset the column counter.
                    final int extraSpace = (col.get() > indentTo ? 1 : 0);
                    if (col.get() + word.length() + extraSpace > blockWidth) {
                        row.append(nl()).append(repeat(" ", indentTo));
                        col.set(indentTo);
                    }

                    // If this is not the first input on the line, add a space.
                    if (col.get() > indentTo) {
                        row.append(" ");
                        col.incrementAndGet();
                    }

                    // Write the word and increment the column counter.
                    row.append(word);
                    col.addAndGet(word.length());
                }
            });
        
        return row;
    }
    
    private static int currentTabLevel(Generator gen) {
        final int level = (int) gen.getRenderStack()
            .fromTop(HasJavadoc.class)
            .distinct()
            .count();
        
        if (level <= 2) {
            return 0;
        } else {
            return (level - 2) * Formatting.tab().length();
        }
    }
    
    private static String groupOf(JavadocTag tag) {
        switch (tag.getName()) {
            case "param"      :
            case "return"     : return "group_0";
            case "throws"     : return "group_1";
            case "author"     : 
            case "deprecated" : 
            case "since"      : 
            case "version"    : return "group_2";
            case "see"        : return "group_3";
            default           : return "group_4";
        }
    }
}