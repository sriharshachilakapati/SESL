package com.shc.sesl;

/**
 * @author Sri Harsha Chilakapati
 */
public abstract class Expr
{
    public static class Binary extends Expr
    {
        public final Expr left;
        public final Token operator;
        public final Expr right;

        public Binary(Expr left, Token operator, Expr right)
        {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public String toString()
        {
            return String.format("Binary(%s %s %s)", operator.lexeme, left, right);
        }
    }

    public static class Logical extends Expr
    {
        public final Expr left;
        public final Token operator;
        public final Expr right;

        public Logical(Expr left, Token operator, Expr right)
        {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public String toString()
        {
            return String.format("Logical(%s %s %s)", operator.lexeme, left, right);
        }
    }

    public static class Variable extends Expr
    {
        public final Token name;

        public Variable(Token name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return String.format("Variable(%s)", name.lexeme);
        }
    }

    public static class Assign extends Expr
    {
        public final Token token;
        public final Token operator;
        public final Expr value;

        public Assign(Token token, Token operator, Expr value)
        {
            this.token = token;
            this.operator = operator;
            this.value = value;
        }

        @Override
        public String toString()
        {
            return String.format("Assign(%s %s %s)", operator.lexeme, token.lexeme, value);
        }
    }

    public static class Grouping extends Expr
    {
        public final Expr expression;

        public Grouping(Expr expression)
        {
            this.expression = expression;
        }

        @Override
        public String toString()
        {
            return String.format("Group(%s)", expression);
        }
    }

    public static class Literal extends Expr
    {
        public final Object value;

        public Literal(Object value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return String.format("Literal(%s)", value);
        }
    }

    public static class PreUnary extends Expr
    {
        public final Token operator;
        public final Expr right;

        public PreUnary(Token operator, Expr right)
        {
            this.operator = operator;
            this.right = right;
        }

        @Override
        public String toString()
        {
            return String.format("PreUnary(%s %s)", operator.lexeme, right);
        }
    }

    public static class PostUnary extends Expr
    {
        public final Expr left;
        public final Token operator;

        public PostUnary(Expr left, Token operator)
        {
            this.left = left;
            this.operator = operator;
        }

        @Override
        public String toString()
        {
            return String.format("PostUnary(%s %s)", left, operator.lexeme);
        }
    }
}
