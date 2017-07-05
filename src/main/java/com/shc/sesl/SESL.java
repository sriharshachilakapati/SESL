package com.shc.sesl;

import static com.shc.sesl.TokenType.EOF;

/**
 * @author Sri Harsha Chilakapati
 */
public class SESL
{
    private static boolean hadError = false;

    static void error(int line, String message)
    {
        report(line, "", message);
    }

    static void error(Token token, String message)
    {
        if (token.type == EOF)
            report(token.line, " at end", message);
        else
            report(token.line, " at '" + token.lexeme + "'", message);
    }

    private static void report(int line, String where, String message)
    {
        System.err.println(String.format("[line %d] Error %s: %s", line, where, message));
        hadError = true;
    }
}
