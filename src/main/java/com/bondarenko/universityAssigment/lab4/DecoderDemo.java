package com.bondarenko.universityAssigment.lab4;

import java.util.List;
import java.util.Scanner;

public class DecoderDemo {
    private final static List<String> EXAMPLES = List.of(
            "s4m2 2nc4d2d t2xt w3th m2th4d f4r v4w2ls",         // Some encoded text with method for vowels
            "tone epdofef vezv xivj nevjof gos doptopapvt",     // Some encoded text with method for consonants
            "s4m2 epdofef t2xt xivj b4th nevjoft"               // Some encoded text with both methods
    );

    public static void main(String[] args) {
        System.out.println("Type in text you want to decode, or enter nothing to see prepared examples");
        System.out.println("Note: it doesn't decode UPPERCASE letters");
        String input = new Scanner(System.in).nextLine();

        if(input.isEmpty() || input.isBlank())
        {
            System.out.println("Examples:");
            for (String text : EXAMPLES) {
                System.out.println(text + " -> " + JavaDecoder.decodeText(text));
            }
        }
        else
        {
            System.out.println("Decoded message:");
            System.out.println(JavaDecoder.decodeText(input));
        }
    }


}
