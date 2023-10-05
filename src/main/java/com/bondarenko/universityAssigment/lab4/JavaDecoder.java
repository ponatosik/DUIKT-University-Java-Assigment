package com.bondarenko.universityAssigment.lab4;

import com.bondarenko.universityAssigment.lab4.DecodeMethods.CaesarCipherDecoder;
import com.bondarenko.universityAssigment.lab4.DecodeMethods.RankCipherDecoder;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * JavaDecoder decodes simple encoded words.
 * <p> <b> NOTE: </b> This implementation assumes that character codes are placed in alphabetical order </p>
 * **/
public class JavaDecoder {
    private final static List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u', 'y');
    private final static List<Character> CONSONANTS = getConsonants();

    private final static Decoder vowelsDecoder = new RankCipherDecoder(VOWELS);
    private final static Decoder consonantDecoder = new CaesarCipherDecoder(CONSONANTS);

    public static String decode(String word) {
        if(vowelsDecoder.isEncoded(word))
        {
            return vowelsDecoder.decode(word);
        }
        else if(consonantDecoder.isEncoded(word))
        {
            return consonantDecoder.decode(word);
        }
        return word;
    }

    public static boolean isEncoded(String word) {
        return vowelsDecoder.isEncoded(word) || consonantDecoder.isEncoded(word);
    }

    private static List<Character> getConsonants(){
        return Stream.iterate('a', character -> (int)character <= (int)'z', character -> (char)((int) character + 1))
                .filter(Predicate.not(VOWELS::contains))
                .toList();
    }
}
