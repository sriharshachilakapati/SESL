package com.shc.sesl;

import static com.shc.sesl.TokenType.EOF;

/**
 * @author Sri Harsha Chilakapati
 */
public class SESL
{
    private static boolean hadError = false;

    static void error(int line, int column, String message)
    {
        report(line, column, "", message);
    }

    static void error(Token token, String message)
    {
        if (token.type == EOF)
            report(token.line, token.column," at end", message);
        else
            report(token.line, token.column," at '" + token.lexeme + "'", message);
    }

    private static void report(int line, int column, String where, String message)
    {
        System.err.println(String.format("[line %d, col %d] Error %s: %s", line, column, where, message));
        hadError = true;
    }
}
