package com.shc.sesl.ast.stmt;

import com.shc.sesl.ast.expr.Expr;

public class Expression extends Stmt
{
    public final Expr expression;

    public Expression(Expr expression)
    {
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        return String.format("Expr(%s)", expression);
    }
}
