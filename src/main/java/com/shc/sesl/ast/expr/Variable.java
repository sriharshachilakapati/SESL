package com.shc.sesl.ast.expr;

import com.shc.sesl.Token;

/**
 * A node just to encapsulate the variables. Contains a single child which is a token
 * for the variable name.
 *
 * @author Sri Harsha Chilakapati
 */
public class Variable extends Expr
{
    public final Token name;
    public final com.shc.sesl.ast.expr.Variable owner;

    public Variable(Token name)
    {
        this(name, null);
    }

    public Variable(Token name, com.shc.sesl.ast.expr.Variable owner)
    {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String toString()
    {
        return owner == null ? String.format("Variable(%s)", name.lexeme)
                             : String.format("Variable(%s.%s)", owner, name.lexeme);
    }
}
