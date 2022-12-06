package tokenizer;

public abstract class TokenizerState {
    protected Tokenizer tokenizer;

    public TokenizerState(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract void nextChar(int ch);
}
