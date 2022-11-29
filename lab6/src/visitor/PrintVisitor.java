package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final List<Token> tokens;

    public PrintVisitor(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void print() {
        for (Token token : tokens) {
            token.accept(this);
            System.out.print(' ');
        }
        System.out.println();
    }

    @Override
    public void visit(NumberToken token) {
        System.out.print(token.value());
    }

    @Override
    public void visit(BraceToken token) {
        System.out.print(token.value());
    }

    @Override
    public void visit(OperationToken token) {
        System.out.print(token.value());
    }
}
