package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * Logical expression node. This is used to denote logical operands which result in
 * the expression evaluating to boolean.
 *
 * @author Sri Harsha Chilakapati
 */
public class Logical extends Expr
{
    public final Expr  left;
    public final Token operator;
    public final Expr  right;

    public Logical(Expr left, Token operator, Expr right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return String.format("Logical(%s %s %s)", operator.lexeme, left, right);
    }
}
