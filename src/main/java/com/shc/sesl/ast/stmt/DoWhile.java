package com.shc.sesl.ast.stmt;

import com.shc.sesl.ast.expr.Expr;

public class DoWhile extends Stmt
{
    public final Stmt body;
    public final Expr condition;

    public DoWhile(Stmt body, Expr condition)
    {
        this.body = body;
        this.condition = condition;
    }

    @Override
    public String toString()
    {
        return String.format("DoWhile(%s %s)", body, condition);
    }
}
