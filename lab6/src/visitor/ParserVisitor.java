package visitor;

import token.*;

import java.util.*;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> tokens;
    private final Queue<Token> resultQueue = new ArrayDeque<>();
    private final Deque<Token> operationDeque = new ArrayDeque<>();

    public ParserVisitor(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> parse() {
        for (Token token : tokens) {
            token.accept(this);
        }

        while (!operationDeque.isEmpty()) {
            if (operationDeque.getLast() instanceof OperationToken) {
                resultQueue.add(operationDeque.removeLast());
            } else {
                throw new RuntimeException("Unmatched left brace");
            }
        }

        return new ArrayList<>(resultQueue);
    }

    @Override
    public void visit(NumberToken token) {
        resultQueue.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token instanceof LeftBraceToken) {
            operationDeque.addLast(token);
        } else {
            while (!operationDeque.isEmpty() && operationDeque.getLast() instanceof OperationToken) {
                resultQueue.add(operationDeque.removeLast());
            }
            if (!(!operationDeque.isEmpty() && operationDeque.removeLast() instanceof LeftBraceToken)) {
                throw new RuntimeException("Unmatched right brace");
            }
        }
    }

    private int precedence(OperationToken operationToken) {
        if (operationToken instanceof AddToken || operationToken instanceof SubToken) {
            return 0;
        }
        return 1;
    }

    @Override
    public void visit(OperationToken token) {
        while (!operationDeque.isEmpty() && operationDeque.getLast() instanceof OperationToken operationToken) {
            if (precedence(operationToken) >= precedence(token)) {
                resultQueue.add(operationDeque.removeLast());
            } else {
                break;
            }
        }

        operationDeque.addLast(token);
    }
}
