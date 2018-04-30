package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * Assignment expression node. This assigns a value which is resultant of evaluating
 * an expression to a name.
 *
 * @author Sri Harsha Chilakapati
 */
public class Assign extends Expr
{
    public final Variable variable;
    public final Token operator;
    public final Expr     value;

    public Assign(Variable variable, Token operator, Expr value)
    {
        this.variable = variable;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("Assign(%s %s %s)", operator.lexeme, variable, value);
    }
}
