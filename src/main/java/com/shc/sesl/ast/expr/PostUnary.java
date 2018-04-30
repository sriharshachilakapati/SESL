package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * @author Sri Harsha Chilakapati
 */
public class PostUnary extends Expr
{
    public final Expr  left;
    public final Token operator;

    public PostUnary(Expr left, Token operator)
    {
        this.left = left;
        this.operator = operator;
    }

    @Override
    public String toString()
    {
        return String.format("PostUnary(%s %s)", left, operator.lexeme);
    }
}
