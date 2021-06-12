package io.github.cminus;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.GrammarParserInterpreter;


import java.io.IOException;
import java.util.Arrays;

import static io.github.cminus.CompilerHelper.getSyntaxTree;

public class CMinusCompiler {
    public static void main(String[] args) throws IOException {
        // Lexer configuration
        CharStream input = CharStreams.fromFileName("src/test/resources/advance/fp.cm");
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
        } else {
            System.exit(-1);
        }
    }
}
