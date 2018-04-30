package com.shc.sesl.ast.stmt;

import com.shc.sesl.Token;
import com.shc.sesl.ast.expr.Expr;

public class Var extends Stmt
{
    public final Token type;
    public final Token name;
    public final Expr initializer;

    public Var(Token type, Token name, Expr initializer)
    {
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toString()
    {
        return String.format("Var(%s %s %s)", type, name, initializer);
    }
}
