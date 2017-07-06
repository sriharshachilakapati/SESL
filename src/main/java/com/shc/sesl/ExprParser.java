package com.shc.sesl;

import static com.shc.sesl.TokenType.*;

/**
 * @author Sri Harsha Chilakapati
 */
class ExprParser
{
    private Parser parser;

    ExprParser(Parser parser)
    {
        this.parser = parser;
    }

    Expr parse()
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

        if (parser.match(EQUAL_TO, PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS,
                SLASH_EQUALS, LOGICAL_OR_EQUALS, LOGICAL_AND_EQUALS))
        {
            Token operator = parser.previous();
            Expr value = parseAssignment();

            if (expr instanceof Expr.Variable)
            {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(operator, name, value);
            }

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
            expr = new Expr.Logical(expr, operator, right);
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
            expr = new Expr.Logical(expr, operator, right);
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
            expr = new Expr.Binary(expr, operator, right);
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
            expr = new Expr.Binary(expr, operator, right);
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
            expr = new Expr.Binary(expr, operator, right);
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
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr parseUnary()
    {
        // pre_unary = ( "!" | "-" ) unary
        //           | ( "++" | "--" ) identifier
        //           | primary ;
        if (parser.match(NOT, MINUS))
        {
            Token operator = parser.previous();
            Expr right = parseUnary();
            return new Expr.PreUnary(operator, right);
        }

        if (parser.match(INCREMENT, DECREMENT))
        {
            Token operator = parser.previous();
            Expr right = parsePrimary();

            if (right instanceof Expr.Variable)
                return new Expr.PreUnary(operator, right);

            throw Parser.error(parser.peek(), "Expect a variable.");
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
        if (parser.match(INTEGER_VALUE, FLOAT_VALUE))
            return new Expr.Literal(parser.previous().literal);

        if (parser.match(IDENTIFIER))
        {
            Expr variable = new Expr.Variable(parser.previous());

            if (parser.match(INCREMENT, DECREMENT))
                return new Expr.PostUnary(variable, parser.previous());

            return variable;
        }

        if (parser.match(FALSE))
            return new Expr.Literal(false);

        if (parser.match(TRUE))
            return new Expr.Literal(true);

        if (parser.match(LEFT_PAREN))
        {
            Expr expr = parse();
            parser.consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw Parser.error(parser.peek(), "Expect an expression.");
    }
}
