package token;

public final class LeftBraceToken extends BraceToken {
    @Override
    public String value() {
        return "(";
    }
}
