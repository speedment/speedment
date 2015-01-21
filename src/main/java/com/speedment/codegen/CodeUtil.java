package com.speedment.codegen;

import com.speedment.util.$;
import java.util.function.Function;

/**
 *
 * @author Duncan
 */
public class CodeUtil {
	/**
	 * Returns the specified text but with the first character lowercase.
	 * @param input The text.
	 * @return The resulting text.
	 */
	public static CharSequence lcfirst(CharSequence input) {
		return withFirst(input, (first) -> String.valueOf(Character.toUpperCase(first)));
	}
	
	/**
	 * Returns the specified text but with the first character uppercase.
	 * @param input The text.
	 * @return The resulting text.
	 */
	public static CharSequence ucfirst(CharSequence input) {
		return withFirst(input, (first) -> String.valueOf(Character.toUpperCase(first)));
	}
	
	public static CharSequence withFirst(CharSequence input, Function<Character, CharSequence> callback) {
		if (input == null) {
            return null;
        } else if (input.length() == 0) {
			return "";
		} else {
			return String.join(
				callback.apply(input.charAt(0)),
				input.subSequence(1, input.length())
			);
		}
	}
	
	/**
	 * Surrounds the specified text with brackets.
	 * @param text The text to surround.
	 * @return The text with a '{' before and a '}' afterwards.
	 */
	public static CharSequence tightBrackets(CharSequence text) {
		return new $(BS, text, BE);
	}
	
	/**
	 * Surrounds the specified text with brackets and put the content on a
	 * separate line.
	 * @param text The text to surround.
	 * @return The text with a '{\n' before and a '\n}' afterwards.
	 */
	public static CharSequence looseBrackets(CharSequence text) {
		return new $(BS, nl, text, nl, BE);
	}
	
	/**
	 * Indents the specified text, surrounds it with brackets and put the 
	 * content on a separate line.
	 * @param text The text to surround.
	 * @return The text with a '{\n' before and a '\n}' afterwards.
	 */
	public static CharSequence looseBracketsIndent(CharSequence text) {
		return new $(BS, nl, indent(text), nl, BE);
	}
	
	/**
	 * Indents one level after each new-line-character as defined by 
	 * <code>nl()</code>.
	 * @param text The text to indent.
	 * @return The indented text.
	 */
	public static CharSequence indent(CharSequence text) {
		return new $(tab, text.toString().replaceAll("\\r?\\n", nltab));
	}
	
	/**
	 * Returns the new-line-character as defined in <code>nl(String)</code>.
	 * @return The new-line character.
	 */
	public static CharSequence nl() {
		return nl;
	}
	
	/**
	 * Sets which new-line-character to use. Should be set before any code
	 * generation happens for indentation to work as expected.
	 * @param nl The new character to use.
	 */
	public static void nl(String nl) {
		CodeUtil.nl = nl;
		CodeUtil.nltab = nl + tab;
	}
	
	/**
	 * Returns the current tab character. This can be defined by
	 * using <code>tab(String)</code>.
	 * @return The current tab character.
	 */
	public static CharSequence tab() {
		return tab;
	}

	/**
	 * Sets the tab character to use when generating code. This should only be 
	 * called before any indentation occurs to prevent errors.
	 * @param tab The new tab character.
	 */
	public static void tab(String tab) {
		CodeUtil.tab = tab;
		CodeUtil.nltab = nl + tab;
	}
	
	private static String 
		nl = "\n",
		tab = "\t",
		nltab = "\n\t";
	
	public final static CharSequence 
		BS = "{", 
		BE = "}", 
		PS = "(",
		PE = ")",
		AS = "[",
		AE = "]",
		SPACE = " ",
		EMPTY = "",
		SC = ";";
}
