package com.shc.sesl.ast.expr;

/**
 * @author Sri Harsha Chilakapati
 */
public class Literal extends Expr
{
    public final Object value;

    public Literal(Object value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("Literal(%s)", value);
    }
}
