package com.shc.sesl.test.scanner;

import org.junit.Test;

import static com.shc.sesl.TokenType.*;
import static com.shc.sesl.test.scanner.ScannerTestUtil.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class ValuesScanTest
{
    @Test
    public void testIntegerValue1()
    {
        assertTokens("1234", INTEGER_VALUE, EOF);
        assertLexemes("1234", "1234", "");
        assertLiterals("1234", 1234, null);
    }

    @Test
    public void testIntegerValue2()
    {
        assertTokens("10_000_000", INTEGER_VALUE, EOF);
        assertLexemes("10_000_000", "10_000_000", "");
        assertLiterals("10_000_000", 10000000, null);
    }

    @Test
    public void testIntegerValue3()
    {
        assertTokens("0xFFFFFF", INTEGER_VALUE, EOF);
        assertLexemes("0xFFFFFF", "0xFFFFFF", "");
        assertLiterals("0xFFFFFF", 0xFFFFFF, null);
    }

    @Test
    public void testIntegerValue4()
    {
        assertTokens("0117", INTEGER_VALUE, EOF);
        assertLexemes("0117", "0117", "");
        assertLiterals("0117", 0117, null);
    }

    @Test
    public void testIntegerValue5()
    {
        assertTokens("0b1011110", INTEGER_VALUE, EOF);
        assertLexemes("0b1011110", "0b1011110", "");
        assertLiterals("0b1011110", 0b1011110, null);
    }

    @Test
    public void testFloatValue1()
    {
        assertTokens("3.14", FLOAT_VALUE, EOF);
        assertLexemes("3.14", "3.14", "");
        assertLiterals("3.14", 3.14f, null);
    }
}
