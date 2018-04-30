package com.shc.sesl.ast.stmt;

import com.shc.sesl.ast.expr.Expr;

public class For extends Stmt
{
    public final Expr initializer;
    public final Expr condition;
    public final Expr loop;
    public final Stmt body;

    public For(Expr initializer, Expr condition, Expr loop, Stmt body)
    {
        this.initializer = initializer;
        this.condition = condition;
        this.loop = loop;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return String.format("For(%s %s %s %s)", initializer, condition, loop, body);
    }
}
