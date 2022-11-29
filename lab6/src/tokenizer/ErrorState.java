package tokenizer;

public class ErrorState extends TokenizerState {
    public ErrorState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextChar(int ch) {
        tokenizer.setState(this);
    }
}
