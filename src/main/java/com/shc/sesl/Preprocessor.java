package com.shc.sesl;

import com.shc.sesl.ast.expr.Expr;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * A minimalistic preprocessor for SESL, with similar syntax of the GLSL preprocessor. However, the functions that are
 * available are very much less, as the most needed functionality will be nothing but conditional compilation.
 */
public class Preprocessor
{
    /**
     * Process a string of text with a default context. The context will change depending on the preprocessor directives
     * in the file.
     *
     * @param source  The input file as a string.
     * @param context A mapping of preprocessor macros with their values.
     *
     * @return The processed string which can be fed into the Scanner.
     */
    public static String process(String source, Map<String, String> context)
    {
        // Split the lines first, and take care of the differences in line endings.
        String[] lines = source.split("(\\r)?\\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];

            // All preprocessor directives should be on their own line
            if (line.contains("#") && line.trim().indexOf('#') != 0)
                error(i + 1, line.indexOf('#'), "Expect preprocessor directives on their own lines.");

            // No more than one preprocessor directive is allowed on a single line
            if (line.indexOf('#') != line.lastIndexOf('#'))
                error(i + 1, line.indexOf('#'), "Only one preprocessor directive allowed on a line");

            // If there is no preprocessor directive, transform it with the context
            if (!line.contains("#"))
                sb.append(transform(line, context));

            // Define new macro, parse it and add it into the context
            else if (line.contains("#define"))
            {
                String[] parts = line.trim().split("[\\s\\t]+", 3);

                if (parts.length == 1)
                    error(i + 1, line.indexOf("#"), "Expected an identifier for the name of the macro.");

                context.put(parts[1], parts.length == 3 ? parts[2] : "");
            }

            // Undefine an existing macro, and remove it from the context
            else if (line.contains("#undef"))
            {
                String[] parts = line.trim().split("[\\s\\t]+", 2);

                if (parts.length == 1)
                    error(i + 1, line.indexOf("#"), "Expected an identifier for the name of the macro.");

                context.remove(parts[1]);
            }

            // Conditional blocks, #ifdef and #ifndef. They just check for
            else if (line.contains("#ifdef") || line.contains("#ifndef"))
            {
                String[] parts = line.trim().split("[\\s\\t]+", 2);

                if (parts.length == 1)
                    error(i + 1, line.indexOf("#") + parts[0].length(), "Expected an identifier for the name of macro.");

                boolean result = context.get(parts[1]) != null;
                boolean negate = line.contains("#ifndef");

                if ((result && !negate) || (negate && !result))
                    processSuccessBlock(lines, i);
                else
                    processFailureBlock(lines, i);
            }

            // Conditional block #if, the one which supports arithmetic expressions
            else if (line.contains("#if"))
            {
                String[] line2 = { line.trim().replaceAll("\\s*#if", "") };

                if (line2[0].trim().isEmpty())
                    error(i + 1, line.indexOf("#") + 3, "Expected an expression.");

                // Transform the expression, substituting values of macros
                context.forEach((key, value) -> line2[0] = line2[0]
                        .replaceAll(String.format("defined\\s*\\(\\s*%s\\s*\\)", Pattern.quote(key)), "true")
                        .replaceAll(Pattern.quote(key), value));

                // Any other remaining defined operators are set to false
                line2[0] = line2[0].replaceAll("defined\\s*\\(.+\\)", "false");

                // Now parse the exception
                Scanner scanner = new Scanner(line2[0]);
                Parser parser = new Parser(scanner.scanTokens());
                Expr expr = parser.parseExpression();

                // If there are any remaining tokens in the parser, it is an invalid expression
                if (!parser.isAtEnd())
                    error(i, parser.peek().column, "Expected a valid expression");

                // Evaluate the expression and process the blocks
                if (evaluate(expr))
                    processSuccessBlock(lines, i);
                else
                    processFailureBlock(lines, i);
            }

            // Unexpected preprocessor directives
            else if (line.contains("#endif") || line.contains("#elif") || line.contains("#elsif"))
                error(i + 1, line.indexOf("#"), "Unexpected directive.");

            // Unknown directive at that location.
            else
                error(i + 1, line.indexOf('#'), "Unknown preprocessor directive.");

            if (i < lines.length - 1)
                sb.append('\n');
        }

        return sb.toString();
    }

    /*
     * Evaluates an expression and returns whether it is true or false
     */
    private static boolean evaluate(Expr expr)
    {
        Object value = PPExprEvaluator.evaluate(expr);
        return value != null && value.equals(true);
    }

    /*
     * Transforms a line with the context.
     */
    private static String transform(String line, Map<String, String> context)
    {
        String[] transform = { line };

        context.forEach((key, value) ->
                transform[0] = transform[0].replaceAll(Pattern.quote(key), value));

        return transform[0];
    }

    /*
     * Process a conditional block whose condition evaluated to true.
     */
    private static void processSuccessBlock(String[] lines, int i)
    {
        // Remove the preprocessor directive
        lines[i] = "";

        for (int j = i; j < lines.length; j++)
        {
            // If it is beginning of another conditional block, skip it.
            // It will be processed in a next pass.
            if (lines[j].contains("#if") || lines[j].contains("#ifdef") || lines[j].contains("#ifndef"))
            {
                int required = 1;

                for (int k = j; k < lines.length; k++)
                {
                    if (lines[k].contains("#if") || lines[j].contains("#ifdef") || lines[j].contains("#ifndef"))
                        required++;

                    if (lines[k].contains("#endif"))
                        required--;

                    if (required == 0)
                    {
                        // Skip the lines we just went over
                        j = k;
                        break;
                    }
                }
            }

            // If we reached the else or elsif clauses, remove them until the end of the block
            if (lines[j].contains("#elif") || lines[j].contains("#else"))
            {
                for (int k = j; k < lines.length; k++)
                {
                    if (lines[k].contains("#endif"))
                    {
                        lines[k] = "";
                        return;
                    }

                    lines[k] = "";
                }
            }

            // If we reached the end of the block, return
            if (lines[j].contains("#endif"))
            {
                lines[j] = "";
                return;
            }
        }

        // If we ever reach here, that means it is an error
        error(lines.length, 0, "Expected a #endif directive");
    }

    /*
     * Processes a conditional block whose condition evaluated to false.
     */
    private static void processFailureBlock(String[] lines, int i)
    {
        // Remove the preprocessor directive
        lines[i] = "";

        for (int j = i; j < lines.length; j++)
        {
            // If it is a nested conditional block, remove it entirely
            if (lines[j].contains("#if") || lines[j].contains("#ifdef") || lines[j].contains("#ifndef"))
            {
                int required = 1;
                int end = -1;

                // Find the end of the block first. This also takes care of nested blocks in the nested block.
                for (int k = j; k < lines.length; k++)
                {
                    if (lines[k].contains("#if") || lines[j].contains("#ifdef") || lines[j].contains("#ifndef"))
                        required++;

                    if (lines[k].contains("#endif"))
                        required--;

                    if (required == 0)
                    {
                        end = k;
                        break;
                    }
                }

                // Clear all the lines until the end, removing the block entirely.
                for (int k = j; k <= end; k++)
                    lines[k] = "";

                // Skip the already cleared lines, and move along
                if (end != -1)
                {
                    j = end;
                    continue;
                }
            }

            // If we reached an elif or else clause, transform it so that it becomes a new block
            if (lines[j].contains("#elif") || lines[j].contains("#else"))
            {
                lines[j] = lines[j].replaceAll("#elif (.+)", "#if $1")
                        .replaceAll("#else", "#if true");

                return;
            }

            // If we reached an endif token, then remove it as well and return
            if (lines[j].contains("#endif"))
            {
                lines[j] = "";
                return;
            }

            // Eat the line, clear it
            lines[j] = "";
        }

        // If we ever reach here, that means it is an error
        error(lines.length, 0, "Expected a #endif directive");
    }

    /*
     * Simple method to print an error and throw an exception
     */
    private static void error(int line, int column, String message)
    {
        SESL.error(line, column, message);
        throw new PreprocessorException();
    }

    /**
     * Preprocessor Exception. The cause is printed always.
     */
    public final static class PreprocessorException extends RuntimeException
    {
    }
}
