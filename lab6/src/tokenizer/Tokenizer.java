package tokenizer;

import token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final InputStream inputStream;
    private final List<Token> tokens;
    private TokenizerState state;

    public Tokenizer(InputStream inputStream) {
        this.inputStream = inputStream;
        tokens = new ArrayList<>();
        state = new StartState(this);
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public List<Token> tokenize() {
        while (true) {
            try {
                int ch = inputStream.read();
                state.nextChar(ch);

                if (state instanceof EofState) {
                    break;
                }

                if (state instanceof ErrorState) {
                    throw new RuntimeException("Unexpected token");
                }

            } catch (IOException e) {
                throw new RuntimeException("Read error", e);
            }
        }

        return tokens;
    }

    public void setState(TokenizerState state) {
        this.state = state;
    }
}
