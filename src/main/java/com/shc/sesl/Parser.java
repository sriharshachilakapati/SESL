package com.shc.sesl;

import java.util.List;

import static com.shc.sesl.TokenType.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class Parser
{
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens)
    {
        this.tokens = tokens;
    }

    public Expr parseExpression()
    {
        return parseEquality();
    }

    private Expr parseEquality()
    {
        // equality → comparison ( ( "!=" | "==" ) comparison )*
        Expr expr = parseComparison();

        while (match(NOT_EQUALS, EQUALS))
        {
            Token operator = previous();
            Expr right = parseComparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseComparison()
    {
        // comparison → term ( ( ">" | ">=" | "<" | "<=" ) term )*
        Expr expr = parseTerm();

        while (match(GREATER, GREATER_EQUALS, LESSER, LESSER_EQUALS))
        {
            Token operator = previous();
            Expr right = parseTerm();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseTerm()
    {
        // term → factor ( ( "-" | "+" ) factor )*
        Expr expr = parseFactor();

        while (match(MINUS, PLUS))
        {
            Token operator = previous();
            Expr right = parseFactor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseFactor()
    {
        // factor → unary ( ( "/" | "*" ) unary )*
        Expr expr = parseUnary();

        while (match(SLASH, STAR))
        {
            Token operator = previous();
            Expr right = parseUnary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseUnary()
    {
        // unary → ( "!" | "-" ) unary
        //       | primary
        if (match(NOT, MINUS))
        {
            Token operator = previous();
            Expr right = parseUnary();
            return new Expr.Unary(operator, right);
        }

        return parsePrimary();
    }

    private Expr parsePrimary()
    {
        // primary → INTEGER_VALUE | FLOAT_VALUE | "false" | "true"
        //         | "(" expression ")"
        if (match(INTEGER_VALUE, FLOAT_VALUE))
            return new Expr.Literal(previous().literal);

        if (match(FALSE))
            return new Expr.Literal(false);

        if (match(TRUE))
            return new Expr.Literal(true);

        if (match(LEFT_PAREN))
        {
            Expr expr = parseExpression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect an expression.");
    }

    private Token consume(TokenType tokenType, String message)
    {
        if (check(tokenType))
            return advance();

        throw error(peek(), message);
    }

    private static ParseError error(Token token, String message)
    {
        SESL.error(token, message);
        return new ParseError();
    }

    private boolean match(TokenType... types)
    {
        for (TokenType type : types)
            if (check(type))
            {
                advance();
                return true;
            }

        return false;
    }

    private boolean check(TokenType type)
    {
        if (isAtEnd())
            return false;

        return peek().type == type;
    }

    private Token advance()
    {
        if (!isAtEnd())
            current++;

        return previous();
    }

    private boolean isAtEnd()
    {
        return peek().type == EOF;
    }

    private Token peek()
    {
        return tokens.get(current);
    }

    private Token previous()
    {
        return tokens.get(current - 1);
    }

    private static class ParseError extends RuntimeException
    {}
}
