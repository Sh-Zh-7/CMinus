package io.github.cminus;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

import static io.github.cminus.CompilerHelper.getSyntaxTree;

public class CMinusCompiler {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("One input file's path MUST BE specified!");
        } else {
            runFromFile(args[0]);
        }
    }

    public static void runFromFile(String path) throws IOException {
        // Lexer configuration
        CharStream input = CharStreams.fromFileName(path);
        CMinusCustomLexer lexer = new CMinusCustomLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new LexerErrorListener());
        // Parser configuration
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CMinusParser parser = new CMinusParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener());
        parser.setErrorHandler(new ErrorStrategy());
        // Parsing
        ParseTree tree = parser.program();

        // Error handling
        if (lexer.getNumberOfTokenErrors() == 0 && parser.getNumberOfSyntaxErrors() == 0) {
            System.out.println(getSyntaxTree(parser, tree));
        }
    }
}
