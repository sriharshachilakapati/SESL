package com.shc.sesl.test.scanner;

import com.shc.sesl.Scanner;
import com.shc.sesl.Token;
import com.shc.sesl.TokenType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Sri Harsha Chilakapati
 */
class ScannerTestUtil
{
    static void assertTokens(String input, TokenType... tokenTypes)
    {
        List<Token> tokens = new Scanner(input).scanTokens();
        assertEquals(String.format("Expected: %s\nActual: %s", Arrays.toString(tokenTypes), tokens), tokenTypes.length, tokens.size());

        for (int i = 0; i < tokens.size(); i++)
            assertEquals(tokenTypes[i], tokens.get(i).type);
    }

    static void assertLexemes(String input, String... lexemes)
    {
        List<Token> tokens = new Scanner(input).scanTokens();
        assertEquals(String.format("Expected: %s\nActual: %s", Arrays.toString(lexemes), tokens), lexemes.length, tokens.size());

        for (int i = 0; i < tokens.size(); i++)
            assertEquals(lexemes[i], tokens.get(i).lexeme);
    }

    static void assertLiterals(String input, Object... literals)
    {
        List<Token> tokens = new Scanner(input).scanTokens();
        assertEquals(String.format("Expected: %s\nActual: %s", Arrays.toString(literals), tokens), literals.length, tokens.size());

        for (int i = 0; i < tokens.size(); i++)
            assertEquals(literals[i], tokens.get(i).literal);
    }
}
