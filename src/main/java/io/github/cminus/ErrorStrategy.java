package io.github.cminus;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

public class ErrorStrategy extends DefaultErrorStrategy {
    public ErrorStrategy() {}

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        super.recover(recognizer, e);
        IntervalSet recoverySet = getErrorRecoverySet(recognizer);
        recognizer.consume();
        consumeUntil(recognizer, recoverySet);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        Token matchedSymbol = this.singleTokenDeletion(recognizer);
        if (matchedSymbol != null) {
            recognizer.consume();
            return matchedSymbol;
        } else if (this.singleTokenInsertion(recognizer)) {
            return this.getMissingSymbol(recognizer);
        } else {
            throw new InputMismatchException(recognizer);
        }
    }
}