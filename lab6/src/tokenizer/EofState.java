package tokenizer;

public class EofState extends TokenizerState {
    public EofState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextChar(int ch) {
        tokenizer.setState(this);
    }
}
