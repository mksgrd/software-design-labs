package token;


import visitor.TokenVisitor;

public abstract sealed class OperationToken implements Token permits AddToken, DivToken, MulToken, SubToken {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
