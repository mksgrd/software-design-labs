package visitor;

import token.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private final List<Token> tokens;
    private final Deque<Integer> deque = new ArrayDeque<>();

    public CalcVisitor(List<Token> tokens) {
        this.tokens = tokens;
    }

    public int calc() {
        for (Token token : tokens) {
            token.accept(this);
        }

        if (deque.size() != 1) {
            throw new RuntimeException("Stack should have exactly one element after evaluation");
        }

        return deque.getLast();
    }

    @Override
    public void visit(NumberToken token) {
        deque.addLast(token.number());
    }

    @Override
    public void visit(BraceToken token) {
        throw new RuntimeException("Unsupported operation");
    }

    @Override
    public void visit(OperationToken token) {
        if (deque.size() < 2) {
            throw new RuntimeException("Not enough operands on stack");
        }

        int b = deque.removeLast();
        int a = deque.removeLast();

        if (token instanceof AddToken) {
            deque.add(a + b);
        } else if (token instanceof SubToken) {
            deque.add(a - b);
        } else if (token instanceof MulToken) {
            deque.add(a * b);
        } else {
            deque.add(a / b);
        }
    }
}
