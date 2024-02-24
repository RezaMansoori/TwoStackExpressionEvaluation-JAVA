package ac.um.ds;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;


public class ExpressionEvaluator<T> {
    public double evaluateExpression(String s, HashMap<String, Double> variableValues) throws Exception {
        double val;
        Vector<Token> inTokens = new Vector<Token>();
        Vector<Token> postTokens = new Vector<Token>();
        tokenizeString(s, inTokens);
        infix2Postfix(inTokens, postTokens);
        val = evaluatePostfixExpression(postTokens, variableValues);
        // postTokens are a rearrangement of inTokens. So, we don't delete them
        for (int i = 0; i < inTokens.size(); i++) {
            inTokens.remove(i);
        }

        return val;
    }

    public String infix2Postfix(String s) throws Exception {
        int i;
        String out;
        Vector<Token> inTokens = new Vector<Token>();
        Vector<Token> postTokens = new Vector<Token>();
        tokenizeString(s, inTokens);
        infix2Postfix(inTokens, postTokens);
        out = tokenSeq2String(postTokens);

        // postTokens are a rearrangement of inTokens. So, we don't delete them
        for (i = 0; i < inTokens.size(); i++) {
            inTokens.remove(i);
        }

        return out;
    }

    public void tokenizeString(String s, Vector<Token> out) throws Exception {
        int i;
        char[] ch = new char[s.length() + 1];
        StringBuilder ss = new StringBuilder();

        s = s.toLowerCase();

        ss.append(s);
        ss.append('\0');

        Token token = null;
        token = getNextToken(ss, token);
        while (token != null) {

            out.add(token);
            token = getNextToken(ss, token);

        }
    }

    public String tokenSeq2String(Vector<Token> vt) {
        int i;
        String s = "";
        for (i = 0; i < vt.size(); i++) {
            s += ' ';
            s += vt.get(i).toText();
        }
        return s;
    }

    public double evaluatePostfixExpression(Vector<Token> s, HashMap<String, Double> variableValues) {
        Vector<Token> vtCalculate = new Vector<Token>();
        Token tk;
        while (!s.isEmpty()) {
            tk = s.firstElement();
            if (tk.mType == Token.Type.NUMERIC_CONST) {
                vtCalculate.add(tk);
            } else if (tk.mType == Token.Type.VARIABLE) {
                VariableName variable = (VariableName) tk;
                NumericConstant number = new NumericConstant(variableValues.get(variable.mName));
                vtCalculate.add(number);
            } else if (tk.mType == Token.Type.OPERATOR || tk.mType == Token.Type.FUNCTION) {

                NumericConstant numUp = (NumericConstant) vtCalculate.lastElement();
                vtCalculate.remove(vtCalculate.size() - 1);

                if (tk instanceof UnaryMinusOperator) {
                    UnaryMinusOperator newTk = (UnaryMinusOperator) tk;
                    Double result = newTk.evaluate(numUp.mValue, 0);
                    NumericConstant res = new NumericConstant(result);
                    vtCalculate.add(res);
                } else if (tk instanceof SinFunc) {
                    SinFunc newTk = (SinFunc) tk;
                    Double result = newTk.evaluate(numUp.mValue, 0);
                    NumericConstant res = new NumericConstant(result);
                    vtCalculate.add(res);
                } else if (tk instanceof CosFunc) {
                    CosFunc newTk = (CosFunc) tk;
                    Double result = newTk.evaluate(numUp.mValue, 0);
                    NumericConstant res = new NumericConstant(result);
                    vtCalculate.add(res);
                } else if (tk instanceof LnFunc) {
                    LnFunc newTk = (LnFunc) tk;
                    Double result = newTk.evaluate(numUp.mValue, 0);
                    NumericConstant res = new NumericConstant(result);
                    vtCalculate.add(res);
                } else if (tk instanceof ExpFunc) {
                    ExpFunc newTk = (ExpFunc) tk;
                    Double result = newTk.evaluate(numUp.mValue, 0);
                    NumericConstant res = new NumericConstant(result);
                    vtCalculate.add(res);
                } else {

                    NumericConstant numDown = (NumericConstant) vtCalculate.lastElement();
                    vtCalculate.remove(vtCalculate.size() - 1);

                    if (tk instanceof DivisionOperator) {
                        DivisionOperator newTk = (DivisionOperator) tk;
                        Double result = newTk.evaluate(numDown.mValue, numUp.mValue);
                        NumericConstant res = new NumericConstant(result);
                        vtCalculate.add(res);
                    } else if (tk instanceof MultiplicationOperator) {
                        MultiplicationOperator newTk = (MultiplicationOperator) tk;
                        Double result = newTk.evaluate(numDown.mValue, numUp.mValue);
                        NumericConstant res = new NumericConstant(result);
                        vtCalculate.add(res);
                    } else if (tk instanceof PlusOperator) {
                        PlusOperator newTk = (PlusOperator) tk;
                        Double result = newTk.evaluate(numDown.mValue, numUp.mValue);
                        NumericConstant res = new NumericConstant(result);
                        vtCalculate.add(res);
                    } else if (tk instanceof MinusOperator) {
                        MinusOperator newTk = (MinusOperator) tk;
                        Double result = newTk.evaluate(numDown.mValue, numUp.mValue);
                        NumericConstant res = new NumericConstant(result);
                        vtCalculate.add(res);
                    } else if (tk instanceof PowerOperator) {
                        PowerOperator newTk = (PowerOperator) tk;
                        Double result = newTk.evaluate(numDown.mValue, numUp.mValue);
                        NumericConstant res = new NumericConstant(result);
                        vtCalculate.add(res);
                    }
                }
            }
            s.remove(0);
        }

        NumericConstant num = (NumericConstant) vtCalculate.firstElement();
        return num.mValue;
    }

    public void infix2Postfix(Vector<Token> infixExpression, Vector<Token> postfixExpression) throws Exception {

        Vector<Token> temp = new Vector<Token>();

        for (int i = 0; i < infixExpression.size(); i++) {
            switch (infixExpression.get(i).mType) {
                case DELIMETER:
                    Delimeter delimeter = (Delimeter) infixExpression.get(i);
                    if (delimeter.mMark == '(') {
                        temp.add(infixExpression.get(i));
                    } else {
                        Token tk = temp.lastElement();
                        while (!(tk instanceof LeftParanthesis)) {
                            postfixExpression.add(tk);
                            temp.remove(temp.size() - 1);
                            tk = temp.lastElement();

                        }
                        temp.remove(temp.size() - 1);
                        if (!temp.isEmpty()) {
                            tk = temp.lastElement();
                            if (tk.mType == Token.Type.FUNCTION) {
                                postfixExpression.add(tk);
                                temp.remove(temp.size() - 1);
                            }
                        }
                    }
                    break;
                case FUNCTION:
                    temp.add(infixExpression.get(i));
                    break;
                case OPERATOR:
                    Operator infixOperator = (Operator) infixExpression.get(i);
                    if (!temp.isEmpty() && temp.lastElement() instanceof Operator) {
                        Operator tempOperator = (Operator) temp.lastElement();
                        while (infixOperator.mPrecedence < tempOperator.mPrecedence
                                || ((infixOperator.mPrecedence == tempOperator.mPrecedence)
                                        && (infixOperator.mAssociativity == Operator.Associativity.ASSOC_LEFT))) {
                            postfixExpression.add(temp.lastElement());
                            temp.remove(temp.size() - 1);
                            if (!temp.isEmpty() && temp.lastElement() instanceof Operator) {
                                tempOperator = (Operator) temp.lastElement();
                            } else
                                break;
                        }
                    }
                    temp.add(infixExpression.get(i));
                    break;
                case NUMERIC_CONST:
                case VARIABLE:
                    postfixExpression.add(infixExpression.get(i));
                    break;
            }
        }
        while (!temp.isEmpty()) {
            postfixExpression.add(temp.lastElement());
            temp.remove(temp.size() - 1);
        }
    }

    public Token getNextToken(StringBuilder ss, Token lastToken) throws Exception {
        int state = 0;
        int length = ss.length();

        Operator op;
        String tokenStr = "";
        char ch = ' ';
        String FinalString = "";
        Stack<Character> charStack = new Stack<Character>();
        boolean dotIsSeen = false;

        while (true) {
            ch = ss.charAt(0);
            ss.deleteCharAt(0);
            switch (state) {
                // Initial State
                case 0:
                    if (ch <= '9' && ch >= '0') {
                        tokenStr += ch;
                        state = 1;
                        dotIsSeen = false;
                        break;
                    }
                    if ((ch <= (byte) 'z' && ch >= (byte) 'a') || (ch <= (byte) 'Z' && ch >= (byte) 'A')) {
                        tokenStr += ch;
                        state = 2;
                        break;
                    } else {
                        switch (ch) {
                            case '(':
                                return new LeftParanthesis();
                            case ')':
                                return new RightParanthesis();
                            case '^':
                                return new PowerOperator();
                            case '+':
                                return new PlusOperator();
                            case '*':
                                return new MultiplicationOperator();
                            case '/':
                                return new DivisionOperator();
                            case '-':
                                if (lastToken == null || lastToken instanceof LeftParanthesis
                                        || (lastToken instanceof Operator) && ((op = (Operator) lastToken) != null)) {
                                    return new UnaryMinusOperator();
                                } else {
                                    return new MinusOperator();
                                }
                            case '.':
                                state = 1;
                                dotIsSeen = true;
                                break;
                            case '\0':
                                return null;
                            case 10:
                            case 13:
                            case 32:
                            case (int) ('\t'):
                                break;
                            default: {
                                String buff = "UnAllowed character No:     ";
                                buff += '0' + ch / 100;
                                buff += '0' + (ch % 100) / 10;
                                buff += '0' + ch % 10;
                                buff = buff.substring(0, 28);
                                throw new Exception(buff);
                            }
                        }
                    }
                    break;

                // Number
                case 1:
                    if (ch == '.') {
                        if (dotIsSeen) {
                            throw new Exception("Numeric string with two dots!");
                        } else {
                            dotIsSeen = true;
                        }

                    } else if (ch > '9' || ch < '0') {
                        double val;
                        ss.insert(0, ch);
                        val = Double.parseDouble(tokenStr);
                        return new NumericConstant(val);
                    }

                    tokenStr += (char) ch;
                    break;

                // String
                case 2:
                    if ((ch <= (byte) 'z' && ch >= (byte) 'a') || (ch <= (byte) 'Z' && ch >= (byte) 'A')
                            || (ch <= '9' && ch >= '0')) {
                        tokenStr += (char) ch;
                    } else {
                        ss.insert(0, ch);
                        if (tokenStr.equals("sin")) {
                            return new SinFunc();
                        } else if (tokenStr.equals("cos")) {
                            return new CosFunc();
                        } else if (tokenStr.equals("exp")) {
                            return new ExpFunc();
                        } else if (tokenStr.equals("ln")) {
                            return new LnFunc();
                        } else {
                            return new VariableName(tokenStr);
                        }
                    }
                    break;
            }
        }
    }
}