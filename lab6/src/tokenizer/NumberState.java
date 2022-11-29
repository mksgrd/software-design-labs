package tokenizer;

import token.NumberToken;

public class NumberState extends TokenizerState {
    private final String number;

    public NumberState(Tokenizer tokenizer, String number) {
        super(tokenizer);
        this.number = number;
    }

    @Override
    public void nextChar(int ch) {
        if (Character.isDigit(ch)) {
            tokenizer.setState(new NumberState(tokenizer, number + Character.toString(ch)));
        } else {
            tokenizer.addToken(new NumberToken(Integer.parseInt(number)));

            TokenizerState state = new StartState(tokenizer);
            tokenizer.setState(state);

            state.nextChar(ch);
        }
    }
}
