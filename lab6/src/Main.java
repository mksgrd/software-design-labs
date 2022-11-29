import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression: ");
        //String expr = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2";
        String expr = scanner.nextLine();
        InputStream inputStream = new ByteArrayInputStream(expr.getBytes(StandardCharsets.UTF_8));

        Tokenizer tokenizer = new Tokenizer(inputStream);

        ParserVisitor parserVisitor = new ParserVisitor(tokenizer.tokenize());
        List<Token> rpnTokens = parserVisitor.parse();

        PrintVisitor printVisitor = new PrintVisitor(rpnTokens);
        System.out.print("RPN: ");
        printVisitor.print();

        CalcVisitor calcVisitor = new CalcVisitor(rpnTokens);
        System.out.print("Result: ");
        System.out.println(calcVisitor.calc());
    }
}