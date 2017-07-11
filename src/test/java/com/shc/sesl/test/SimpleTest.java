package com.shc.sesl.test;

import com.shc.sesl.Expr;
import com.shc.sesl.Parser;
import com.shc.sesl.Scanner;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Sri Harsha Chilakapati
 */
public class SimpleTest
{
    public static void main(String[] args) throws Exception
    {
        String source = new String(Files.readAllBytes(Paths.get(SimpleTest.class.getClassLoader().getResource("SimpleShader.sesl").toURI())),
                Charset.defaultCharset());
        new Scanner(source).scanTokens().forEach(System.out::println);

        expression("((3 + 5) / 7 == answer) && (3 + 4 / 2 == 5)");
        expression("a = 4");
        expression("a++");
        expression("++a");
        expression("a += 64");
        expression("3 + ++a");
        expression("3 + a++");
        expression("3+(++a)");
        expression("a++ - 4");
        expression("++a + 5");
        expression("a.b++");
        expression("++a.b");
    }

    private static void expression(String expression)
    {
        Expr expr = new Parser(new Scanner(expression).scanTokens()).parseExpression();
        System.out.println(expr);
    }
}
