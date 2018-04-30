package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * Binary expression tree node. This node has three children, one operator in middle
 * and two expression nodes on the either side.
 *
 * @author Sri Harsha Chilakapati
 */
public class Binary extends Expr
{
    public final Expr  left;
    public final Token operator;
    public final Expr  right;

    public Binary(Expr left, Token operator, Expr right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return String.format("Binary(%s %s %s)", operator.lexeme, left, right);
    }
}
