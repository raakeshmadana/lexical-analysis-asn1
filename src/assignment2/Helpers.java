package assignment2;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;

public class Helpers {

    public static String useAmpersandForConcatenation(String regex) {
        for(int i = 0; i < regex.length() - 1; i++) {
            char cur = regex.charAt(i);
            if(cur != '|' && cur != '(') { // Don't add '.' after '|' and '('
                char next = regex.charAt(i + 1);
                if(next != '|' && next != '*' && next != ')') { // Don't add '.' if '|', '*', ')' are the next characters
                    regex = new StringBuilder(regex).insert(i + 1, "&").toString();
                    i++;
                }
            }
        }
        return regex;
    }

    private static int precedence(char operator) {
        switch(operator) {
            case '&':
                return 2;
            case '|':
                return 1;
            default:
                return -1;
        }
    }

    public static String infixToPostfix(String infixRegex) {
        Deque<Character> stack = new ArrayDeque<Character>();

        String postfixRegex = "";
        for(int i = 0; i < infixRegex.length(); i++) {
            char c = infixRegex.charAt(i);
            switch(c) {
                case '&':
                case '|':
                    while(!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                        postfixRegex += stack.removeFirst();
                    }
                    stack.addFirst(c);
                    break;

                case '(':
                    stack.addFirst(c);
                    break;

                case ')':
                    while(!stack.isEmpty() && stack.peek() != '(') {
                        postfixRegex += stack.removeFirst();
                    }
                    stack.removeFirst();
                    break;

                default:
                    postfixRegex += c;
            }
        }

        while(!stack.isEmpty()) {
            postfixRegex += stack.removeFirst();
        }

        return postfixRegex;
    }
}