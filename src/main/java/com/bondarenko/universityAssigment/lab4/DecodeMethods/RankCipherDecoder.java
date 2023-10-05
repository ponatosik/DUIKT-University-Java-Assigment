package com.bondarenko.universityAssigment.lab4.DecodeMethods;

import com.bondarenko.universityAssigment.lab4.Decoder;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RankCipherDecoder implements Decoder {
    private final Map<Character, Character> cipherCodes;
    private final Pattern encodedTestPatter;

    public RankCipherDecoder(List<Character> cipherAlphabet) {
        cipherCodes =  cipherAlphabet.stream().collect(Collectors.toMap(
                vowel -> digitToChar(cipherAlphabet.indexOf(vowel) + 1),
                vowel -> vowel
        ));
        encodedTestPatter = compileTestPattern();
    }

    public String decode(String word) {
        StringBuilder builder = new StringBuilder();
        word.chars().forEach(charCode -> {
            char character = (char) charCode;
            builder.append(cipherCodes.getOrDefault(character, character));
        });
        return builder.toString();
    }

    public boolean isEncoded(String word) {
        return encodedTestPatter.matcher(word).matches();
    }

    private static char digitToChar(int digit) {
        return (char)(digit + '0');
    }

    private Pattern compileTestPattern() {
        StringBuilder builder = new StringBuilder();
        builder.append(".*[");
        cipherCodes.keySet().forEach(builder::append);
        builder.append("]+.*");
        return Pattern.compile(builder.toString());
    }
}
