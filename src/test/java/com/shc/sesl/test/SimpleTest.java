package com.shc.sesl.test;

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
    }
}
