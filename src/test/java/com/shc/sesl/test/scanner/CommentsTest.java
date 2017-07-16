package com.shc.sesl.test.scanner;

import org.junit.Test;

import static com.shc.sesl.TokenType.*;
import static com.shc.sesl.test.scanner.ScannerTestUtil.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class CommentsTest
{
    @Test
    public void testEmptySourceShouldGenerateEOF()
    {
        assertTokens("", EOF);
    }

    @Test
    public void testSkipSingleLineComments1()
    {
        assertTokens("// Hello World!!!", EOF);
    }

    @Test
    public void testSkipSingleLineComments2()
    {
        assertTokens("// Hello\n1// World", INTEGER_VALUE, EOF);
    }

    @Test
    public void testSkipMultiLineComments1()
    {
        assertTokens("/* Hello */ 1 /* World */", INTEGER_VALUE, EOF);
    }

    @Test
    public void testSkipMultiLineComments2()
    {
        assertTokens("/* Hello\nWorld */", EOF);
    }
}
