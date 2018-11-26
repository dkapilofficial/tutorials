package com.baeldung.algorithms.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringNonRepeatingCharacters {

    public static String getNonRepeatingCharactersBruteForce(String input) {
        String output = "";
        for (int start = 0; start < input.length(); start++) {
            Set<Character> visited = new HashSet<>();
            int end = start;
            for (; end < input.length(); end++) {
                char currChar = input.charAt(end);
                if (visited.contains(currChar)) {
                    break;
                } else {
                    visited.add(currChar);
                }
            }
            if (output.length() < end - start + 1) {
                output = input.substring(start, end);
            }
        }
        return output;
    }

    public static String getNonRepeatingCharacters(String input) {
        Map<Character, Integer> visited = new HashMap<>();
        String output = "";
        for (int start = 0, end = 0; end < input.length(); end++) {
            char currChar = input.charAt(end);
            if(visited.containsKey(currChar)) {
                start = Math.max(visited.get(currChar)+1, start);
            }
            if(output.length() < end - start + 1) {
                output = input.substring(start, end+1);
            }
            visited.put(currChar, end);
        }
        return output;
    }

    public static void main(String[] args) {
        String input = "CODINGISAWESOME";
        System.out.println(getNonRepeatingCharacters(input));
    }

}
