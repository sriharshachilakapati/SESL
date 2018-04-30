package com.shc.sesl;

import com.shc.sesl.ast.expr.Expr;
import com.shc.sesl.ast.stmt.Stmt;

import java.util.List;

import static com.shc.sesl.TokenType.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class Parser
{
    private final List<Token> tokens;
    private int current = 0;

    private final ExprParser exprParser;
    private final StmtParser stmtParser;

    public Parser(List<Token> tokens)
    {
        this.tokens = tokens;
        this.exprParser = new ExprParser(this);
        this.stmtParser = new StmtParser(this);
    }

    static ParseError error(Token token, String message)
    {
        SESL.error(token, message);
        return new ParseError();
    }

    public Expr parseExpression()
    {
        return exprParser.parse();
    }

    public Stmt parseStatement()
    {
        return stmtParser.parse();
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

    boolean isAtEnd()
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
