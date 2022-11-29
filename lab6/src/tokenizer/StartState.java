package tokenizer;

import token.*;

public class StartState extends TokenizerState {
    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextChar(int ch) {
        if (ch == -1) {
            tokenizer.setState(new EofState(tokenizer));
            return;
        }

        if (Character.isWhitespace(ch)) {
            return;
        }

        if (ch == '(') {
            tokenizer.addToken(new LeftBraceToken());
        } else if (ch == ')') {
            tokenizer.addToken(new RightBraceToken());
        } else if (ch == '+') {
            tokenizer.addToken(new AddToken());
        } else if (ch == '-') {
            tokenizer.addToken(new SubToken());
        } else if (ch == '*') {
            tokenizer.addToken(new MulToken());
        } else if (ch == '/') {
            tokenizer.addToken(new DivToken());
        } else if (Character.isDigit(ch)) {
            tokenizer.setState(new NumberState(tokenizer, Character.toString(ch)));
        } else {
            tokenizer.setState(new ErrorState(tokenizer));
        }
    }
}
