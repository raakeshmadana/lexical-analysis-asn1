package assignment2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class Assignment2 {
    // Regular Expressions
    public static final String UPPER_CASE_LETTERS = "A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z";
    public static final String LOWER_CASE_LETTERS = "a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z";
    public static final String HYPHENED_UPPER_CASE_LETTERS = "-A|-B|-C|-D|-E|-F|-G|-H|-I|-J|-K|-L|-M|-N|-O|-P|-Q|-R|-S|-T|-U|-V|-W|-X|-Y|-Z";
    public static final String HYPHENED_LOWER_CASE_LETTERS = "-a|-b|-c|-d|-e|-f|-g|-h|-i|-j|-k|-l|-m|-n|-o|-p|-q|-r|-s|-t|-u|-v|-w|-x|-y|-z";
    public static final String DIGITS = "0|1|2|3|4|5|6|7|8|9";
    public static final String HYPHENED_DIGITS = "-0|-1|-2|-3|-4|-5|-6|-7|-8|-9";
    public static final String NON_ZERO_DIGITS = "1|2|3|4|5|6|7|8|9";
    public static final String ZERO = "0";
    public static final String TYPE_REFERENCE = "(" + UPPER_CASE_LETTERS + ")"
                                + "(" + UPPER_CASE_LETTERS + "|"
                                + LOWER_CASE_LETTERS + "|"
                                + DIGITS + "|"
                                + HYPHENED_UPPER_CASE_LETTERS + "|"
                                + HYPHENED_LOWER_CASE_LETTERS + "|"
                                + HYPHENED_DIGITS + ")" + "*";
    public static final String IDENTIFIER = "(" + LOWER_CASE_LETTERS + ")"
                                + "(" + UPPER_CASE_LETTERS + "|"
                                + LOWER_CASE_LETTERS + "|"
                                + DIGITS + "|"
                                + HYPHENED_UPPER_CASE_LETTERS + "|"
                                + HYPHENED_LOWER_CASE_LETTERS + "|"
                                + HYPHENED_DIGITS + ")" + "*";
    public static final String NUMBER = ZERO + "|"
                                + "(" + NON_ZERO_DIGITS + ")"
                                + "(" + DIGITS + ")" + "*";
    public static final String ASSIGNMENT = "::=";
    public static final String RANGE_SEPARATOR = "..";
    public static final String LCURLY = "{";
    public static final String RCURLY = "}";
    public static final String COMMA = ",";
    public static final String LPAREN = "(";
    public static final String RPAREN = ")";
    public static final String BAR = "|";
    public static final String QUOTE = "\"";

    public static List<List<Edge>> combinedEnfaAdjList = new ArrayList<List<Edge>>();
    public static ENFA combinedEnfa;
    public static List<Character> inputSymbols;

    public static addInputSymbols() {
        inputSymbols = new ArrayList<Character>();

        // Add upper case letters
        for(int i = 65; i <= 90; i++) {
            inputSymbols.add((char)i);
        }
        
        // Add lower case letters
        for(int i = 97; i <= 122; i++) {
            inputSymbols.add((char)i);
        }

        // Add digits
        for(int i = 48; i <= 57; i++) {
            inputSymbols.add((char)i);
        }

        inputSymbols.add((char)34); // Quotation
        inputSymbols.add((char)40); // Left Parenthesis
        inputSymbols.add((char)41); // Right Parenthesis
        inputSymbols.add((char)44); // Comma
        inputSymbols.add((char)45); // Hyphen-minus
        inputSymbols.add((char)46); // Dot
        inputSymbols.add((char)58); // Colon
        inputSymbols.add((char)61); // Equals
        inputSymbols.add((char)123); // Left Curly
        inputSymbols.add((char)125); // Right Curly
        inputSymbols.add((char)124); // Bar
    }

    public static void main(String args[]) throws IOException {

        // Add input symbols
        addInputSymbols();
        // Add the initial state
        List<Edge> edgeList = new ArrayList<Edge>();
        combinedEnfaAdjList.add(edgeList);

        combinedEnfa = new ENFA(combinedEnfaAdjList);

        // ENFA for TYPE_REFERENCE
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
        String infixRegex = Helpers.useAmpersandForConcatenation(TYPE_REFERENCE);
        writer.write("Using ampersand for concatenation\n");
        writer.write(infixRegex + "\n");
        String postfixRegex = Helpers.infixToPostfix(infixRegex);
        writer.write("\nConverting the regular expression to Postfix notation\n");
        writer.write(postfixRegex + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, postfixRegex, "TYPE_REFERENCE");

        // ENFA for IDENTIFIER
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        infixRegex = Helpers.useAmpersandForConcatenation(IDENTIFIER);
        writer.write("Using ampersand for concatenation\n");
        writer.write(infixRegex + "\n");
        postfixRegex = Helpers.infixToPostfix(infixRegex);
        writer.write("\nConverting the regular expression to Postfix notation\n");
        writer.write(postfixRegex + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, postfixRegex, "IDENTIFIER");

        // ENFA for NUMBER
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        infixRegex = Helpers.useAmpersandForConcatenation(NUMBER);
        writer.write("Using ampersand for concatenation\n");
        writer.write(infixRegex + "\n");
        postfixRegex = Helpers.infixToPostfix(infixRegex);
        writer.write("\nConverting the regular expression to Postfix notation\n");
        writer.write(postfixRegex + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, postfixRegex, "NUMBER");

        // ENFA for ASSIGNMENT
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        infixRegex = Helpers.useAmpersandForConcatenation(ASSIGNMENT);
        writer.write("Using ampersand for concatenation\n");
        writer.write(infixRegex + "\n");
        postfixRegex = Helpers.infixToPostfix(infixRegex);
        writer.write("\nConverting the regular expression to Postfix notation\n");
        writer.write(postfixRegex + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, postfixRegex, "ASSIGNMENT");

        // ENFA for RANGE_SEPARATOR
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        infixRegex = Helpers.useAmpersandForConcatenation(RANGE_SEPARATOR);
        writer.write("Using ampersand for concatenation\n");
        writer.write(infixRegex + "\n");
        postfixRegex = Helpers.infixToPostfix(infixRegex);
        writer.write("\nConverting the regular expression to Postfix notation\n");
        writer.write(postfixRegex + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, postfixRegex, "RANGE_SEPARATOR");

        // ENFA for LCURLY
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(LCURLY + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, LCURLY, "LCURLY");

        // ENFA for RCURLY
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(RCURLY + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, RCURLY, "RCURLY");

        // ENFA for COMMA
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(COMMA + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, COMMA, "COMMA");

        // ENFA for LPAREN
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(LPAREN + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, LPAREN, "LPAREN");

        // ENFA for RPAREN
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(RPAREN + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, RPAREN, "RPAREN");

        // ENFA for QUOTE
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(QUOTE + "\n");
        writer.close();

        combinedEnfa = RegexToENFA.regexToENFA(combinedEnfa, QUOTE, "QUOTE");

        // ENFA for BAR
        // regexToENFA function treats `|` as an operator. So, it cannot be used
        writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write(BAR + "\n");
        writer.close();

        combinedEnfaAdjList = combinedEnfa.getAdjList();
        for(int i = 0; i < 2; i++) {
            edgeList = new ArrayList<Edge>();
            combinedEnfaAdjList.add(edgeList);
        }

        edgeList = combinedEnfaAdjList.get(combinedEnfaAdjList.size() - 2);
        Edge edge = new Edge(combinedEnfaAdjList.size() - 1, '|');
        edgeList.add(edge);

        edgeList = combinedEnfaAdjList.get(0);
        edge = new Edge(combinedEnfaAdjList.size() - 2, '\u03b5');
        edgeList.add(edge);

        combinedEnfa.addFinalState(combinedEnfaAdjList.size() - 1, "BAR");

        RegexToENFA.printAdjList(combinedEnfa);
    }
}