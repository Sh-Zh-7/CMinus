package io.github.cminus;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.GrammarParserInterpreter;


import static io.github.cminus.CompilerHelper.getSyntaxTree;

public class CMinusCompiler {
    public static void main(String[] args) {
        try {
            // Lexer configuration
            CharStream input = CharStreams.fromFileName("src/test/resources/examples/example2.cm");
            CMinusLexer lexer = new CMinusLexer(input);
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
            System.out.println(getSyntaxTree(parser, tree));
        } catch (Exception e){
            System.exit(-1);
        }
    }
}
