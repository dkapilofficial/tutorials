/**
 * 
 */
package com.baeldung.string;

/**
 * @author iaforek
 *
 */
public class RemoveLastChar {
	public static String substring (String s) {
		if (s == null || s.length() == 0) {
			return null;
		} else {
			return (s.substring(0, s.length() - 1));
		}
	}
}
