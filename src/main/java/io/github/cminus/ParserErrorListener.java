package io.github.cminus;

import org.antlr.v4.runtime.*;

public class ParserErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        if (e instanceof InputMismatchException) {
            System.err.println("Error type B at Line " + line + ": " + msg);
        }
        throw e;
    }
}
