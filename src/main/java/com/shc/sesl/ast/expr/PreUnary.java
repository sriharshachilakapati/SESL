package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * @author Sri Harsha Chilakapati
 */
public class PreUnary extends Expr
{
    public final Token operator;
    public final Expr  right;

    public PreUnary(Token operator, Expr right)
    {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return String.format("PreUnary(%s %s)", operator.lexeme, right);
    }
}
