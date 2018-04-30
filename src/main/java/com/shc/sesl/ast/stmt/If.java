package com.shc.sesl.ast.stmt;

import com.shc.sesl.ast.expr.Expr;

public class If extends Stmt
{
    public final Expr condition;
    public final Stmt thenBranch;
    public final Stmt elseBranch;

    public If(Expr condition, Stmt thenBranch, Stmt elseBranch)
    {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString()
    {
        return String.format("If(%s %s %s)", condition, thenBranch, elseBranch);
    }
}
