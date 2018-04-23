package com.shc.sesl.test.parser;

import com.shc.sesl.Expr;
import com.shc.sesl.TokenType;
import org.junit.Test;

import static com.shc.sesl.test.parser.ExprTestUtil.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class ExpressionTest
{
    @Test
    public void testAdditionExpression()
    {
        final String source = "2 + 2;";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(2),
                createOperatorToken(TokenType.PLUS),
                new Expr.Literal(2));

        assertExpr(source, correct);
    }

    @Test
    public void testSubtractionExpression()
    {
        final String source = "2 - 2;";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(2),
                createOperatorToken(TokenType.MINUS),
                new Expr.Literal(2));

        assertExpr(source, correct);
    }

    @Test
    public void testMultiplicationExpression()
    {
        final String source = "4 * 2;";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(4),
                createOperatorToken(TokenType.STAR),
                new Expr.Literal(2));

        assertExpr(source, correct);
    }

    @Test
    public void testDivisionExpression()
    {
        final String source = "4 / 2;";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(4),
                createOperatorToken(TokenType.SLASH),
                new Expr.Literal(2));

        assertExpr(source, correct);
    }
}
