package io.github.cminus;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public class CMinusCustomLexer extends CMinusLexer {
    private int _tokenErrors = 0;

    public CMinusCustomLexer(CharStream input) {
        super(input);
    }

    @Override
    public void notifyListeners(LexerNoViableAltException e) {
        ++this._tokenErrors;
        super.notifyListeners(e);
    }

    @Override
    public Token emit() {
        switch (getType()) {
            case ILLEGAL_OCT:
                return handleIllegalInput("Illegal octal numbers!");
            case ILLEGAL_HEX:
                return handleIllegalInput("Illegal hex numbers!");
            case UNTERMINATED_ML_COMMENT:
                return handleIllegalInput("Illegal block comment! Did you miss the '*' or '*/'?");
            default:
                return super.emit();
        }
    }

    public int getNumberOfTokenErrors() {
        return this._tokenErrors;
    }

    private Token handleIllegalInput(String msg) {
        ++this._tokenErrors;
        Token token = super.emit();
        RecognitionException exception = new RecognitionException(this, getInputStream(), null);
        getErrorListenerDispatch().syntaxError(
                this,
                null,
                getLine(),
                getCharPositionInLine(),
                msg,
                exception
        );

        return token;
    }
}
