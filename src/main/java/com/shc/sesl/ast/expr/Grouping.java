package com.shc.sesl.ast.expr;

/**
 * @author Sri Harsha Chilakapati
 */
public class Grouping extends Expr
{
    public final Expr expression;

    public Grouping(Expr expression)
    {
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return String.format("Group(%s)", expression);
    }
}
