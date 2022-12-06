package token;

import visitor.TokenVisitor;

public record NumberToken(int number) implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String value() {
        return String.valueOf(number);
    }
}