package com.shc.sesl;

import java.util.List;

/**
 * @author Sri Harsha Chilakapati
 */
public abstract class Stmt
{
    public static class Expression extends Stmt
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

    public static class If extends Stmt
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

    public static class Var extends Stmt
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

    public static class While extends Stmt
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

    public static class For extends Stmt
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

    public static class DoWhile extends Stmt
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

    public static class Block extends Stmt
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
}
