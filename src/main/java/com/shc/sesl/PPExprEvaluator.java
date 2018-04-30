package com.shc.sesl;

import com.shc.sesl.ast.expr.*;

class PPExprEvaluator
{
    static Object evaluate(Expr expr)
    {
        while (expr instanceof Grouping)
            expr = ((Grouping) expr).expression;

        if (expr instanceof Literal)
            return evaluate((Literal) expr);

        if (expr instanceof PreUnary)
            return evaluate((PreUnary) expr);

        if (expr instanceof Binary)
            return evaluate((Binary) expr);

        return null;
    }

    private static Object evaluate(Literal expr)
    {
        return expr.value;
    }

    private static Object evaluate(PreUnary expr)
    {
        Object right = evaluate(expr.right);

        if (right == null)
            return null;

        switch (expr.operator.type)
        {
            case MINUS:
                return -((Number) right).doubleValue();

            case NOT:
                return !isTruthy(right);
        }

        return null;
    }

    private static Object evaluate(Binary expr)
    {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        if (left == null || right == null)
            return null;

        switch (expr.operator.type)
        {
            case MINUS:
                return ((Number) left).doubleValue() - ((Number) right).doubleValue();

            case PLUS:
                return ((Number) left).doubleValue() + ((Number) right).doubleValue();

            case SLASH:
                return ((Number) left).doubleValue() / ((Number) right).doubleValue();

            case STAR:
                return ((Number) left).doubleValue() * ((Number) right).doubleValue();

            case GREATER:
                return ((Number) left).doubleValue() > ((Number) right).doubleValue();

            case LESSER:
                return ((Number) left).doubleValue() < ((Number) right).doubleValue();

            case GREATER_EQUALS:
                return ((Number) left).doubleValue() >= ((Number) right).doubleValue();

            case LESSER_EQUALS:
                return ((Number) left).doubleValue() <= ((Number) right).doubleValue();

            case NOT_EQUALS:
                return !isEquals(left, right);

            case EQUALS:
                return isEquals(left, right);
        }

        return null;
    }

    private static boolean isTruthy(Object object)
    {
        return object != null && (!(object instanceof Boolean) || (boolean) object);
    }

    private static boolean isEquals(Object a, Object b)
    {
        if (a instanceof Number && b instanceof Number)
            return ((Number) a).doubleValue() == ((Number) b).doubleValue();

        return a == null && b == null || a != null && a.equals(b);
    }
}
