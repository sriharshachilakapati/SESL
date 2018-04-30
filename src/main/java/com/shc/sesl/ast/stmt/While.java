package com.shc.sesl.ast.stmt;

import com.shc.sesl.ast.expr.Expr;

public class While extends Stmt
{
    public final Expr condition;
    public final Stmt body;

    public While(Expr condition, Stmt body)
    {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return String.format("While(%s %s)", condition, body);
    }
}
