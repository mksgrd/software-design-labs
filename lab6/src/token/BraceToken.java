package token;

import visitor.TokenVisitor;

public abstract sealed class BraceToken implements Token permits LeftBraceToken, RightBraceToken {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
