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
package com.speedment.internal.util;

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pemi
 */
public enum Pluralis {

    INSTANCE;

    private final List<Rule> rules;
    private final Set<String> uncountables;
    private final Map<String, String> irregulars;

    Pluralis() {
        rules = new ArrayList<>();
        uncountables = new HashSet<>();
        irregulars = new HashMap<>();
        init();
        Collections.reverse(rules); // Search the general rules last
    }

    public String pluralizeJavaIdentifier(String javaIdentifier) {
        requireNonNull(javaIdentifier);
        int lastCapitalCharacter = -1;
        for (int i = 0; i < javaIdentifier.length(); i++) {
            if (Character.isUpperCase(javaIdentifier.charAt(i))) {
                lastCapitalCharacter = i;
            }
        }
        if (lastCapitalCharacter == -1) {
            return pluralize(javaIdentifier);
        } else {
            final String firstPartOfWord = javaIdentifier.substring(0, lastCapitalCharacter);
            final String lastPartOfWord = javaIdentifier.substring(lastCapitalCharacter);
            final String pluralOflastPartOfWord = pluralize(lastPartOfWord);
            return firstPartOfWord + JavaLanguage.javaTypeName(pluralOflastPartOfWord);
        }
    }

    /**
     * Returns a plural version in normalized form of the given word.
     *
     * @param word the given singularis word
     * @return a plural version in normalized form of the given word
     */
    public String pluralize(String word) {
        requireNonNull(word);
        final String normalized = normalize(word);
        if (normalized.length() == 0) {
            return normalized;
        }
        if (isUncountable(normalized)) {
            return normalized;
        }
        if (isIrregular(word)) {
            return irregulars.get(normalized);
        }

        for (Rule rule : rules) {
            final Optional<String> result = rule.apply(normalized);
            if (result.isPresent()) {
                return result.get();
            }
        }
        return normalized;
    }

    /**
     * Returns a textual representation of an ordinal. 1 = 1st, 2 = 2nd, 101
     * = 101st, 111 = 111th
     *
     * @param number the ordinal number
     * @return a textual representation of an ordinal
     */
    public String ordinalize(final int number) {
        final int remainder = Math.abs(number % 100);
        final String numberStr = Integer.toString(number);
        if (remainder < 10) {
            if (remainder == 1) {
                return numberStr + "st";
            }
            if (remainder == 2) {
                return numberStr + "nd";
            }
            if (remainder == 3) {
                return numberStr + "rd";
            }
        }
        return numberStr + "th";
    }

    /**
     * Returns if the provided word is considered as an uncountable.
     *
     * @param word to check
     * @return if the provided word is considered as an uncountable
     */
    public boolean isUncountable(String word) {
        requireNonNull(word);
        return uncountables.contains(normalize(word));
    }

    public boolean isIrregular(String word) {
        requireNonNull(word);
        return irregulars.containsKey(normalize(word));
    }

    protected void addRule(String rule, String replacement) {
        requireNonNulls(rule, replacement);
        rules.add(new Rule(rule, replacement));
    }

    protected void addIrregular(String singular, String plural) {
        requireNonNulls(singular, plural);
        irregulars.put(normalize(singular), normalize(plural));
    }

    protected void addUncountable(String... words) {
        requireNonNulls(words);
        Arrays.asList(words).stream().map(normalizeMapper()).forEach(uncountables::add);
    }

    private String normalize(String input) {
        requireNonNull(input);
        return normalizeMapper().apply(input);
    }

    public Function<String, String> normalizeMapper() {
        return s -> Optional.ofNullable(s).orElse("").trim().toLowerCase();
    }

    private void init() {
        addRule("$", "s");
        addRule("(.+)s$", "$1ses");
        addRule("(ax|test)is$", "$1es");
        addRule("(octop)us$", "$1i");
        addRule("(octop|vir)i$", "$1i");
        addRule("(alias|status)$", "$1es");
        addRule("(bu)s$", "$1ses");
        addRule("(buffal|tomat)o$", "$1oes");
        addRule("([ti])um$", "$1a");
        addRule("([ti])a$", "$1a");
        addRule("sis$", "ses");
        addRule("(?:([^f])fe|([lr])f)$", "$1$2ves");
        addRule("(hive)$", "$1s");
        addRule("([^aeiouy]|qu)y$", "$1ies");
        addRule("(x|ch|ss|sh)$", "$1es");
        addRule("(matr|vert|ind)ix|ex$", "$1ices");
        addRule("([m|l])ouse$", "$1ice");
        addRule("([m|l])ice$", "$1ice");
        addRule("^(ox)$", "$1en");
        addRule("(quiz)$", "$1zes");

        addIrregular("woman", "women");
        addIrregular("man", "men");
        addIrregular("child", "children");
        addIrregular("sex", "sexes");
        addIrregular("person", "people");

        addUncountable("furniture", "equipment", "information", "rice", "money", "species", "series", "fish", "sheep", "data");
    }

    private final class Rule implements Function<String, Optional<String>> {

        protected final String expression;
        protected final Pattern expressionPattern;
        protected final String replacement;

        private Rule(final String expression, final String replacement) {
            this.expression = requireNonNull(expression);
            this.replacement =requireNonNull(replacement);
            this.expressionPattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        }

        /**
         * Apply the rule against the input string, returning the modified
         * string or null if the rule didn't apply (and no modifications were
         * made)
         *
         * @param input the input string
         * @return the modified string if this rule applied, or null if the
         * input was not modified by this rule
         */
        @Override
        public Optional<String> apply(String input) {
            final Matcher matcher = expressionPattern.matcher(input);
            if (!matcher.find()) {
                return Optional.empty();
            }
            return Optional.ofNullable(matcher.replaceAll(replacement));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 41 * hash + Objects.hashCode(expression);
            hash = 41 * hash + Objects.hashCode(replacement);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Rule other = (Rule) obj;
            if (!Objects.equals(this.expression, other.expression)) {
                return false;
            }
            if (!Objects.equals(this.replacement, other.replacement)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return expression + " -> " + replacement;
        }
    }

}
