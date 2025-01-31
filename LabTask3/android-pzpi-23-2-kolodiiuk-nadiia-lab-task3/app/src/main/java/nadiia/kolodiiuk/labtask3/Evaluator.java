package nadiia.kolodiiuk.labtask3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Evaluator implements Serializable {
    private StringBuilder currentNumber = new StringBuilder();
    private ArrayList<String> expression = new ArrayList<>();
    private boolean hasDecimalPoint = false;
    private boolean isNewCalculation = true;
    private static final int MAX_DIGITS = 16;

    public String calculate() {
        try {
            if (currentNumber.length() > 0) {
                expression.add(currentNumber.toString());
            }
            if (expression.isEmpty()) {
                return "0";
            }
            ArrayList<String> postfix = convertToPostfix();
            double result = evaluatePostfix(postfix);
            clear();
            currentNumber.append(formatNumber(result));
            return getCurrentDisplay();
        } catch (Exception e) {
            clear();
            return "Error";
        }
    }
    
    public void appendDigit(String digit) {
        if (isNewCalculation) {
            clear();
            isNewCalculation = false;
        }
        if (!hasDecimalPoint && currentNumber.length() >= MAX_DIGITS) {
            return;
        }
        if (currentNumber.length() == 1 && currentNumber.charAt(0) == '0' && !hasDecimalPoint) {
            currentNumber.setLength(0);
        }
        currentNumber.append(digit);
    }

    public void appendDecimalPoint() {
        if (isNewCalculation) {
            clear();
            isNewCalculation = false;
        }
        if (!hasDecimalPoint) {
            if (currentNumber.length() == 0) {
                currentNumber.append("0");
            }
            currentNumber.append(".");
            hasDecimalPoint = true;
        }
    }

    public String applyOperator(String operator) {
        if (currentNumber.length() > 0) {
            expression.add(currentNumber.toString());
            expression.add(operator);
            currentNumber.setLength(0);
            hasDecimalPoint = false;
            isNewCalculation = false;
        } else if (!expression.isEmpty()) {
            expression.set(expression.size() - 1, operator);
        }
        return getCurrentDisplay();
    }
    
    private ArrayList<String> convertToPostfix() {
        ArrayList<String> postfix = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : expression) {
            if (isNumber(token)) {
                postfix.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() 
                        && getPrecedence(operators.peek()) >= getPrecedence(token)) {
                    postfix.add(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.add(operators.pop());
        }

        return postfix;
    }

    private int getPrecedence(String operator) {
        switch (operator) {
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double evaluatePostfix(ArrayList<String> postfix) {
        Stack<Double> values = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else {
                if (values.size() < 2) {
                    throw new IllegalStateException("Invalid expression");
                }
                double b = values.pop();
                double a = values.pop();
                values.push(performOperation(a, b, token));
            }
        }

        if (values.size() != 1) {
            throw new IllegalStateException("Invalid expression");
        }
        
        return values.pop();
    }

    private double performOperation(double a, double b, String operator) {
        switch (operator) {
            case "+": 
                return a + b;
            case "-": 
                return a - b;
            case "*": 
                return a * b;
            case "/": 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    public void clear() {
        currentNumber.setLength(0);
        expression.clear();
        hasDecimalPoint = false;
        isNewCalculation = true;
    }

    public String getCurrentDisplay() {
        StringBuilder display = new StringBuilder();
        for (String token : expression) {
            display.append(token);
        }
        display.append(currentNumber);
        return display.length() > 0 ? display.toString() : "0";
    }

    private String formatNumber(double number) {
        if (Double.isInfinite(number) || Double.isNaN(number)) {
            return "Error";
        }
        if (number == (long) number) {
            return String.format("%d", (long) number);
        }
        String result = String.format("%.8f", number)
                            .replaceAll("0*$", "")
                            .replaceAll("\\.$", "");
        return result.length() > 16 ? String.format("%.8e", number) : result;
    }
}