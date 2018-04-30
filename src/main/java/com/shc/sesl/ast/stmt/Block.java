package com.shc.sesl.ast.stmt;

import java.util.List;

public class Block extends Stmt
{
    public final List<Stmt> statements;

    public Block(List<Stmt> statements)
    {
        this.statements = statements;
    }

    @Override
    public String toString()
    {
        return String.format("Block(%s)", statements);
    }
}
