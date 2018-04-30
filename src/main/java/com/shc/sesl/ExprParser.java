package com.shc.sesl;

import com.shc.sesl.ast.expr.*;

import static com.shc.sesl.TokenType.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class ExprParser
{
    private final Parser parser;

    public ExprParser(Parser parser)
    {
        this.parser = parser;
    }

    public Expr parse()
    {
        // expression = assignment ;
        return parseAssignment();
    }

    private Expr parseAssignment()
    {
        // assignment = variable ( "=" | "+=" | "-=" | "*=" | "/=" | "||=" | "&&=" ) assignment
        //            | logic_or ;
        Expr expr = parseOr();

        if (parser.match(EQUAL_TO, PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS,
                SLASH_EQUALS, LOGICAL_OR_EQUALS, LOGICAL_AND_EQUALS))
        {
            Token operator = parser.previous();
            Expr value = parseAssignment();

            if (expr instanceof Variable)
                return new Assign((Variable) expr, operator, value);

            throw Parser.error(operator, "Invalid assignment target.");
        }

        return expr;
    }

    private Expr parseOr()
    {
        // logic_or = logic_and ( "||" logic_and )* ;
        Expr expr = parseAnd();

        while (parser.match(LOGICAL_OR))
        {
            Token operator = parser.previous();
            Expr right = parseAnd();
            expr = new Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr parseAnd()
    {
        // logic_and = equality ( "&&" equality )* ;
        Expr expr = parseEquality();

        while (parser.match(LOGICAL_AND))
        {
            Token operator = parser.previous();
            Expr right = parseEquality();
            expr = new Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr parseEquality()
    {
        // equality = comparison ( ( "!=" | "==" ) comparison )* ;
        Expr expr = parseComparison();

        while (parser.match(NOT_EQUALS, EQUALS))
        {
            Token operator = parser.previous();
            Expr right = parseComparison();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseComparison()
    {
        // comparison = term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
        Expr expr = parseTerm();

        while (parser.match(GREATER, GREATER_EQUALS, LESSER, LESSER_EQUALS))
        {
            Token operator = parser.previous();
            Expr right = parseTerm();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseTerm()
    {
        // term = factor ( ( "-" | "+" ) factor )* ;
        Expr expr = parseFactor();

        while (parser.match(MINUS, PLUS))
        {
            Token operator = parser.previous();
            Expr right = parseFactor();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseFactor()
    {
        // factor = unary ( ( "/" | "*" ) unary )* ;
        Expr expr = parseUnary();

        while (parser.match(SLASH, STAR))
        {
            Token operator = parser.previous();
            Expr right = parseUnary();
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseUnary()
    {
        // unary = ( "!" | "-" ) unary
        //       | ( "++" | "--" ) variable
        //       | primary ;
        if (parser.match(NOT, MINUS))
        {
            Token operator = parser.previous();
            Expr right = parseUnary();
            return new PreUnary(operator, right);
        }

        if (parser.match(INCREMENT, DECREMENT))
        {
            Token operator = parser.previous();
            Expr right = parsePrimary();

            if (right instanceof Variable)
                return new PreUnary(operator, right);

            throw Parser.error(parser.peek(), "Expect a variable.");
        }

        return parsePrimary();
    }

    private Expr parsePrimary()
    {
        // primary = INTEGER_VALUE | FLOAT_VALUE | "false" | "true"
        //         | variable ( "++" | "--" )?
        //         | "(" expression ")" ;
        if (parser.match(INTEGER_VALUE, FLOAT_VALUE))
            return new Literal(parser.previous().literal);

        if (parser.match(FALSE))
            return new Literal(false);

        if (parser.match(TRUE))
            return new Literal(true);

        if (parser.match(IDENTIFIER))
        {
            Expr variable = parseVariable();

            if (parser.match(INCREMENT, DECREMENT))
                return new PostUnary(variable, parser.previous());

            return variable;
        }

        if (parser.match(LEFT_PAREN))
        {
            Expr expr = parse();
            parser.consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Grouping(expr);
        }

        throw Parser.error(parser.peek(), "Expect an expression.");
    }

    private Expr parseVariable()
    {
        // variable = IDENTIFIER ( "." IDENTIFIER )* ;
        Expr variable = new Variable(parser.previous());

        while (parser.match(DOT))
        {
            if (parser.match(IDENTIFIER))
                variable = new Variable(parser.previous(), (Variable) variable);
            else
                throw Parser.error(parser.peek(), "Expected a variable.");
        }

        return variable;
    }
}
