package com.shc.sesl.test.parser;

import com.shc.sesl.ast.expr.Expr;
import com.shc.sesl.ExprParser;
import com.shc.sesl.Parser;
import com.shc.sesl.Scanner;
import com.shc.sesl.Token;
import com.shc.sesl.TokenType;

import java.util.HashMap;
import java.util.Map;

import static com.shc.sesl.TokenType.*;
import static org.junit.Assert.*;

/**
 * A utility class just to help in unit testing the expressions that are parsed from the source code.
 *
 * @author Sri Harsha Chilakapati
 */
class ExprTestUtil
{
    private static final Map<TokenType, String> OPERATOR_LEXEMES = new HashMap<TokenType, String>() {{
        put(PLUS, "+");
        put(MINUS, "-");
        put(STAR, "*");
        put(SLASH, "/");
        put(PLUS_EQUALS, "+=");
        put(MINUS_EQUALS, "-=");
        put(STAR_EQUALS, "*=");
        put(SLASH_EQUALS, "/=");
        put(EQUAL_TO, "=");
        put(EQUALS, "==");
        put(NOT_EQUALS, "!=");
        put(LESSER, "<");
        put(GREATER, ">");
        put(LESSER_EQUALS, "<=");
        put(GREATER_EQUALS, ">=");
        put(LOGICAL_OR_EQUALS, "||=");
        put(LOGICAL_AND_EQUALS, "&&=");
        put(INCREMENT, "++");
        put(DECREMENT, "--");
        put(NOT, "!");
    }};
    
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
        assertEquals(correct.toString(), expr.toString());
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
        return new Token(operator, OPERATOR_LEXEMES.get(operator), null, 0, 0);
    }

    /**
     * Utility function to create an identifier token.
     *
     * @param name The name of the identifier.
     * @return The identifier token.
     */
    static Token createIdentifierToken(String name)
    {
        return new Token(TokenType.IDENTIFIER, name, name, 0, 0);
    }
}
