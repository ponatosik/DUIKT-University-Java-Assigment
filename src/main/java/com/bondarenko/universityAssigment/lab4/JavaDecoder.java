package com.bondarenko.universityAssigment.lab4;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JavaDecoder decodes simple encoded words.
 * <p> <b> NOTE: </b> This implementation assumes that character codes are placed in alphabetical order </p>
 * **/
public class JavaDecoder {
    private final static List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u', 'y');
    private final static List<Character> CONSONANTS = getConsonants();

    private final static Map<Character, Character> VOWEL_CODES = getVowelCodes();
    private final static Map<Character, Character> CONSONANT_CODES = getConsonantCodes();

    public static String decodeVowels (String word) {
        return decode(word, VOWEL_CODES);
    }

    public static String decodeConsonants (String word) {
        return decode(word, CONSONANT_CODES);
    }

    public static boolean isVowelsEncoded (String word) {
        return testDecoder(word, VOWEL_CODES);
    }

    public static boolean isConsonantsEncoded (String word) {
        return testDecoder(word, CONSONANT_CODES);
    }

    public static boolean testDecoder(String word, Map<Character, Character> codes) {
        Pattern pattern = getRegex(codes);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    private static String decode (String word, Map<Character, Character> codes) {
        StringBuilder builder = new StringBuilder();
        word.chars().forEach(charCode -> {
            char character = (char) charCode;
            builder.append(codes.getOrDefault(character, character));
        });
        return builder.toString();
    }

    private static List<Character> getConsonants(){
        return Stream.iterate('a', character -> (int)character <= (int)'z', character -> (char)((int) character + 1))
                .filter(Predicate.not(VOWELS::contains))
                .toList();
    }

    private static Map<Character, Character> getVowelCodes() {
        return VOWELS.stream().collect(Collectors.toMap(
                vowel -> digitToChar(VOWELS.indexOf(vowel) + 1),
                vowel -> vowel
        ));
    }

    private static Map<Character, Character> getConsonantCodes() {
        return CONSONANTS.stream().collect(Collectors.toMap(
                consonant -> CONSONANTS.get((CONSONANTS.indexOf(consonant) + 1) % CONSONANTS.size() ),
                consonant -> consonant
        ));
    }

    private static Pattern getRegex (Map<Character, Character> codes) {
        StringBuilder builder = new StringBuilder();
        builder.append(".*[");
        codes.keySet().forEach(builder::append);
        builder.append("]+.*");
        return Pattern.compile(builder.toString());
    }

    private static char digitToChar(int digit) {
        return (char)(digit + '0');
    }
}
