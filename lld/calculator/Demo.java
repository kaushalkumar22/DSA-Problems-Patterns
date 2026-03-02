package lld.calculator;

import java.util.*;
// ----------------- Operation Strategy ---------------------
interface OperationStrategy {
    double apply(double a, double b);
}
class AddStrategy implements OperationStrategy {
    public double apply(double a, double b) { return a + b; }
}
class SubtractStrategy implements OperationStrategy {
    public double apply(double a, double b) { return a - b; }
}
class MultiplyStrategy implements OperationStrategy {
    public double apply(double a, double b) { return a * b; }
}
class DivideStrategy implements OperationStrategy {
    public double apply(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divide by zero");
        return a / b;
    }
}
// ----------------- Operation Factory -----------------------
class OperationFactory {
    private static final Map<String, OperationStrategy> ops = new HashMap<>();
    static {
        ops.put("+", new AddStrategy());
        ops.put("-", new SubtractStrategy());
        ops.put("*", new MultiplyStrategy());
        ops.put("/", new DivideStrategy());
    }
    public static OperationStrategy get(String symbol) {
        OperationStrategy op = ops.get(symbol);
        if (op == null) throw new IllegalArgumentException("Unknown operator: " + symbol);
        return op;
    }
}
// ----------------- Expression Parser (Shunting Yard) -------
class ExpressionParser {
    private static final Map<String, Integer> precedence = Map.of(
            "+", 1, "-", 1,
            "*", 2, "/", 2
    );
    public List<String> infixToPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        StringTokenizer st = new StringTokenizer(expr, "+-*/() ", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if (token.isEmpty()) continue;
            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() &&
                        isOperator(operators.peek()) &&
                        precedence.get(operators.peek()) >= precedence.get(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("("))
                    output.add(operators.pop());
                operators.pop();
            }
        }
        while (!operators.isEmpty())
            output.add(operators.pop());
        return output;
    }
    private boolean isNumber(String s) { return s.matches("-?\\d+(\\.\\d+)?"); }
    private boolean isOperator(String s) { return "+-*/".contains(s); }
}

// ----------------- Postfix Evaluator ----------------------
class PostfixEvaluator {
    public double evaluate(List<String> tokens) {
        Stack<Double> stack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.valueOf(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                OperationStrategy op = OperationFactory.get(token);
                stack.push(op.apply(a, b));
            }
        }
        return stack.pop();
    }
}

// ----------------- Calculator (Facade) --------------------
class Calculator {
    private final ExpressionParser parser = new ExpressionParser();
    private final PostfixEvaluator evaluator = new PostfixEvaluator();
    public double evaluate(String expression) {
        List<String> postfix = parser.infixToPostfix(expression);
        return evaluator.evaluate(postfix);
    }
}

// ----------------- DEMO --------------------
public class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.evaluate("2 + 3 * 4"));            // 14
        System.out.println(calc.evaluate("(5 + 5) * 2"));          // 20
        System.out.println(calc.evaluate("10 / 2 + 6 * 3"));       // 28
    }
}

