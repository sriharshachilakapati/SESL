package com.shc.sesl.test.scanner;

import org.junit.Test;

import static com.shc.sesl.TokenType.*;
import static com.shc.sesl.test.scanner.ScannerTestUtil.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class IdentifiersTest
{
    @Test
    public void testIdentifierName()
    {
        assertTokens("hello", IDENTIFIER, EOF);
        assertLexemes("hello", "hello", "");
        assertLiterals("hello", "hello", null);
    }

    @Test
    public void testIdentifierWithNumbers()
    {
        assertTokens("hello53", IDENTIFIER, EOF);
        assertLexemes("hello53", "hello53", "");
        assertLiterals("hello53", "hello53", null);
    }

    @Test
    public void testSpaceSeparatedIdentifiers()
    {
        assertTokens("hello world", IDENTIFIER, IDENTIFIER, EOF);
        assertLexemes("hello world", "hello", "world", "");
        assertLiterals("hello world", "hello", "world", null);
    }

    @Test
    public void testUnderscoresInIdentifier()
    {
        assertTokens("hello_world", IDENTIFIER, EOF);
        assertLexemes("hello_world", "hello_world", "");
        assertLiterals("hello_world", "hello_world", null);
    }

    @Test
    public void testIdentifiersStartingWithUnderscore()
    {
        assertTokens("_testID", IDENTIFIER, EOF);
        assertLexemes("_testID", "_testID", "");
        assertLiterals("_testID", "_testID", null);
    }

    @Test
    public void testIdentifiersFollowedByNumbers()
    {
        assertTokens("98Hello", INTEGER_VALUE, IDENTIFIER, EOF);
        assertLexemes("98Hello", "98", "Hello", "");
        assertLiterals("98Hello", 98, "Hello", null);
    }
}
