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

    @Test
    public void testComplexExpression()
    {
        final String source = "4 / 2 * 3 + 2 * 4;";
        final Expr correct = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Binary(
                                new Expr.Literal(4),
                                createOperatorToken(TokenType.SLASH),
                                new Expr.Literal(2)),
                        createOperatorToken(TokenType.STAR),
                        new Expr.Literal(3)),
                createOperatorToken(TokenType.PLUS),
                new Expr.Binary(
                        new Expr.Literal(2),
                        createOperatorToken(TokenType.STAR),
                        new Expr.Literal(4)));

        assertExpr(source, correct);
    }

    @Test
    public void testVariableInExpression()
    {
        final String source = "3 * pi * r;";
        final Expr correct = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.STAR),
                        new Expr.Variable(createIdentifierToken("pi"))),
                createOperatorToken(TokenType.STAR),
                new Expr.Variable(createIdentifierToken("r")));

        assertExpr(source, correct);
    }

    @Test
    public void testVariableInVariable()
    {
        final String source = "a.b;";
        final Expr correct = new Expr.Variable(
                createIdentifierToken("b"),
                new Expr.Variable(createIdentifierToken("a")));

        assertExpr(source, correct);
    }

    @Test
    public void testVariableInVariableInExpression()
    {
        final String source = "a.b + 20;";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("b"),
                        new Expr.Variable(createIdentifierToken("a"))),
                createOperatorToken(TokenType.PLUS),
                new Expr.Literal(20));

        assertExpr(source, correct);
    }

    @Test
    public void testCustomGroupingInExpression()
    {
        final String source = "3 * (2 + 2);";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(3),
                createOperatorToken(TokenType.STAR),
                new Expr.Grouping(
                        new Expr.Binary(
                                new Expr.Literal(2),
                                createOperatorToken(TokenType.PLUS),
                                new Expr.Literal(2))));

        assertExpr(source, correct);
    }

    @Test
    public void testSimpleAssignmentInExpression()
    {
        final String source = "a = 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.EQUAL_TO),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testAdditiveAssignmentInExpression()
    {
        final String source = "a += 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.PLUS_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testSubtractiveAssignmentInExpression()
    {
        final String source = "a -= 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.MINUS_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testMultiplicativeAssignmentInExpression()
    {
        final String source = "a *= 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.STAR_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testDivisiveAssignmentInExpression()
    {
        final String source = "a /= 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.SLASH_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testOrAssignmentInExpression()
    {
        final String source = "a ||= 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.LOGICAL_OR_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testAndAssignmentInExpression()
    {
        final String source = "a &&= 3 + 5;";
        final Expr correct = new Expr.Assign(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.LOGICAL_AND_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(5)));

        assertExpr(source, correct);
    }

    @Test
    public void testEqualsComparison()
    {
        final String source = "a == 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testNotEqualsComparison()
    {
        final String source = "a != 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.NOT_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testLesserThanComparison()
    {
        final String source = "a < 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.LESSER),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testGreaterThanComparison()
    {
        final String source = "a > 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.GREATER),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testLesserThanOrEqualsComparison()
    {
        final String source = "a <= 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.LESSER_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testGreaterOrEqualsThanComparison()
    {
        final String source = "a >= 3 + b";
        final Expr correct = new Expr.Binary(
                new Expr.Variable(createIdentifierToken("a")),
                createOperatorToken(TokenType.GREATER_EQUALS),
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Variable(createIdentifierToken("b"))));

        assertExpr(source, correct);
    }

    @Test
    public void testPreIncrementOperatorInExpression()
    {
        final String source = "++a * 30;";
        final Expr correct = new Expr.Binary(
                new Expr.PreUnary(createOperatorToken(TokenType.INCREMENT),
                        new Expr.Variable(createIdentifierToken("a"))),
                createOperatorToken(TokenType.STAR),
                new Expr.Literal(30));

        assertExpr(source, correct);
    }

    @Test
    public void testPreDecrementOperatorInExpression()
    {
        final String source = "--a * 30;";
        final Expr correct = new Expr.Binary(
                new Expr.PreUnary(createOperatorToken(TokenType.DECREMENT),
                        new Expr.Variable(createIdentifierToken("a"))),
                createOperatorToken(TokenType.STAR),
                new Expr.Literal(30));

        assertExpr(source, correct);
    }

    @Test
    public void testPostIncrementOperatorInExpression()
    {
        final String source = "a++ * 30;";
        final Expr correct = new Expr.Binary(
                new Expr.PostUnary(new Expr.Variable(createIdentifierToken("a")),
                        createOperatorToken(TokenType.INCREMENT)),
                createOperatorToken(TokenType.STAR),
                new Expr.Literal(30));

        assertExpr(source, correct);
    }

    @Test
    public void testPostDecrementOperatorInExpression()
    {
        final String source = "a-- * 30;";
        final Expr correct = new Expr.Binary(
                new Expr.PostUnary(new Expr.Variable(createIdentifierToken("a")),
                        createOperatorToken(TokenType.DECREMENT)),
                createOperatorToken(TokenType.STAR),
                new Expr.Literal(30));

        assertExpr(source, correct);
    }

    @Test
    public void testNotOperatorInExpression()
    {
        final String source = "false == !true;";
        final Expr correct = new Expr.Binary(
                new Expr.Literal(false),
                createOperatorToken(TokenType.EQUALS),
                new Expr.PreUnary(createOperatorToken(TokenType.NOT),
                        new Expr.Literal(true)));

        assertExpr(source, correct);
    }

    @Test
    public void testUnaryMinusOperatorInExpression()
    {
        final String source = "3 + 2 + (-3);";
        final Expr correct = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Literal(3),
                        createOperatorToken(TokenType.PLUS),
                        new Expr.Literal(2)),
                createOperatorToken(TokenType.PLUS),
                new Expr.Grouping(
                        new Expr.PreUnary(createOperatorToken(TokenType.MINUS),
                                new Expr.Literal(3))));

        assertExpr(source, correct);
    }
}
