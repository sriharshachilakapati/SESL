package com.shc.sesl.ast.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sri Harsha Chilakapati
 */
public class Call extends Expr
{
    public final Variable callee;
    public final List<Expr> arguments;

    public Call(Variable callee, List<Expr> arguments) {
        this.callee = callee;
        this.arguments = arguments != null ? arguments : new ArrayList<>();
    }

    public Call(Variable callee) {
        this(callee, null);
    }

    @Override
    public String toString() {
        final String argumentsList = arguments.stream()
                .map(Expr::toString)
                .collect(Collectors.joining(", "));

        return String.format("%s<", callee.toString()) + argumentsList + ">";
    }
}
