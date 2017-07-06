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
            return String.format("( %s %s %s )", operator.lexeme, left, right);
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
            return String.format("( %s )", expression);
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
            return String.format("%s", value);
        }
    }

    public static class Unary extends Expr
    {
        public final Token operator;
        public final Expr right;

        public Unary(Token operator, Expr right)
        {
            this.operator = operator;
            this.right = right;
        }

        @Override
        public String toString()
        {
            return String.format("( %s %s )", operator.lexeme, right);
        }
    }
}
