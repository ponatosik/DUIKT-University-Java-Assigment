package com.bondarenko.universityAssigment.lab4.DecodeMethods;

import com.bondarenko.universityAssigment.lab4.Decoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CaesarCipherDecoder implements Decoder {
    private final List<Character> cipherAlphabet;
    private final Map<Character, Character> cipherCodes;
    private final Pattern encodedTestPatter;

    public CaesarCipherDecoder(List<Character> cipherAlphabet) {
        this.cipherAlphabet = new ArrayList<>(cipherAlphabet);
        cipherCodes =  cipherAlphabet.stream().collect(Collectors.toMap(
                consonant -> cipherAlphabet.get((cipherAlphabet.indexOf(consonant) + 1) % cipherAlphabet.size() ),
                consonant -> consonant
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

    private Pattern compileTestPattern() {
        StringBuilder builder = new StringBuilder();
        builder.append(".*[");
        cipherAlphabet.forEach(builder::append);
        builder.append("]+.*");
        return Pattern.compile(builder.toString());
    }
}
