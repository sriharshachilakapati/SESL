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

    static ParseError error(Token token, String message)
    {
        SESL.error(token, message);
        return new ParseError();
    }

    public Expr parseExpression()
    {
        return new ExprParser(this).parse();
    }

    Token consume(TokenType tokenType, String message)
    {
        if (check(tokenType))
            return advance();

        throw error(peek(), message);
    }

    boolean match(TokenType... types)
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
        return !isAtEnd() && peek().type == type;
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

    Token peek()
    {
        return tokens.get(current);
    }

    Token previous()
    {
        return tokens.get(current - 1);
    }

    private static class ParseError extends RuntimeException
    {
    }
}
