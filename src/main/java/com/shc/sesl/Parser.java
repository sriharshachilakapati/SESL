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

    private static ParseError error(Token token, String message)
    {
        SESL.error(token, message);
        return new ParseError();
    }

    public Expr parseExpression()
    {
        // expression = assignment ;
        return parseAssignment();
    }

    private Expr parseAssignment()
    {
        // assignment = identifier "=" assignment
        //            | identifier "+=" assignment
        //            | identifier "-=" assignment
        //            | identifier "*=" assignment
        //            | identifier "/=" assignment
        //            | identifier "||=" assignment
        //            | identifier "&&=" assignment
        //            | logic_or ;
        Expr expr = parseOr();

        if (match(EQUAL_TO, PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS,
                SLASH_EQUALS, LOGICAL_OR_EQUALS, LOGICAL_AND_EQUALS))
        {
            Token operator = previous();
            Expr value = parseAssignment();

            if (expr instanceof Expr.Variable)
            {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(operator, name, value);
            }

            error(operator, "Invalid assignment target.");
        }

        return expr;
    }

    private Expr parseOr()
    {
        // logic_or = logic_and ( "||" logic_and )* ;
        Expr expr = parseAnd();

        while (match(LOGICAL_OR))
        {
            Token operator = previous();
            Expr right = parseAnd();
            expr = new Expr.Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr parseAnd()
    {
        // logic_and = equality ( "&&" equality )* ;
        Expr expr = parseEquality();

        while (match(LOGICAL_AND))
        {
            Token operator = previous();
            Expr right = parseEquality();
            expr = new Expr.Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr parseEquality()
    {
        // equality = comparison ( ( "!=" | "==" ) comparison )* ;
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
        // comparison = term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
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
        // term = factor ( ( "-" | "+" ) factor )* ;
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
        // factor = unary ( ( "/" | "*" ) unary )* ;
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
        // pre_unary = ( "!" | "-" ) unary
        //           | ( "++" | "--" ) identifier
        //           | primary ;
        if (match(NOT, MINUS))
        {
            Token operator = previous();
            Expr right = parseUnary();
            return new Expr.PreUnary(operator, right);
        }

        if (match(INCREMENT, DECREMENT))
        {
            Token operator = previous();
            Expr right = parsePrimary();

            if (right instanceof Expr.Variable)
                return new Expr.PreUnary(operator, right);

            throw error(peek(), "Expect a variable.");
        }

        return parsePrimary();
    }

    private Expr parsePrimary()
    {
        // primary = INTEGER_VALUE | FLOAT_VALUE | "false" | "true"
        //         | IDENTIFIER "++"
        //         | IDENTIFIER "--"
        //         | IDENTIFIER
        //         | "(" expression ")" ;
        if (match(INTEGER_VALUE, FLOAT_VALUE))
            return new Expr.Literal(previous().literal);

        if (match(IDENTIFIER))
        {
            Expr variable = new Expr.Variable(previous());

            if (match(INCREMENT, DECREMENT))
                return new Expr.PostUnary(variable, previous());

            return variable;
        }

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
    {
    }
}
