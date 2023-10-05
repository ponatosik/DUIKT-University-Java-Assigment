package com.bondarenko.universityAssigment.lab4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class JavaDecoderTest {
    @ParameterizedTest
    @ValueSource(strings = {"t2st", "epdofef", "t2xt", "xivj", "b4th", "nevjoft"})
    void IsEncoded_EncodedWord_ShouldReturnTrue(String decodedWord) {
        var actual = JavaDecoder.isEncoded(decodedWord);

        assertTrue(actual);
    }

    @ParameterizedTest
    @CsvSource({"t2st,test", "616,yay", "f44,foo", "123456,aeiouy"})
    public void Decode_EncodedWordWithVowelMethod_ShouldReturnDecoded(String vowelEncodedWord, String expected) {
        var actual = JavaDecoder.decode(vowelEncodedWord);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"vetv,test", "yay,yay", "goo,foo", "cdfghjklmnpqrstvwxzb,bcdfghjklmnpqrstvwxz"})
    public void Decode_EncodedWordWithConsonantMethod_ShouldReturnDecoded(String consonantEncodedWord, String expected) {
        var actual = JavaDecoder.decode(consonantEncodedWord);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "s4m2 2nc4d2d t2xt w3th m2th4d f4r v4w2ls,some encoded text with method for vowels",
            "tone epdofef vezv xivj nevjof gos doptopapvt,some encoded text with method for consonants",
            "s4m2 epdofef t2xt xivj b4th nevjoft,some encoded text with both methods"
    })
    public void DecodeText_EncodedWordWithVowelMethod_ShouldReturnDecoded(String encodedText, String expected) {
        var actual = JavaDecoder.decodeText(encodedText);

        assertEquals(expected, actual);
    }

}