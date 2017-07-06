package com.shc.sesl;

import static com.shc.sesl.TokenType.SEMICOLON;

/**
 * @author Sri Harsha Chilakapati
 */
class StmtParser
{
    private final Parser parser;

    StmtParser(Parser parser)
    {
        this.parser = parser;
    }

    Stmt parse()
    {
        // statement = expression_stmt ;
        return parseExpressionStatement();
    }

    private Stmt parseExpressionStatement()
    {
        // expression_stmt = expression ";" ;
        Expr expr = parser.parseExpression();
        parser.consume(SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }


}
