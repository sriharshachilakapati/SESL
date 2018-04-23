package com.shc.sesl.test.parser;

import com.shc.sesl.Expr;
import com.shc.sesl.ExprParser;
import com.shc.sesl.Parser;
import com.shc.sesl.Scanner;
import com.shc.sesl.Token;
import com.shc.sesl.TokenType;

import static org.junit.Assert.*;

/**
 * A utility class just to help in unit testing the expressions that are parsed from the source code.
 *
 * @author Sri Harsha Chilakapati
 */
class ExprTestUtil
{
    /**
     * Assert whether a given input string produces a given expression.
     *
     * @param input   The expression in source string to test for.
     * @param correct The correct expression tree that it should produce after parsing.
     */
    static void assertExpr(String input, Expr correct)
    {
        ExprParser parser = new ExprParser(new Parser(new Scanner(input).scanTokens()));
        Expr expr = parser.parse();
        assertEquals(expr.toString(), correct.toString());
    }

    /**
     * Utility function to create operator token
     *
     * @param operator The operator to create
     *
     * @return The created token object
     */
    static Token createOperatorToken(TokenType operator)
    {
        String lexeme = null;

        switch (operator)
        {
            case PLUS:
                lexeme = "+";
                break;
            case MINUS:
                lexeme = "-";
                break;
            case STAR:
                lexeme = "*";
                break;
            case SLASH:
                lexeme = "/";
                break;
            case PLUS_EQUALS:
                lexeme = "+=";
                break;
            case MINUS_EQUALS:
                lexeme = "-=";
                break;
            case STAR_EQUALS:
                lexeme = "*=";
                break;
            case SLASH_EQUALS:
                lexeme = "/=";
                break;
        }

        return new Token(operator, lexeme, null, 0, 0);
    }
}
